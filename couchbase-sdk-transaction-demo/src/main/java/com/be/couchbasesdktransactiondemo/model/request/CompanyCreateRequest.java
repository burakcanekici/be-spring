package com.be.couchbasesdktransactiondemo.model.request;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

public class CompanyCreateRequest implements Serializable {

    private static final long serialVersionUID = 6092965421642913081L;

    private String companyName;
    private String location;
    private int size;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        CompanyCreateRequest that = (CompanyCreateRequest) o;

        return new EqualsBuilder().append(size, that.size).append(companyName, that.companyName).append(location, that.location).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(companyName).append(location).append(size).toHashCode();
    }


    public static final class CompanyCreateRequestBuilder {
        private String companyName;
        private String location;
        private int size;

        private CompanyCreateRequestBuilder() {
        }

        public static CompanyCreateRequestBuilder aCompanyCreateRequest() {
            return new CompanyCreateRequestBuilder();
        }

        public CompanyCreateRequestBuilder companyName(String companyName) {
            this.companyName = companyName;
            return this;
        }

        public CompanyCreateRequestBuilder location(String location) {
            this.location = location;
            return this;
        }

        public CompanyCreateRequestBuilder size(int size) {
            this.size = size;
            return this;
        }

        public CompanyCreateRequest build() {
            CompanyCreateRequest companyCreateRequest = new CompanyCreateRequest();
            companyCreateRequest.setCompanyName(companyName);
            companyCreateRequest.setLocation(location);
            companyCreateRequest.setSize(size);
            return companyCreateRequest;
        }
    }
}
