package com.recruitment.candidateservice.dataaccess.dao;

import com.recruitment.candidateservice.dataaccess.entities.CandidateEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CandidateRepository extends MongoRepository<CandidateEntity, String> {

    Optional<CandidateEntity> findByUserId(String userId);

    boolean existsByUserId(String userId);
}