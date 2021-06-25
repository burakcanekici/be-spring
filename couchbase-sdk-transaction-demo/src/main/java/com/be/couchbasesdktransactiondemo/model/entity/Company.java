package com.be.couchbasesdktransactiondemo.model.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;
import org.springframework.data.couchbase.core.mapping.id.IdAttribute;

import java.io.Serializable;


public class Company implements Serializable {

    private static final long serialVersionUID = 7330101427517450936L;

    @Id
    @GeneratedValue(strategy = GenerationStrategy.USE_ATTRIBUTES, delimiter = "#")
    private String id;

    @Field
    @IdAttribute(order = 0)
    private String companyName;

    @Field
    @IdAttribute(order = 1)
    private String location;

    @Field
    private int size;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

        Company company = (Company) o;

        return new EqualsBuilder().append(size, company.size).append(id, company.id).append(companyName, company.companyName).append(location, company.location).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(companyName).append(location).append(size).toHashCode();
    }

    public static final class CompanyBuilder {
        private String id;
        private String companyName;
        private String location;
        private int size;

        private CompanyBuilder() {
        }

        public static CompanyBuilder aCompany() {
            return new CompanyBuilder();
        }

        public CompanyBuilder id(String id) {
            this.id = id;
            return this;
        }

        public CompanyBuilder companyName(String companyName) {
            this.companyName = companyName;
            return this;
        }

        public CompanyBuilder location(String location) {
            this.location = location;
            return this;
        }

        public CompanyBuilder size(int size) {
            this.size = size;
            return this;
        }

        public Company build() {
            Company company = new Company();
            company.setId(id);
            company.setCompanyName(companyName);
            company.setLocation(location);
            company.setSize(size);
            return company;
        }
    }
}
