package com.be.couhcbasesdkdemo.repository;

import com.be.couhcbasesdkdemo.model.entity.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, String> {

    void deleteEmployeeByName(String name);
}
