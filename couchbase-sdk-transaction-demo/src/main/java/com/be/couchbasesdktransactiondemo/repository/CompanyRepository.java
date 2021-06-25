package com.be.couchbasesdktransactiondemo.repository;

import com.be.couchbasesdktransactiondemo.model.entity.Company;
import org.springframework.data.repository.CrudRepository;

public interface CompanyRepository extends CrudRepository<Company, String> {

}
