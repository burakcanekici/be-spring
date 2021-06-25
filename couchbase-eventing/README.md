There are 2 options to create buckets;

* **OPTION 1:**

execute the following command to create Couchbase cluster container;

```java
docker run -d --name db -p 8091-8094:8091-8094 -p 11210:11210 couchbase
```

create a cluster with the information below;

```java
cluster-name: be-cluster
user-name: admin
password: 123456
```

create `beer-sample` and `beer-sample-metadata` buckets

* **OPTION 2:**

executing `docker-compose.yml` to create the bucket we will need;
```java
docker-compose up
```
---

There are 2 different bucket.
* beer-sample : where keep the actual record
* beer-sample-metadata : where keep the metadata information

---

Open `localhost:8091` on browser and navigate `Eventing` tab on the left panel

---

Click `ADD FUNCTION` button

----

Select the following
* Source Bucket -> beer-sample
* Metadata Bucket -> beer-sample-metadata
* Function Name -> notify
* Binding section : open terminal and type `ifconfig` and get ip (like; 192.168.1.255). Then add `notify-api` alias and bind this ip to use it in javascript function.

![alt text](https://github.com/burakcanekici/be-spring/blob/main/couchbase-eventing/image/add_function.png)

---

Click `Add Code` button and copy the following code and save it

```java
function OnUpdate(doc, meta) {
    var request = {
        path: '/notify',
        body: {
            id : meta.id,
            value: doc
        }
    };
    var response = curl("POST", notifyApi, request);
    if(response.status != 200){
        log('request failed', response);
    }
}
function OnDelete(meta, options) {
}
```
---

Deploy the function

![alt text](https://github.com/burakcanekici/be-spring/blob/main/couchbase-eventing/image/deploy.png)

---

create index on name property that will be used as updating;
```java
CREATE INDEX `idx_beer-sample` ON `beer-sample`(`name`)
```

insert a beer document with the following N1QL;
```java
INSERT INTO `beer-sample` (KEY, VALUE)
VALUES ("1", {
  "distributer": "TR",
  "name": "Bud"
});
```

update the beer document with the following N1QL;
```java
UPDATE `beer-sample`
SET distributer = "USA"
WHERE name = "Bud";
```

after executing the N1QL queries, the log about changing will POST to API and show on terminal

![alt text](https://github.com/burakcanekici/be-spring/blob/main/couchbase-eventing/image/log.png)
