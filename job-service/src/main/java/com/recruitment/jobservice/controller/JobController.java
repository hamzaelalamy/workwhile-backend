package com.recruitment.jobservice.controller;

import com.recruitment.jobservice.logic.api.JobService;
import com.recruitment.jobservice.to.JobDTO;
import com.recruitment.jobservice.to.JobPostingRequest;
import com.recruitment.jobservice.to.JobSearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @GetMapping
    public ResponseEntity<List<JobDTO>> getAllJobs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        System.out.println("Page: " + page + ", Size: " + size);
        return ResponseEntity.ok(jobService.getAllJobs(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getJobById(@PathVariable String id) {
        JobDTO job = jobService.getJobById(id);
        if (job == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No job offers found with ID: " + id);
        }
        return ResponseEntity.ok(job);
    }

    @PostMapping("/create_offer")
    public ResponseEntity<JobDTO> createJob(@RequestBody JobPostingRequest request) {
        // Remove authentication requirement for now since it causes issues with the authentication service
        // For testing, you can set a default recruiterId or leave it as provided in the request
        return ResponseEntity.status(HttpStatus.CREATED).body(jobService.createJob(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobDTO> updateJob(
            @PathVariable String id,
            @RequestBody JobPostingRequest request) {
        try {
            return ResponseEntity.ok(jobService.updateJob(id, request));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable String id) {
        try {
            jobService.deleteJob(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activateJob(@PathVariable String id) {
        try {
            jobService.activateJob(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateJob(@PathVariable String id) {
        try {
            jobService.deactivateJob(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<JobDTO>> searchJobs(JobSearchCriteria criteria) {
        return ResponseEntity.ok(jobService.searchJobs(criteria));
    }

    @GetMapping("/featured")
    public ResponseEntity<List<JobDTO>> getFeaturedJobs(
            @RequestParam(defaultValue = "5") int limit) {
        return ResponseEntity.ok(jobService.getFeaturedJobs(limit));
    }

    @GetMapping("/recent")
    public ResponseEntity<List<JobDTO>> getRecentJobs(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(jobService.getRecentJobs(limit));
    }

    @GetMapping("/recruiter/{recruiterId}")
    public ResponseEntity<List<JobDTO>> getJobsByRecruiterId(
            @PathVariable String recruiterId) {
        return ResponseEntity.ok(jobService.getJobsByRecruiterId(recruiterId));
    }

    @PostMapping("/{id}/application")
    public ResponseEntity<Void> incrementApplicationCount(@PathVariable String id) {
        jobService.incrementApplicationCount(id);
        return ResponseEntity.ok().build();
    }
}