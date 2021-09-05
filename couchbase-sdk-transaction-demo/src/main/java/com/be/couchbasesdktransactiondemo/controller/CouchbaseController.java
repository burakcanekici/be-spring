package com.be.couchbasesdktransactiondemo.controller;

import com.be.couchbasesdktransactiondemo.model.request.CompanyCreateRequest;
import com.be.couchbasesdktransactiondemo.service.CouchbaseService;
import com.be.couchbasesdktransactiondemo.service.CouchbaseTxService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/transaction")
public class CouchbaseController {

    private final CouchbaseService couchbaseService;
    private final CouchbaseTxService couchbaseTxService;

    public CouchbaseController(CouchbaseService couchbaseService,
                               CouchbaseTxService couchbaseTxService) {
        this.couchbaseService = couchbaseService;
        this.couchbaseTxService = couchbaseTxService;
    }

    @PostMapping("/failed")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void failed(@RequestBody @Valid CompanyCreateRequest companyCreateRequest) {
        couchbaseService.insert(companyCreateRequest);
    }

    @PostMapping("/failedTx")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void failedTx(@RequestBody @Valid CompanyCreateRequest companyCreateRequest) {
        couchbaseTxService.insertTx(companyCreateRequest, true);
    }

    @PostMapping("/succeedTx")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void succeedTx(@RequestBody @Valid CompanyCreateRequest companyCreateRequest) {
        couchbaseTxService.insertTx(companyCreateRequest, false);
    }
}
