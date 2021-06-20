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

CLUSTER_USER="admin"
CLUSTER_PASSWORD="123456"
CLUSTER_NAME="be-cluster"
COMPANY_BUCKET="Company"
EMPLOYEE_BUCKET="Employee"
COMPANY_USER="Company"
EMPLOYEE_USER="Employee"
PASSWORD="password"

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

# Setup index and memory quota
log "$(date +"%T") Init cluster ........."
couchbase-cli cluster-init -c 127.0.0.1 --cluster-username $CLUSTER_USER --cluster-password $CLUSTER_PASSWORD \
  --cluster-name $CLUSTER_NAME --cluster-ramsize 768 --cluster-index-ramsize 256 --services data,index,query,fts \
  --index-storage-setting default

  # Create the Company bucket
  log "$(date +"%T") Create Company bucket ........."
  couchbase-cli bucket-create -c 127.0.0.1 --username $CLUSTER_USER --password $CLUSTER_PASSWORD --bucket-type couchbase \
  --bucket-ramsize 250 --bucket $COMPANY_BUCKET

  # Create the Employee bucket
  log "$(date +"%T") Create Employee bucket ........."
  couchbase-cli bucket-create -c 127.0.0.1 --username $CLUSTER_USER --password $CLUSTER_PASSWORD --bucket-type couchbase \
  --bucket-ramsize 250 --bucket $EMPLOYEE_BUCKET

  # Create Company user
  log "$(date +"%T") Create Company user ........."
  couchbase-cli user-manage -c 127.0.0.1:8091 -u $CLUSTER_USER -p $CLUSTER_PASSWORD --set --rbac-username $COMPANY_USER \
  --rbac-password $PASSWORD --rbac-name $COMPANY_BUCKET --roles admin --auth-domain local

  # Create Employee user
  log "$(date +"%T") Create Employee user ........."
  couchbase-cli user-manage -c 127.0.0.1:8091 -u $CLUSTER_USER -p $CLUSTER_PASSWORD --set --rbac-username $EMPLOYEE_USER \
  --rbac-password $PASSWORD --rbac-name $EMPLOYEE_BUCKET --roles admin --auth-domain local

  # Need to wait until query service is ready to process N1QL queries
  log "$(date +"%T") Waiting ........."
  sleep 20

  # Create indexes
  echo "$(date +"%T") Create indexes ........."
  cbq -u $COMPANY_USER -p $PASSWORD -s "CREATE INDEX \`idx_company\` ON \`Company\`(META().\`id\`);"
  cbq -u $EMPLOYEE_USER -p $PASSWORD -s "CREATE INDEX \`idx_employee\` ON \`Employee\`(META().\`id\`);"


fg 1
