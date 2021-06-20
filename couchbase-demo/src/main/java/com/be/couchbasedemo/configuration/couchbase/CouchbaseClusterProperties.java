package com.be.couchbasedemo.configuration.couchbase;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "couchbase.buckets")
public class CouchbaseClusterProperties {

    private List<String> hosts;

    @NestedConfigurationProperty
    private CouchbaseBucketProperties company;

    @NestedConfigurationProperty
    private CouchbaseBucketProperties employee;

    public List<String> getHosts() {
        return hosts;
    }

    public void setHosts(List<String> hosts) {
        this.hosts = hosts;
    }

    public CouchbaseBucketProperties getCompany() {
        return company;
    }

    public void setCompany(CouchbaseBucketProperties company) {
        this.company = company;
    }

    public CouchbaseBucketProperties getEmployee() {
        return employee;
    }

    public void setEmployee(CouchbaseBucketProperties employee) {
        this.employee = employee;
    }
}
