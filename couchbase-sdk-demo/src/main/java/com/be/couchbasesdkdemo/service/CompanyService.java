package com.be.couchbasesdkdemo.service;

import com.be.couchbasesdkdemo.model.request.CompanyCreateRequest;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.MutationResult;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {


    public void insert(Cluster cluster, CompanyCreateRequest companyCreateRequest){
        Bucket bucket = cluster.bucket("Company");
        Collection collection = bucket.defaultCollection();
        MutationResult upsertResult = collection.upsert(
                DigestUtils.md5Hex(companyCreateRequest.getCompanyName()),
                JsonObject.create().put("name", companyCreateRequest.getCompanyName())
                        .put("category", companyCreateRequest.getCategory())
                        .put("location", companyCreateRequest.getLocation())
                        .put("size", companyCreateRequest.getSize())
        );
    }
}
