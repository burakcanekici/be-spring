package com.be.couchbaseclientsdkdemo.service;

import com.be.couchbaseclientsdkdemo.model.entity.Company;
import com.be.couchbaseclientsdkdemo.model.entity.Employee;
import com.be.couchbaseclientsdkdemo.model.request.CompanyCreateRequest;
import com.be.couchbaseclientsdkdemo.repository.CompanyRepository;
import com.be.couchbaseclientsdkdemo.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import static com.be.couchbaseclientsdkdemo.model.entity.Company.CompanyBuilder.aCompany;
import static com.be.couchbaseclientsdkdemo.model.entity.Employee.EmployeeBuilder.anEmployee;

@Service
public class CouchbaseService {

    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;

    public CouchbaseService(CompanyRepository companyRepository,
                            EmployeeRepository employeeRepository) {
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
    }

    public void insert(CompanyCreateRequest companyCreateRequest){
        Company company = aCompany()
                .companyName(companyCreateRequest.getCompanyName())
                .location(companyCreateRequest.getLocation())
                .size(companyCreateRequest.getSize())
                .build();

        companyRepository.save(company);

        Employee employee = anEmployee()
                .name("Tom")
                .surname("Riddle")
                .build();

        employeeRepository.save(employee);
    }

    public void insertEmployee(String name, String surname){
        Employee employee = anEmployee()
                .name(name)
                .surname(surname)
                .build();

        employeeRepository.save(employee);
    }

    public void delete(String name){
        employeeRepository.deleteEmployeeByName(name);
    }
}
