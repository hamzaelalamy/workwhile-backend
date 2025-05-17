package com.gestion.service.matchingservice.repository;



import com.gestion.service.matchingservice.model.Job;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface JobRepository extends MongoRepository<Job, String> {

    List<Job> findByRequiredSkillsIn(List<String> skills);

    List<Job> findByLocationContainingIgnoreCase(String location);

    List<Job> findByIsActiveTrue();

    List<Job> findByExperienceRequiredLessThanEqual(Integer experienceYears);

    List<Job> findByJobType(String jobType);

    List<Job> findByClosingDateAfter(Date date);

    @Query("{'requiredSkills': {$in: ?0}, 'location': {$regex: ?1, $options: 'i'}, 'isActive': true}")
    List<Job> findActiveJobsWithSkillsInLocation(List<String> skills, String location);

    @Query("{'requiredSkills': {$in: ?0}, 'experienceRequired': {$lte: ?1}, 'isActive': true}")
    List<Job> findActiveJobsWithSkillsAndMaxExperience(List<String> skills, Integer maxExperience);

    @Query("{'companyId': ?0, 'isActive': true}")
    List<Job> findActiveJobsByCompanyId(String companyId);
}