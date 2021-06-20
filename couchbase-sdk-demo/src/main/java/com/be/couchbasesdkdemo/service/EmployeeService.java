package com.be.couchbasesdkdemo.service;

import com.be.couchbasesdkdemo.model.request.EmployeeCreateRequest;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.MutationResult;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {


    public void insert(EmployeeCreateRequest employeeCreateRequest){
        Cluster cluster = Cluster.connect("localhost", "admin", "123456");
        Bucket bucket = cluster.bucket("Employee");
        Collection collection = bucket.defaultCollection();
        MutationResult upsertResult = collection.upsert(
                DigestUtils.md5Hex(employeeCreateRequest.getName()),
                JsonObject.create().put("name", employeeCreateRequest.getName())
                        .put("surname", employeeCreateRequest.getSurname())
                        .put("salary", employeeCreateRequest.getSalary())
        );
    }
}
