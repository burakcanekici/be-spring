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
