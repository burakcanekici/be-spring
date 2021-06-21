package com.be.couchbasesdkdemo.service;

import com.be.couchbasesdkdemo.domain.Company;
import com.be.couchbasesdkdemo.model.request.CompanyCreateRequest;
import com.be.couchbasesdkdemo.repository.CompanyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.be.couchbasesdkdemo.domain.Company.CompanyBuilder.aCompany;

@Service
public class CompanyPGService {

    private final CompanyRepository companyRepository;

    public CompanyPGService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public void insertTx(CompanyCreateRequest companyCreateRequest){
        Company.CompanyBuilder comp = aCompany()
                .name(companyCreateRequest.getCompanyName())
                .location(companyCreateRequest.getLocation())
                .size(companyCreateRequest.getSize());

        if(companyCreateRequest.getCompanyName().equals("Apple#2")){
            throw new RuntimeException("test exception");
        }

        companyRepository.save(comp.build());
    }
}
