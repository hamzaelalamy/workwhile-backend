package com.gestion.service.matchingservice.controller;



import com.gestion.service.matchingservice.model.Job;
import com.gestion.service.matchingservice.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jobs") // Le chemin relatif (sera combiné avec "/api" du context-path)
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    // Créer une offre d'emploi
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Job createJob(@RequestBody Job job) {
        return jobRepository.save(job);
    }

    // Récupérer toutes les offres d'emploi (exemple supplémentaire)
    @GetMapping
    public Iterable<Job> getAllJobs() {
        return jobRepository.findAll();
    }
}