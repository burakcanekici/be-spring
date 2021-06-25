package com.be.couchbasesdktransactiondemo.configuration.couchbase;

import com.couchbase.client.core.cnc.Event;
import com.couchbase.client.java.Cluster;
import com.couchbase.transactions.TransactionDurabilityLevel;
import com.couchbase.transactions.Transactions;
import com.couchbase.transactions.config.TransactionConfigBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;

@Configuration
@Profile("!unit-test")
public class CouchbaseBucketConfiguration extends AbstractCouchbaseConfiguration {

    private final CouchbaseClusterProperties couchbaseClusterProperties;

    public CouchbaseBucketConfiguration(CouchbaseClusterProperties couchbaseClusterProperties) {
        this.couchbaseClusterProperties = couchbaseClusterProperties;
    }

    @Override
    public String getConnectionString() {
        return couchbaseClusterProperties.getConnectionString();
    }

    @Override
    public String getUserName() {
        return couchbaseClusterProperties.getUserName();
    }

    @Override
    public String getPassword() {
        return couchbaseClusterProperties.getPassword();
    }

    @Override
    public String getBucketName() {
        return couchbaseClusterProperties.getBucketCompany().getName();
    }

    @Bean
    public Transactions transactions(final Cluster couchbaseCluster){
        return Transactions.create(couchbaseCluster,
                TransactionConfigBuilder
                        .create()
                        .durabilityLevel(TransactionDurabilityLevel.NONE)
                        .logOnFailure(true, Event.Severity.WARN)
                        .build());
    }
}
