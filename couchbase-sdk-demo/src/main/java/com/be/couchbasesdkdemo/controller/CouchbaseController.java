package com.be.couchbasesdkdemo.controller;

import com.be.couchbasesdkdemo.model.request.CompanyCreateRequest;
import com.couchbase.client.core.cnc.Event;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.transactions.TransactionDurabilityLevel;
import com.couchbase.transactions.Transactions;
import com.couchbase.transactions.config.TransactionConfigBuilder;
import com.couchbase.transactions.error.TransactionCommitAmbiguous;
import com.couchbase.transactions.error.TransactionFailed;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/couchbase")
public class CouchbaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CouchbaseController.class);

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation(value = "create document")
    public void insert(@RequestBody @Valid CompanyCreateRequest companyCreateRequest) throws Exception {

        Cluster cluster = Cluster.connect("localhost", "admin", "123456");
        Bucket bucket = cluster.bucket("Company");
        Collection collection = bucket.defaultCollection();

        Transactions transactions = Transactions.create(cluster,
                TransactionConfigBuilder.create()
                        //TODO level PERSIST_TO_MAJORITY olmalı
                        .durabilityLevel(TransactionDurabilityLevel.NONE)
                        .logOnFailure(true, Event.Severity.WARN)
                        .build());

        try {
            transactions.run(ctx -> {
                String baseName = companyCreateRequest.getCompanyName();
                for(int i=0;i<10;i++){
                    if(i == 5){
                        //throw new RuntimeException("exception is fired!");
                    }
                    companyCreateRequest.setCompanyName(baseName + "#" + String.valueOf(i));
                    JsonObject value = JsonObject.create()
                            .put("name", companyCreateRequest.getCompanyName())
                            .put("category", companyCreateRequest.getCategory())
                            .put("location", companyCreateRequest.getLocation())
                            .put("size", companyCreateRequest.getSize());
                    String key = DigestUtils.md5Hex(companyCreateRequest.getCompanyName());
                    ctx.insert(collection, key, value);
                }
                ctx.commit();
            });
        } catch (TransactionCommitAmbiguous e){
            LOGGER.error("Couchbase Transaction Error - TransactionCommitAmbiguous!", e);
        } catch (TransactionFailed e) {
            LOGGER.error("Couchbase Transaction Error - TransactionFailed!", e);
        } catch (RuntimeException e){
            LOGGER.error("Couchbase Transaction Error - RuntimeException!", e);
        }

        /*
        String baseName = companyCreateRequest.getCompanyName();
        for(int i=0;i<10;i++){
            companyCreateRequest.setCompanyName(baseName + "#" + String.valueOf(i));
            companyService.insert(companyCreateRequest);
        }

        employeeService.insert(anEmployeeCreateRequest().name("Tom").surname("Riddle").salary(10000).build());
         */
    }
}
