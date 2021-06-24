package com.be.couchbasesdkdemo.repository;

import com.be.couchbasesdkdemo.model.entity.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, String> {

    void deleteEmployeeByName(String name);
}
