package com.be.couchbaseclientsdkdemo.repository;

import com.be.couchbaseclientsdkdemo.model.entity.Employee;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, String> {

    void deleteEmployeeByName(String name);
}
