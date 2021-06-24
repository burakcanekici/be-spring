package com.be.couhcbasesdkdemo.controller;

import com.be.couhcbasesdkdemo.model.request.CompanyCreateRequest;
import com.be.couhcbasesdkdemo.service.CouchbaseService;
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

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void insert(@RequestBody @Valid CompanyCreateRequest companyCreateRequest) {
        couchbaseService.insert(companyCreateRequest);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void insertEmployee(@RequestBody String name, @RequestBody String surname) {
        couchbaseService.insertEmployee(name, surname);
    }

    @DeleteMapping("/employee/{name}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable String name) {
        couchbaseService.delete(name);
    }
}
