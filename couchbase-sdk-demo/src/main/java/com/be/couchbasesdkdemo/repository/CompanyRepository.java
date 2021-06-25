package com.be.couchbasesdkdemo.repository;

import com.be.couchbasesdkdemo.model.entity.Company;
import org.springframework.data.repository.CrudRepository;

public interface CompanyRepository extends CrudRepository<Company, String> {

}
