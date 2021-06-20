**Problem:** with core-io:2.1.6, `setAttribute` was removed and it is fired exception

`path: cd /Users/burakcan.ekici/.m2/repository/com/couchbase/client/core-io`

```java
    <dependency>
       <groupId>com.couchbase.client</groupId>
       <artifactId>couchbase-transactions</artifactId>
       <version>1.1.7</version>
    </dependency>
```

`couchbase-transaction:1.1.8` above has dependency ->  `java-client:3.16` which has dependency ->  `core-io:2.1.6`

but `setAttribute` was removed after core-io:2.1.5,

so we need to navigate to `path: cd /Users/burakcan.ekici/.m2/repository/com/couchbase/client/core-io` and remove core-io:2.1.6 with `rm -rf core-io/2.1.6` command and keep core-io:2.1.5

**clean .m2 file :**
```java
rm -rf ~/.m2/repository
```

**before start :***
```java
mvnci
```
