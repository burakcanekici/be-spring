package com.be.couchbasesdkdemo.controller;

import com.be.couchbasesdkdemo.model.request.CompanyCreateRequest;
import com.be.couchbasesdkdemo.service.CompanyPGService;
import com.be.couchbasesdkdemo.service.CouchbaseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static com.be.couchbasesdkdemo.model.request.CompanyCreateRequest.CompanyCreateRequestBuilder.aCompanyCreateRequest;

@RestController
@RequestMapping("/couchbase")
public class CouchbaseController {

    private final CouchbaseService couchbaseService;

    public CouchbaseController(CouchbaseService couchbaseService) {
        this.couchbaseService = couchbaseService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation(value = "create document")
    public void insert(@RequestBody @Valid CompanyCreateRequest companyCreateRequest) throws Exception {
        List<CompanyCreateRequest> companies = new ArrayList<>();
        String baseName = companyCreateRequest.getCompanyName();
        for(int i =0;i<10;i++){
            companies.add(aCompanyCreateRequest()
                    .companyName(baseName + "#" + String.valueOf(i))
                    .location(companyCreateRequest.getLocation())
                    .size(companyCreateRequest.getSize())
                    .build());
        }
        couchbaseService.insertTx(companies);
    }
}
