package com.recruitment.candidateservice.logic.api;

import com.recruitment.candidateservice.to.CandidateDTO;

import java.util.List;

public interface CandidateService {

    CandidateDTO createCandidate(CandidateDTO candidateDTO, String authorizationHeader);

    CandidateDTO getCandidateById(String id, String authorizationHeader);

    CandidateDTO getCandidateByUserId(String userId, String authorizationHeader);

    List<CandidateDTO> getAllCandidates(String authorizationHeader);

    CandidateDTO updateCandidate(String id, CandidateDTO candidateDTO, String authorizationHeader);

    void deleteCandidate(String id, String authorizationHeader);

    List<CandidateDTO.SkillDTO> getCandidateSkills(String id, String authorizationHeader);

    CandidateDTO addSkill(String id, CandidateDTO.SkillDTO skillDTO, String authorizationHeader);

    CandidateDTO updateSkill(String id, String skillName, CandidateDTO.SkillDTO skillDTO, String authorizationHeader);

    CandidateDTO removeSkill(String id, String skillName, String authorizationHeader);
}