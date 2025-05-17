package com.gestion.service.matchingservice.controller;

import com.gestion.service.matchingservice.model.Candidate;
import com.gestion.service.matchingservice.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
// Supprimez le "/candidates" ici ↓
@RequestMapping("/")
public class CandidateController {

    @Autowired
    private CandidateRepository candidateRepository;

    // Ajoutez le chemin complet ici ↓
    @PostMapping("/candidates")
    public Candidate createCandidate(@RequestBody Candidate candidate) {
        return candidateRepository.save(candidate);
    }
}