#!/bin/bash

# Enables job control
set -m

# Enables error propagation
set -e

# Run the server and send it to the background
/entrypoint.sh couchbase-server &

# Check if couchbase server is up
check_db() {
  curl --silent http://127.0.0.1:8091/pools > /dev/null
  echo $?
}

# Variable used in echo
i=1
# Echo with
log() {
  echo "[$i] [$(date +"%T")] $@"
  i=`expr $i + 1`
}

# Wait until it's ready
until [[ $(check_db) = 0 ]]; do
  >&2 log "Waiting for Couchbase Server to be available ..."
  sleep 1
done

# Check if cluster EXISTS
if couchbase-cli server-list -c localhost:8091 --username admin --password 123456 ; then
    log "$(date +"%T") Cluster already initialized ........."
else
    # Setup index and memory quota
    log "$(date +"%T") Init cluster ........."
    couchbase-cli cluster-init -c 127.0.0.1 --cluster-username admin --cluster-password 123456 \
      --cluster-name be-cluster --cluster-ramsize 768 --cluster-index-ramsize 256 --services data,index,query,fts \
      --index-storage-setting default

    # Create the buckets
    log "$(date +"%T") Create Company bucket ........."
    couchbase-cli bucket-create -c 127.0.0.1 --username admin --password 123456 \
      --bucket-type couchbase --bucket-ramsize 250 --bucket Company

    log "$(date +"%T") Create Employee bucket ........."
    couchbase-cli bucket-create -c 127.0.0.1 --username admin --password 123456 \
      --bucket-type couchbase --bucket-ramsize 250 --bucket Employee

    # Create the users
    log "$(date +"%T") Create Company user ........."
    couchbase-cli user-manage -c 127.0.0.1:8091 -u admin -p 123456 \
      --set --rbac-username Company --rbac-password com123 \
      --rbac-name "Company" --roles admin --auth-domain local

    log "$(date +"%T") Create Employee user ........."
    couchbase-cli user-manage -c 127.0.0.1:8091 -u admin -p 123456 \
      --set --rbac-username Employee --rbac-password emp123 \
      --rbac-name "Employee" --roles admin --auth-domain local

    # Need to wait until query service is ready to process N1QL queries
    log "$(date +"%T") Waiting ........."
    sleep 20

    # Create SellerTaskChallenge indexes
    echo "$(date +"%T") Create indexes ........."
    cbq -u Company -p com123 -s "CREATE INDEX \`idx_company_id\` ON \`Company\`(META().\`id\`);"
    cbq -u Employee -p emp123 -s "CREATE INDEX \`idx_employee_id\` ON \`Employee\`(META().\`id\`);"
    cbq -u Employee -p emp123 -s "CREATE INDEX \`idx_employee_name\` ON \`Employee\`(\`name\`);"
fi

fg 1
