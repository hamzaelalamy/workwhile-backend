package com.recruitment.candidateservice.logic.impl;

import com.recruitment.candidateservice.client.UserServiceClient;
import com.recruitment.candidateservice.dataaccess.dao.CandidateRepository;
import com.recruitment.candidateservice.dataaccess.entities.CandidateEntity;
import com.recruitment.candidateservice.logic.api.CandidateService;
import com.recruitment.candidateservice.service.rest.exception.BadRequestException;
import com.recruitment.candidateservice.service.rest.exception.ForbiddenException;
import com.recruitment.candidateservice.service.rest.exception.ResourceNotFoundException;
import com.recruitment.candidateservice.to.CandidateDTO;
import com.recruitment.candidateservice.to.CandidateMapper;
import com.recruitment.candidateservice.to.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;
    private final UserServiceClient userServiceClient;

    @Override
    public CandidateDTO createCandidate(CandidateDTO candidateDTO, String authorizationHeader) {
        // Validate the user exists and has the right role
        ResponseEntity<UserDTO> userResponse = userServiceClient.getUserById(authorizationHeader, candidateDTO.getUserId());
        UserDTO user = userResponse.getBody();

        if (user == null || !user.isActive()) {
            throw new BadRequestException("User not found or inactive");
        }

        if (!"CANDIDATE".equals(user.getRole())) {
            throw new BadRequestException("User is not a candidate");
        }

        if (candidateRepository.existsByUserId(candidateDTO.getUserId())) {
            throw new BadRequestException("Candidate profile already exists for this user");
        }

        CandidateEntity candidateEntity = candidateMapper.candidateDTOToCandidateEntity(candidateDTO);
        CandidateEntity savedCandidate = candidateRepository.save(candidateEntity);

        return candidateMapper.candidateEntityToCandidateDTO(savedCandidate);
    }

    @Override
    public CandidateDTO getCandidateById(String id, String authorizationHeader) {
        CandidateEntity candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with id: " + id));

        return candidateMapper.candidateEntityToCandidateDTO(candidate);
    }

    @Override
    public CandidateDTO getCandidateByUserId(String userId, String authorizationHeader) {
        CandidateEntity candidate = candidateRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found for user: " + userId));

        return candidateMapper.candidateEntityToCandidateDTO(candidate);
    }

    @Override
    public List<CandidateDTO> getAllCandidates(String authorizationHeader) {
        return candidateRepository.findAll().stream()
                .map(candidateMapper::candidateEntityToCandidateDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CandidateDTO updateCandidate(String id, CandidateDTO candidateDTO, String authorizationHeader) {
        CandidateEntity existingCandidate = candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with id: " + id));

        // Check authorization - only the candidate themselves or admin can update
        ResponseEntity<UserDTO> userResponse = userServiceClient.getUserById(authorizationHeader, existingCandidate.getUserId());
        UserDTO user = userResponse.getBody();

        if (user == null) {
            throw new BadRequestException("Unable to verify user identity");
        }

        // Ensure the userId is not changed
        if (!existingCandidate.getUserId().equals(candidateDTO.getUserId())) {
            throw new BadRequestException("Cannot change the associated user");
        }

        candidateMapper.updateCandidateEntityFromDTO(candidateDTO, existingCandidate);
        CandidateEntity updatedCandidate = candidateRepository.save(existingCandidate);

        return candidateMapper.candidateEntityToCandidateDTO(updatedCandidate);
    }

    @Override
    public void deleteCandidate(String id, String authorizationHeader) {
        if (!candidateRepository.existsById(id)) {
            throw new ResourceNotFoundException("Candidate not found with id: " + id);
        }

        candidateRepository.deleteById(id);
    }

    @Override
    public List<CandidateDTO.SkillDTO> getCandidateSkills(String id, String authorizationHeader) {
        CandidateEntity candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with id: " + id));

        return candidate.getSkills().stream()
                .map(candidateMapper::skillToSkillDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CandidateDTO addSkill(String id, CandidateDTO.SkillDTO skillDTO, String authorizationHeader) {
        CandidateEntity candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with id: " + id));

        // Check if skill already exists
        boolean skillExists = candidate.getSkills().stream()
                .anyMatch(skill -> skill.getName().equalsIgnoreCase(skillDTO.getName()));

        if (skillExists) {
            throw new BadRequestException("Skill already exists for this candidate: " + skillDTO.getName());
        }

        CandidateEntity.Skill skill = candidateMapper.skillDTOToSkill(skillDTO);
        candidate.getSkills().add(skill);

        CandidateEntity updatedCandidate = candidateRepository.save(candidate);
        return candidateMapper.candidateEntityToCandidateDTO(updatedCandidate);
    }

    @Override
    public CandidateDTO updateSkill(String id, String skillName, CandidateDTO.SkillDTO skillDTO, String authorizationHeader) {
        CandidateEntity candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with id: " + id));

        // Find and update the skill
        CandidateEntity.Skill skillToUpdate = candidate.getSkills().stream()
                .filter(skill -> skill.getName().equalsIgnoreCase(skillName))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found: " + skillName));

        // If skill name is being changed, check if the new name already exists
        if (!skillName.equalsIgnoreCase(skillDTO.getName())) {
            boolean newNameExists = candidate.getSkills().stream()
                    .anyMatch(skill -> skill.getName().equalsIgnoreCase(skillDTO.getName())
                            && !skill.getName().equalsIgnoreCase(skillName));

            if (newNameExists) {
                throw new BadRequestException("Skill with new name already exists: " + skillDTO.getName());
            }
        }

        // Update skill properties
        skillToUpdate.setName(skillDTO.getName());
        skillToUpdate.setLevel(skillDTO.getLevel());
        skillToUpdate.setYearsOfExperience(skillDTO.getYearsOfExperience());

        CandidateEntity updatedCandidate = candidateRepository.save(candidate);
        return candidateMapper.candidateEntityToCandidateDTO(updatedCandidate);
    }

    @Override
    public CandidateDTO removeSkill(String id, String skillName, String authorizationHeader) {
        CandidateEntity candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with id: " + id));

        boolean removed = candidate.getSkills().removeIf(skill -> skill.getName().equalsIgnoreCase(skillName));

        if (!removed) {
            throw new ResourceNotFoundException("Skill not found: " + skillName);
        }

        CandidateEntity updatedCandidate = candidateRepository.save(candidate);
        return candidateMapper.candidateEntityToCandidateDTO(updatedCandidate);
    }
}