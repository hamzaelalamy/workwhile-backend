package com.recruitment.jobservice.dataaccess.dao;

import com.recruitment.jobservice.dataaccess.entities.JobEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends MongoRepository<JobEntity, String> {

    List<JobEntity> findByRecruiterId(String recruiterId);

    List<JobEntity> findByActiveTrue();
}