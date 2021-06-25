FROM couchbase:6.6.1

COPY initialize.sh /opt/couchbase
COPY function.js /opt/couchbase

CMD ["/opt/couchbase/initialize.sh"]
