* make it single jar file and sign postgresql-42.2.24.jar from local repository
```
mvn package

spark-submit \
--class SimpleApp \
--driver-class-path ~/.m2/repository/org/postgresql/postgresql/42.2.24/postgresql-42.2.24.jar \
target/spark-data-migrate-1.0-SNAPSHOT.jar
```


* make it uber jar file and it takes postgresql-42.2.24.jar from the uber jar
```
mvn clean compile assembly:single

spark-submit \
--class SimpleApp \
target/spark-data-migrate-1.0-SNAPSHOT-jar-with-dependencies.jar
```

After generating jar file, we can run the cbimport command. The _export_ directory is shared volume between Postgres and Couchbase containers. Therefore, there is no need to copy export.csv
file from one container to another. We can directly run the cbimport command.
```
cbimport csv -c couchbase://127.0.0.1 -u root -p password -b Players -d file://import/export.csv -g %id% --ignore-fields id, -t 4
```

Or, we should copy the csv file into Couchbase container and run the cbimport command.
```
docker cp IdeaProjects/spark-data-migration/export.csv new-seller-tag-api_couchbase_1:/
cbimport csv -c couchbase://127.0.0.1 -u root -p password -b Players -d file://export.csv -g %id% --ignore-fields id, -t 4
```
