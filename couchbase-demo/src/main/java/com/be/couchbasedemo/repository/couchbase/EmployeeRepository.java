package com.be.couchbasedemo.repository.couchbase;

import com.be.couchbasedemo.domain.couchbase.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, String> {

}