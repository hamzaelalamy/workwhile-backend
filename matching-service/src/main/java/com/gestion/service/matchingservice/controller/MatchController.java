package com.gestion.service.matchingservice.controller;



import com.gestion.service.matchingservice.model.Match;
import com.gestion.service.matchingservice.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/matches") // Le chemin relatif (sera combiné avec "/api" du context-path)
public class MatchController {

    @Autowired
    private MatchRepository matchRepository;

    // Créer un match
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Match createMatch(@RequestBody Match match) {
        // Ajoutez ici votre logique de calcul de score (ex: matchScore)
        return matchRepository.save(match);
    }

    // Récupérer tous les matches
    @GetMapping
    public Iterable<Match> getAllMatches() {
        return matchRepository.findAll();
    }
}