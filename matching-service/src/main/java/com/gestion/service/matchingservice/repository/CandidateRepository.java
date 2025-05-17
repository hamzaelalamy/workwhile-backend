package com.gestion.service.matchingservice.repository;

import com.gestion.service.matchingservice.model.Candidate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateRepository extends MongoRepository<Candidate, String> {

    List<Candidate> findBySkillsIn(List<String> skills);

    // Modifier cette méthode
    List<Candidate> findByYearsOfExperienceGreaterThanEqual(Integer yearsOfExperience);

    List<Candidate> findByLocationContainingIgnoreCase(String location);

    // Modifier cette requête
    @Query("{'skills': {$all: ?0}, 'yearsOfExperience': {$gte: ?1}}")
    List<Candidate> findCandidatesBySkillsAndMinimumExperience(List<String> skills, Integer minExperience);

    @Query("{'skills': {$in: ?0}}")
    List<Candidate> findCandidatesWithAnySkill(List<String> skills);

    @Query("{'preferredJobTypes': {$in: ?0}}")
    List<Candidate> findCandidatesByPreferredJobTypes(List<String> jobTypes);

    // Méthode pour trouver les candidats par compétence avec un niveau minimum
    @Query("{'skillLevels.?0': {$gte: ?1}}")
    List<Candidate> findCandidatesBySkillWithMinimumLevel(String skill, Integer minimumLevel);

    // Méthode pour trouver les candidats actifs avec des compétences spécifiques
    @Query("{'profileStatus': 'ACTIVE', 'skills': {$all: ?0}}")
    List<Candidate> findActiveCandidatesWithSkills(List<String> skills);

    // Méthode pour trouver les candidats disponibles pour une localisation spécifique
    @Query("{'profileStatus': {$ne: 'UNAVAILABLE'}, 'location': {$regex: ?0, $options: 'i'}}")
    List<Candidate> findAvailableCandidatesByLocation(String location);

    // Modifier cette requête également
    @Query("{'skills': {$in: ?0}, 'yearsOfExperience': {$gte: ?1}, 'location': {$regex: ?2, $options: 'i'}}")
    List<Candidate> suggestCandidates(List<String> desiredSkills, Integer minimumExperience, String location);
}