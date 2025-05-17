package com.recruitment.applicationservice.dataaccess.dao;

import com.recruitment.applicationservice.dataaccess.entities.ApplicationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends MongoRepository<ApplicationEntity, String> {
    List<ApplicationEntity> findByUserId(String userId);
    List<ApplicationEntity> findByJobId(String jobId);
    Optional<ApplicationEntity> findByUserIdAndJobId(String userId, String jobId);
}