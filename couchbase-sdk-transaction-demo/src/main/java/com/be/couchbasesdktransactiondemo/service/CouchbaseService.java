package com.be.couchbasesdktransactiondemo.service;

import com.be.couchbasesdktransactiondemo.model.entity.Company;
import com.be.couchbasesdktransactiondemo.model.request.CompanyCreateRequest;
import com.be.couchbasesdktransactiondemo.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import static com.be.couchbasesdktransactiondemo.model.entity.Company.CompanyBuilder.aCompany;


@Service
public class CouchbaseService {

    private final CompanyRepository companyRepository;

    public CouchbaseService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public void insert(CompanyCreateRequest companyCreateRequest){
        for(int i=0;i<5;i++){
            Company company = aCompany()
                    .companyName(companyCreateRequest.getCompanyName() + "#" + String.valueOf(i))
                    .location(companyCreateRequest.getLocation())
                    .size(companyCreateRequest.getSize())
                    .build();

            if(i == 3){
                throw new RuntimeException("No Tx, the items remaining will not be saved!");
            }

            companyRepository.save(company);
        }
    }


}
