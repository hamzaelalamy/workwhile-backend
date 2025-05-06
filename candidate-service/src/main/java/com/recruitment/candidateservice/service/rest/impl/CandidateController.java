package com.recruitment.candidateservice.service.rest.impl;

import com.recruitment.candidateservice.logic.api.CandidateService;
import com.recruitment.candidateservice.to.CandidateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/candidates")
@RequiredArgsConstructor
@Tag(name = "Candidate API", description = "API for candidate profile management")
public class CandidateController {

    private final CandidateService candidateService;

    @PostMapping
    @Operation(summary = "Create a new candidate profile")
    public ResponseEntity<CandidateDTO> createCandidate(
            @RequestHeader("Authorization") String authorizationHeader,
            @Valid @RequestBody CandidateDTO candidateDTO) {
        return new ResponseEntity<>(
                candidateService.createCandidate(candidateDTO, authorizationHeader),
                HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get candidate by ID")
    public ResponseEntity<CandidateDTO> getCandidateById(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable String id) {
        return ResponseEntity.ok(candidateService.getCandidateById(id, authorizationHeader));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get candidate by user ID")
    public ResponseEntity<CandidateDTO> getCandidateByUserId(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable String userId) {
        return ResponseEntity.ok(candidateService.getCandidateByUserId(userId, authorizationHeader));
    }

    @GetMapping
    @Operation(summary = "Get all candidates")
    public ResponseEntity<List<CandidateDTO>> getAllCandidates(
            @RequestHeader("Authorization") String authorizationHeader) {
        return ResponseEntity.ok(candidateService.getAllCandidates(authorizationHeader));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a candidate profile")
    public ResponseEntity<CandidateDTO> updateCandidate(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable String id,
            @Valid @RequestBody CandidateDTO candidateDTO) {
        return ResponseEntity.ok(candidateService.updateCandidate(id, candidateDTO, authorizationHeader));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a candidate profile")
    public ResponseEntity<Void> deleteCandidate(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable String id) {
        candidateService.deleteCandidate(id, authorizationHeader);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/skills")
    @Operation(summary = "Get all skills for a candidate")
    public ResponseEntity<List<CandidateDTO.SkillDTO>> getCandidateSkills(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable String id) {
        return ResponseEntity.ok(candidateService.getCandidateSkills(id, authorizationHeader));
    }

    @PostMapping("/{id}/skills")
    @Operation(summary = "Add a new skill to a candidate")
    public ResponseEntity<CandidateDTO> addSkill(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable String id,
            @Valid @RequestBody CandidateDTO.SkillDTO skillDTO) {
        return ResponseEntity.ok(candidateService.addSkill(id, skillDTO, authorizationHeader));
    }

    @PutMapping("/{id}/skills/{skillName}")
    @Operation(summary = "Update a skill for a candidate")
    public ResponseEntity<CandidateDTO> updateSkill(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable String id,
            @PathVariable String skillName,
            @Valid @RequestBody CandidateDTO.SkillDTO skillDTO) {
        return ResponseEntity.ok(candidateService.updateSkill(id, skillName, skillDTO, authorizationHeader));
    }

    @DeleteMapping("/{id}/skills/{skillName}")
    @Operation(summary = "Remove a skill from a candidate")
    public ResponseEntity<CandidateDTO> removeSkill(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable String id,
            @PathVariable String skillName) {
        return ResponseEntity.ok(candidateService.removeSkill(id, skillName, authorizationHeader));
    }
}