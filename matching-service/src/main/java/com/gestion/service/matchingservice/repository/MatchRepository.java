package com.gestion.service.matchingservice.repository;




import com.gestion.service.matchingservice.model.Match;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchRepository extends MongoRepository<Match, String> {

    List<Match> findByCandidateId(String candidateId);

    List<Match> findByJobId(String jobId);

    Optional<Match> findByCandidateIdAndJobId(String candidateId, String jobId);

    List<Match> findByMatchScoreGreaterThanEqual(Double minScore);

    List<Match> findByStatus(String status);

    Page<Match> findByCandidateIdOrderByMatchScoreDesc(String candidateId, Pageable pageable);

    Page<Match> findByJobIdOrderByMatchScoreDesc(String jobId, Pageable pageable);

    @Query("{'candidateId': ?0, 'matchScore': {$gte: ?1}}")
    List<Match> findCandidateMatchesWithMinScore(String candidateId, Double minScore);

    @Query("{'jobId': ?0, 'matchScore': {$gte: ?1}}")
    List<Match> findJobMatchesWithMinScore(String jobId, Double minScore);

    @Query("{'status': ?0, 'matchScore': {$gte: ?1}}")
    List<Match> findMatchesByStatusAndMinScore(String status, Double minScore);

    @Query("{'status': 'MUTUAL'}")
    List<Match> findMutualMatches();
}