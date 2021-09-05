package com.be.couchbasesdktransactiondemo.service;

import com.be.couchbasesdktransactiondemo.model.entity.Company;
import com.be.couchbasesdktransactiondemo.model.request.CompanyCreateRequest;
import com.be.couchbasesdktransactiondemo.repository.CompanyRepository;
import com.couchbase.transactions.Transactions;
import com.couchbase.transactions.error.TransactionCommitAmbiguous;
import com.couchbase.transactions.error.TransactionFailed;
import org.springframework.data.couchbase.CouchbaseClientFactory;
import org.springframework.data.couchbase.core.convert.MappingCouchbaseConverter;
import org.springframework.data.couchbase.core.mapping.CouchbaseDocument;
import org.springframework.stereotype.Service;

import static com.be.couchbasesdktransactiondemo.model.entity.Company.CompanyBuilder.aCompany;

@Service
public class CouchbaseTxService {

    private final Transactions transactions;
    private final CouchbaseClientFactory couchbaseClientFactory;
    private final MappingCouchbaseConverter mappingCouchbaseConverter;

    public CouchbaseTxService(Transactions transactions,
                              CouchbaseClientFactory couchbaseClientFactory,
                              MappingCouchbaseConverter mappingCouchbaseConverter) {
        this.transactions = transactions;
        this.couchbaseClientFactory = couchbaseClientFactory;
        this.mappingCouchbaseConverter = mappingCouchbaseConverter;
    }

    public void insertTx(CompanyCreateRequest companyCreateRequest, Boolean isFailed){
        try{
            transactions.run(ctx -> {
                for(int i=0;i<5;i++){
                    Company company = aCompany()
                            .companyName(companyCreateRequest.getCompanyName() + "#" + i)
                            .location(companyCreateRequest.getLocation())
                            .size(companyCreateRequest.getSize())
                            .build();

                    CouchbaseDocument target = new CouchbaseDocument();
                    mappingCouchbaseConverter.write(company, target);

                    if(isFailed && i == 3){
                        throw new RuntimeException("Tx is failed, rollback!");
                    }

                    ctx.insert(couchbaseClientFactory.getDefaultCollection(), company.getId(), target.getContent());
                }

                ctx.commit();
            });
        } catch (TransactionCommitAmbiguous e) {
            System.err.println("Transaction possibly committed");
        } catch (TransactionFailed e) {
            System.err.println("Transaction did not reach commit point");
        }
    }
}
