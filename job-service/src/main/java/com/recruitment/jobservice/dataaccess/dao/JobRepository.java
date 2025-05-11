package com.recruitment.jobservice.dataaccess.dao;

import com.recruitment.jobservice.dataaccess.entities.JobEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface JobRepository extends MongoRepository<JobEntity, String> {
    
    // Find a job by its title
    Optional<JobEntity> findByTitle(String title);
    
    // Check if a job with a specific title exists
    boolean existsByTitle(String title);
    
    // Find jobs by recruiter ID
    List<JobEntity> findByRecruiterId(String recruiterId);
    
    // Find active jobs
    List<JobEntity> findByActiveTrue();
    
    // Find jobs by location
    List<JobEntity> findByLocation(String location);
}