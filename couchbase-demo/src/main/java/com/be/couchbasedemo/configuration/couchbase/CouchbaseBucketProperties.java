package com.be.couchbasedemo.configuration.couchbase;

import java.util.List;

public class CouchbaseBucketProperties {

    private List<String> bootstrapHosts;
    private String bucketName;
    private String userName;
    private String password;

    public List<String> getBootstrapHosts() {
        return bootstrapHosts;
    }

    public void setBootstrapHosts(List<String> bootstrapHosts) {
        this.bootstrapHosts = bootstrapHosts;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
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
}
