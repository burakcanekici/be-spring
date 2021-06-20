package com.be.couchbasedemo.service;

import com.be.couchbasedemo.configuration.couchbase.CouchbaseBucketConfiguration;
import com.be.couchbasedemo.domain.couchbase.Company;
import com.be.couchbasedemo.model.request.CompanyCreateRequest;
import com.be.couchbasedemo.repository.couchbase.CompanyRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import static com.be.couchbasedemo.domain.couchbase.Company.CompanyBuilder.aCompany;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CouchbaseBucketConfiguration couchbaseBucketConfiguration;

    public CompanyService(CompanyRepository companyRepository,
                          CouchbaseBucketConfiguration couchbaseBucketConfiguration) {
        this.companyRepository = companyRepository;
        this.couchbaseBucketConfiguration = couchbaseBucketConfiguration;
    }

    public void insert(CompanyCreateRequest companyCreateRequest){
        Company company = aCompany()
                .id(DigestUtils.md5Hex(companyCreateRequest.getCompanyName()))
                .companyName(companyCreateRequest.getCompanyName())
                .location(companyCreateRequest.getLocation())
                .size(companyCreateRequest.getSize())
                .category(companyCreateRequest.getCategory())
                .build();

        companyRepository.save(company);
    }
}
