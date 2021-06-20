package com.be.couchbasedemo.controller;

import com.be.couchbasedemo.model.request.CompanyCreateRequest;
import com.be.couchbasedemo.service.CompanyService;
import com.be.couchbasedemo.service.EmployeeService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.be.couchbasedemo.model.request.EmployeeCreateRequest.EmployeeCreateRequestBuilder.anEmployeeCreateRequest;

@RestController
@RequestMapping("/couchbase")
public class CouchbaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CouchbaseController.class);

    private final CompanyService companyService;
    private final EmployeeService employeeService;

    public CouchbaseController(CompanyService companyService,
                               EmployeeService employeeService) {
        this.companyService = companyService;
        this.employeeService = employeeService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation(value = "create document")
    public void insert(@RequestBody @Valid CompanyCreateRequest companyCreateRequest) {
        companyService.insert(companyCreateRequest);

        employeeService.insert(anEmployeeCreateRequest()
                .name("Tom")
                .surname("Riddle")
                .salary(10000)
                .build()
        );

    }
}
