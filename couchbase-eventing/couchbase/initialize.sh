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
      --cluster-name be-cluster --cluster-ramsize 768 --cluster-index-ramsize 256 --services data,index,query,fts,eventing \
      --index-storage-setting default

    # Create the buckets
    log "$(date +"%T") Create beer-sample bucket ........."
    couchbase-cli bucket-create -c 127.0.0.1 --username admin --password 123456 \
      --bucket-type couchbase --bucket-ramsize 250 --bucket beer-sample

    log "$(date +"%T") Create beer-sample-metadata bucket ........."
    couchbase-cli bucket-create -c 127.0.0.1 --username admin --password 123456 \
      --bucket-type couchbase --bucket-ramsize 250 --bucket beer-sample-metadata

    # log "$(date +"%T") Create event function ........."
    # couchbase-cli eventing-function-setup -c 127.0.0.1 --username admin --password 123456 \
    #   --import --file opt/couchbase/function.js

    # couchbase-cli eventing-function-setup -c 127.0.0.1 --username admin --password 123456 \
    #  --export-all --name notify --file opt/couchbase/function.js

    # couchbase-cli eventing-function-setup -c 127.0.0.1 --username admin --password 123456 \
    # --deploy --name notify --boundary from-everything

    # couchbase-cli eventing-function-setup -c 192.168.1.5 -u Administrator \
    #  -p password --deploy --name alert_function --boundary from-now
fi

fg 1
