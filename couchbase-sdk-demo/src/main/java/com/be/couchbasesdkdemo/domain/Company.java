package com.be.couchbasesdkdemo.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "companies")
@SequenceGenerator(name = "seq_companies", allocationSize = 15, sequenceName = "seq_companies")
public class Company implements Serializable {

    private static final long serialVersionUID = 6092965421642913080L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_companies")
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "size", nullable = false)
    private int size;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

        return new EqualsBuilder().append(size, company.size).append(id, company.id).append(name, company.name).append(location, company.location).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(name).append(location).append(size).toHashCode();
    }


    public static final class CompanyBuilder {
        private Long id;
        private String name;
        private String location;
        private int size;

        private CompanyBuilder() {
        }

        public static CompanyBuilder aCompany() {
            return new CompanyBuilder();
        }

        public CompanyBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CompanyBuilder name(String name) {
            this.name = name;
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
            company.setName(name);
            company.setLocation(location);
            company.setSize(size);
            return company;
        }
    }
}
