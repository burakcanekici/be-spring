package com.be.couchbaseclientsdkdemo.repository;

import com.be.couchbaseclientsdkdemo.model.entity.Company;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.repository.CrudRepository;

public interface CompanyRepository extends CrudRepository<Company, String> {

}
