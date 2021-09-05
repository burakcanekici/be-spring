package com.be.couchbasesdkdemo.controller;

import com.be.couchbasesdkdemo.model.request.CompanyCreateRequest;
import com.be.couchbasesdkdemo.model.request.EmployeeCreateRequest;
import com.be.couchbasesdkdemo.service.CouchbaseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/couchbase")
public class CouchbaseController {

    private final CouchbaseService couchbaseService;

    public CouchbaseController(CouchbaseService couchbaseService) {
        this.couchbaseService = couchbaseService;
    }

    @PostMapping("/company")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void insertCompany(@RequestBody @Valid CompanyCreateRequest companyCreateRequest) {
        couchbaseService.insertCompany(companyCreateRequest);
    }

    @PostMapping("/employee")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void insertEmployee(@RequestBody @Valid EmployeeCreateRequest employeeCreateRequest) {
        couchbaseService.insertEmployee(employeeCreateRequest);
    }

    @DeleteMapping("/employee/{name}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteEmployee(@PathVariable String name) {
        couchbaseService.delete(name);
    }
}
