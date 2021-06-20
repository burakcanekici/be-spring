package com.be.couchbasedemo.repository.couchbase;


import com.be.couchbasedemo.domain.couchbase.Company;
import org.springframework.data.repository.CrudRepository;

public interface CompanyRepository extends CrudRepository<Company, String> {

}
