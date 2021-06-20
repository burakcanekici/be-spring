package com.be.couchbasedemo.configuration.couchbase;

import com.be.couchbasedemo.domain.couchbase.Company;
import com.be.couchbasedemo.domain.couchbase.Employee;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.cluster.ClusterInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.data.couchbase.repository.config.RepositoryOperationsMapping;

import java.util.List;

@Configuration
@Profile("!unit-test")
public class CouchbaseBucketConfiguration extends AbstractCouchbaseConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(CouchbaseBucketConfiguration.class);

    private final CouchbaseClusterProperties couchbaseClusterProperties;

    public CouchbaseBucketConfiguration(CouchbaseClusterProperties couchbaseClusterProperties) {
        this.couchbaseClusterProperties = couchbaseClusterProperties;
    }

    @Override
    protected List<String> getBootstrapHosts() {
        return couchbaseClusterProperties.getCompany().getBootstrapHosts();
    }

    @Override
    protected String getBucketName() {
        return couchbaseClusterProperties.getCompany().getBucketName();
    }

    @Override
    protected String getBucketPassword() {
        return couchbaseClusterProperties.getCompany().getPassword();
    }

    @Override
    public void configureRepositoryOperationsMapping(RepositoryOperationsMapping baseMapping) {
        try {
            baseMapping
                    .mapEntity(Company.class, companyTemplate())
                    .mapEntity(Employee.class, employeeTemplate());
        } catch (Exception e) {
            LOGGER.error("CouchbaseConfiguration base mapping exception", e);
        }
    }

    //<editor-fold desc="companyTemplate">

    @Bean("companyCluster")
    public Cluster companyCluster() {
        return CouchbaseCluster.create(getBootstrapHosts());
    }

    @Bean("companyClusterInfo")
    public ClusterInfo companyClusterInfo() {
        final var bucketProperties = couchbaseClusterProperties.getCompany();
        final var cluster = companyCluster();
        return cluster.clusterManager(bucketProperties.getUserName(), bucketProperties.getPassword()).info();
    }

    @Bean("companyBucket")
    public Bucket companyBucket() {
        Cluster cluster = this.companyCluster();
        if (!this.getUsername().contentEquals(this.getBucketName())) {
            cluster.authenticate(this.getUsername(), this.getBucketPassword());
        } else if (!this.getBucketPassword().isEmpty()) {
            return cluster.openBucket(couchbaseClusterProperties.getCompany().getBucketName(), couchbaseClusterProperties.getCompany().getPassword());
        }

        return cluster.openBucket(couchbaseClusterProperties.getCompany().getBucketName());
    }

    @Bean("companyTemplate")
    public CouchbaseTemplate companyTemplate() throws Exception {
        final var template = new CouchbaseTemplate(
                companyClusterInfo(),
                companyBucket(),
                mappingCouchbaseConverter(),
                translationService()
        );
        template.setDefaultConsistency(getDefaultConsistency());

        return template;
    }

    //</editor-fold>

    //<editor-fold desc="employeeTemplate">

    @Bean("employeeCluster")
    public Cluster employeeCluster() {
        return CouchbaseCluster.create(getBootstrapHosts());
    }

    @Bean("employeeClusterInfo")
    public ClusterInfo employeeClusterInfo() {
        final var bucketProperties = couchbaseClusterProperties.getEmployee();
        final var cluster = employeeCluster();
        return cluster.clusterManager(bucketProperties.getUserName(), bucketProperties.getPassword()).info();
    }

    @Bean("employeeBucket")
    public Bucket employeeBucket() {
        Cluster cluster = this.employeeCluster();
        if (!this.getUsername().contentEquals(this.getBucketName())) {
            cluster.authenticate(this.getUsername(), this.getBucketPassword());
        } else if (!this.getBucketPassword().isEmpty()) {
            return cluster.openBucket(couchbaseClusterProperties.getEmployee().getBucketName(), couchbaseClusterProperties.getEmployee().getPassword());
        }

        return cluster.openBucket(couchbaseClusterProperties.getEmployee().getBucketName());
    }

    @Bean("employeeTemplate")
    public CouchbaseTemplate employeeTemplate() throws Exception {
        final var template = new CouchbaseTemplate(
                employeeClusterInfo(),
                employeeBucket(),
                mappingCouchbaseConverter(),
                translationService()
        );
        template.setDefaultConsistency(getDefaultConsistency());

        return template;
    }

    //</editor-fold>
}
