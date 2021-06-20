package com.be.couchbasedemo.service;

import com.be.couchbasedemo.domain.couchbase.Company;
import com.be.couchbasedemo.domain.couchbase.Employee;
import com.be.couchbasedemo.model.request.CompanyCreateRequest;
import com.be.couchbasedemo.model.request.EmployeeCreateRequest;
import com.be.couchbasedemo.repository.couchbase.CompanyRepository;
import com.be.couchbasedemo.repository.couchbase.EmployeeRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import static com.be.couchbasedemo.domain.couchbase.Company.CompanyBuilder.aCompany;
import static com.be.couchbasedemo.domain.couchbase.Employee.EmployeeBuilder.anEmployee;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void insert(EmployeeCreateRequest employeeCreateRequest){
        Employee employee = anEmployee()
                .id(DigestUtils.md5Hex(employeeCreateRequest.getName() + employeeCreateRequest.getSurname()))
                .name(employeeCreateRequest.getName())
                .surname(employeeCreateRequest.getSurname())
                .salary(employeeCreateRequest.getSalary())
                .build();

        employeeRepository.save(employee);
    }
}
