package com.be.couchbaseclientsdkdemo.configuration.couchbase;

import com.be.couchbaseclientsdkdemo.model.entity.Company;
import com.be.couchbaseclientsdkdemo.model.entity.Employee;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.couchbase.CouchbaseClientFactory;
import org.springframework.data.couchbase.SimpleCouchbaseClientFactory;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.data.couchbase.core.convert.MappingCouchbaseConverter;
import org.springframework.data.couchbase.repository.config.RepositoryOperationsMapping;

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

    @Override
    public void configureRepositoryOperationsMapping(RepositoryOperationsMapping baseMapping) {
        try {
            baseMapping
                    .mapEntity(Company.class, companyTemplate())
                    .mapEntity(Employee.class, employeeTemplate());
        } catch (Exception e) {
            throw e;
        }
    }

    @Bean("companyClientFactory")
    public CouchbaseClientFactory companyClientFactory() {
        return new SimpleCouchbaseClientFactory(getConnectionString(), authenticator(), couchbaseClusterProperties.getBucketCompany().getName());
    }

    @Bean("companyTemplate")
    public CouchbaseTemplate companyTemplate(){
        return new CouchbaseTemplate(companyClientFactory(), new MappingCouchbaseConverter());
    }

    @Bean("employeeClientFactory")
    public CouchbaseClientFactory employeeClientFactory() {
        return new SimpleCouchbaseClientFactory(getConnectionString(), authenticator(), couchbaseClusterProperties.getBucketEmployee().getName());
    }

    @Bean("employeeTemplate")
    public CouchbaseTemplate employeeTemplate(){
        return new CouchbaseTemplate(employeeClientFactory(), new MappingCouchbaseConverter());
    }
}
