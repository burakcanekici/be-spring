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
if couchbase-cli server-list -c localhost:8091 --username root --password password ; then
    log "$(date +"%T") Cluster already initialized ........."
else
    # Setup index and memory quota
    log "$(date +"%T") Init cluster ........."
    couchbase-cli cluster-init -c 127.0.0.1 --cluster-username root --cluster-password password \
      --cluster-name Players --cluster-ramsize 1200 --cluster-index-ramsize 256 --services data,index,query,fts \
      --index-storage-setting default

    # Create the buckets
    log "$(date +"%T") Create buckets ........."
    couchbase-cli bucket-create -c 127.0.0.1 --username root --password password --bucket-type couchbase \
      --bucket-ramsize 250 --bucket Players

    # Create user
    log "$(date +"%T") Create users ........."
    couchbase-cli user-manage -c 127.0.0.1:8091 -u root -p password --set --rbac-username Players \
      --rbac-password password --rbac-name "Players" --roles admin --auth-domain local

    # Need to wait until query service is ready to process N1QL queries
    log "$(date +"%T") Waiting ........."
    sleep 20

    # Create indexes
    echo "$(date +"%T") Create Players indexes ........."
    cbq -u Players -p password -s "CREATE INDEX \`idx_players_id\` ON \`Players\`(META().\`id\`);"
fi

fg 1
