package com.be.couchbaseclientsdkdemo.model.request;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

public class EmployeeCreateRequest implements Serializable {

    private static final long serialVersionUID = -6985166838129401944L;

    private String name;
    private String surname;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        EmployeeCreateRequest that = (EmployeeCreateRequest) o;

        return new EqualsBuilder().append(name, that.name).append(surname, that.surname).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(name).append(surname).toHashCode();
    }

    public static final class EmployeeCreateRequestBuilder {
        private String name;
        private String surname;

        private EmployeeCreateRequestBuilder() {
        }

        public static EmployeeCreateRequestBuilder anEmployeeCreateRequest() {
            return new EmployeeCreateRequestBuilder();
        }

        public EmployeeCreateRequestBuilder name(String name) {
            this.name = name;
            return this;
        }

        public EmployeeCreateRequestBuilder surname(String surname) {
            this.surname = surname;
            return this;
        }

        public EmployeeCreateRequest build() {
            EmployeeCreateRequest employeeCreateRequest = new EmployeeCreateRequest();
            employeeCreateRequest.setName(name);
            employeeCreateRequest.setSurname(surname);
            return employeeCreateRequest;
        }
    }
}
