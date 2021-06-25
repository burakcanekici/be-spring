package com.be.couchbasesdktransactiondemo.configuration.couchbase;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "couchbase")
public class CouchbaseClusterProperties {

    private String connectionString;
    private String userName;
    private String password;

    @NestedConfigurationProperty
    private CouchbaseBucketProperties bucketCompany;

    @NestedConfigurationProperty
    private CouchbaseBucketProperties bucketEmployee;

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public CouchbaseBucketProperties getBucketCompany() {
        return bucketCompany;
    }

    public void setBucketCompany(CouchbaseBucketProperties bucketCompany) {
        this.bucketCompany = bucketCompany;
    }

    public CouchbaseBucketProperties getBucketEmployee() {
        return bucketEmployee;
    }

    public void setBucketEmployee(CouchbaseBucketProperties bucketEmployee) {
        this.bucketEmployee = bucketEmployee;
    }
}
