package com.be.couchbasedemo.domain.couchbase;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

public class Company implements Serializable {

    private static final long serialVersionUID = 5627219693863130352L;

    @Id
    private String id;

    @Field
    private String companyName;

    @Field
    private String location;

    @Field
    private int size;

    @Field
    private String category;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        return new EqualsBuilder().append(size, company.size).append(id, company.id).append(companyName, company.companyName).append(location, company.location).append(category, company.category).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(companyName).append(location).append(size).append(category).toHashCode();
    }

    public static final class CompanyBuilder {
        private String id;
        private String companyName;
        private String location;
        private int size;
        private String category;

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

        public CompanyBuilder category(String category) {
            this.category = category;
            return this;
        }

        public Company build() {
            Company company = new Company();
            company.setId(id);
            company.setCompanyName(companyName);
            company.setLocation(location);
            company.setSize(size);
            company.setCategory(category);
            return company;
        }
    }
}