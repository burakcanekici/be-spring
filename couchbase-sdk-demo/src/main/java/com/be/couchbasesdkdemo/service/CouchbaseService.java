package com.be.couchbasesdkdemo.service;

import com.be.couchbasesdkdemo.domain.Company;
import com.be.couchbasesdkdemo.model.request.CompanyCreateRequest;
import com.be.couchbasesdkdemo.repository.CompanyRepository;
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
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.be.couchbasesdkdemo.domain.Company.CompanyBuilder.aCompany;


@Service
public class CouchbaseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CouchbaseService.class);

    private final CompanyPGService companyPGService;
    private final CompanyRepository companyRepository;

    public CouchbaseService(CompanyPGService companyPGService,
                            CompanyRepository companyRepository) {
        this.companyPGService = companyPGService;
        this.companyRepository = companyRepository;
    }

    @Transactional
    public void insertTx(List<CompanyCreateRequest> companies){
        Cluster cluster = Cluster.connect("localhost", "admin", "123456");
        Bucket bucket = cluster.bucket("Company");
        Collection collection = bucket.defaultCollection();

        Transactions transactions = Transactions.create(cluster,
                TransactionConfigBuilder.create()
                        //TODO level PERSIST_TO_MAJORITY olmalı
                        .durabilityLevel(TransactionDurabilityLevel.NONE)
                        .logOnFailure(true, Event.Severity.WARN)
                        .build());

        companies.forEach(company -> {
            //companyPGService.insertTx(company);
            Company.CompanyBuilder comp = aCompany()
                    .name(company.getCompanyName())
                    .location(company.getLocation())
                    .size(company.getSize());

            if(company.getCompanyName().equals("Apple#2")){
                //throw new RuntimeException("test exception");
            }

            companyRepository.save(comp.build());
        });

        try {
            transactions.run(ctx -> {

                for (CompanyCreateRequest company: companies) {

                    JsonObject value = JsonObject.create()
                            .put("name", company.getCompanyName())
                            .put("category", company.getCategory())
                            .put("location", company.getLocation())
                            .put("size", company.getSize());
                    String key = DigestUtils.md5Hex(company.getCompanyName());
                    ctx.insert(collection, key, value);
                }
            });
        } catch (TransactionCommitAmbiguous e){
            throw new RuntimeException();
            //LOGGER.error("Couchbase Transaction Error - TransactionCommitAmbiguous!", e);
        }
    }

    public void insertBulkTx(CompanyCreateRequest companyCreateRequest){
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
