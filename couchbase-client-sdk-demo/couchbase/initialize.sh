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

CLUSTER_USER_NAME="admin"
CLUSTER_PASSWORD="123456"
CLUSTER_NAME="be-cluster"

BUCKET_COMPANY="Company"
BUCKET_COMPANY_PASSWORD="Company123"
BUCKET_EMPLOYEE="Employee"
BUCKET_EMPLOYEE_PASSWORD="Employee123"


# Check if cluster EXISTS
if couchbase-cli server-list -c localhost:8091 --username $CLUSTER_USER_NAME --password $CLUSTER_PASSWORD ; then
    log "$(date +"%T") Cluster already initialized ........."
else
    # Setup index and memory quota
    log "$(date +"%T") Init cluster ........."
    couchbase-cli cluster-init -c 127.0.0.1 --cluster-username $CLUSTER_USER_NAME --cluster-password $CLUSTER_PASSWORD \
      --cluster-name CLUSTER_NAME --cluster-ramsize 768 --cluster-index-ramsize 256 --services data,index,query,fts \
      --index-storage-setting default

    # Create the buckets
    log "$(date +"%T") Create Company bucket ........."
    couchbase-cli bucket-create -c 127.0.0.1 --username $CLUSTER_USER_NAME --password $CLUSTER_PASSWORD \
      --bucket-type couchbase --bucket-ramsize 250 --bucket $BUCKET_COMPANY

    log "$(date +"%T") Create Employee bucket ........."
    couchbase-cli bucket-create -c 127.0.0.1 --username $CLUSTER_USER_NAME --password $CLUSTER_PASSWORD \
      --bucket-type couchbase --bucket-ramsize 250 --bucket $BUCKET_EMPLOYEE

    # Create the users
    log "$(date +"%T") Create Company user ........."
    couchbase-cli user-manage -c 127.0.0.1:8091 --username $CLUSTER_USER_NAME --password $CLUSTER_PASSWORD \
      --set --rbac-username $BUCKET_COMPANY --rbac-password $BUCKET_COMPANY_PASSWORD \
      --rbac-name $BUCKET_COMPANY --roles admin --auth-domain local

    log "$(date +"%T") Create Employee user ........."
    couchbase-cli user-manage -c 127.0.0.1:8091 --username $CLUSTER_USER_NAME --password $CLUSTER_PASSWORD \
      --set --rbac-username $BUCKET_EMPLOYEE --rbac-password $BUCKET_EMPLOYEE_PASSWORD \
      --rbac-name $BUCKET_EMPLOYEE --roles admin --auth-domain local

    # Need to wait until query service is ready to process N1QL queries
    log "$(date +"%T") Waiting ........."
    sleep 20

    # Create SellerTaskChallenge indexes
    echo "$(date +"%T") Create indexes ........."
    cbq --username $BUCKET_COMPANY --password $BUCKET_COMPANY_PASSWORD \ -s "CREATE INDEX \`idx_company\` ON \`Company\`(META().\`id\`);"
    cbq --username $BUCKET_EMPLOYEE --password $BUCKET_EMPLOYEE_PASSWORD \ -s "CREATE INDEX \`idx_employee\` ON \`Employee\`(META().\`id\`);"
fi

fg 1
