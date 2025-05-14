package com.recruitment.jobservice.controller;

import com.recruitment.jobservice.logic.api.JobService;
import com.recruitment.jobservice.to.JobDTO;
import com.recruitment.jobservice.to.JobPostingRequest;
import com.recruitment.jobservice.to.JobSearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @GetMapping
    public ResponseEntity<List<JobDTO>> getAllJobs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(jobService.getAllJobs(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable String id, Authentication authentication) {
        JobDTO job = jobService.getJobById(id);

        // If user is a RECRUITER, check if they own this job
        if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_RECRUITER")) &&
                !authentication.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {

            String recruiterId = authentication.getName(); // Assuming username is the recruiterId
            if (!job.getRecruiterId().equals(recruiterId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }
        }

        return ResponseEntity.ok(job);
    }

    @PostMapping("/create_offer")
    public ResponseEntity<JobDTO> createJob(@RequestBody JobPostingRequest request, Authentication authentication) {
        // If user is a RECRUITER, ensure they can only create jobs with their own recruiterId
        if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_RECRUITER")) &&
                !authentication.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {

            String recruiterId = authentication.getName(); // Assuming username is the recruiterId
            if (!request.getRecruiterId().equals(recruiterId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(jobService.createJob(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobDTO> updateJob(
            @PathVariable String id,
            @RequestBody JobPostingRequest request,
            Authentication authentication) {

        // If user is a RECRUITER, ensure they can only update their own jobs
        if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_RECRUITER")) &&
                !authentication.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {

            JobDTO existingJob = jobService.getJobById(id);
            String recruiterId = authentication.getName(); // Assuming username is the recruiterId

            if (!existingJob.getRecruiterId().equals(recruiterId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }

            // Ensure they can't change the recruiterId
            if (!request.getRecruiterId().equals(recruiterId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }
        }

        return ResponseEntity.ok(jobService.updateJob(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable String id, Authentication authentication) {
        // If user is a RECRUITER, ensure they can only delete their own jobs
        if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_RECRUITER")) &&
                !authentication.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {

            JobDTO existingJob = jobService.getJobById(id);
            String recruiterId = authentication.getName(); // Assuming username is the recruiterId

            if (!existingJob.getRecruiterId().equals(recruiterId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }

        jobService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activateJob(@PathVariable String id, Authentication authentication) {
        // Similar check as delete for RECRUITER role
        if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_RECRUITER")) &&
                !authentication.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {

            JobDTO existingJob = jobService.getJobById(id);
            String recruiterId = authentication.getName();

            if (!existingJob.getRecruiterId().equals(recruiterId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }

        jobService.activateJob(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateJob(@PathVariable String id, Authentication authentication) {
        // Similar check as delete for RECRUITER role
        if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_RECRUITER")) &&
                !authentication.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {

            JobDTO existingJob = jobService.getJobById(id);
            String recruiterId = authentication.getName();

            if (!existingJob.getRecruiterId().equals(recruiterId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }

        jobService.deactivateJob(id);
        return ResponseEntity.ok().build();
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
            @PathVariable String recruiterId,
            Authentication authentication) {

        // If user is a RECRUITER, they can only view their own jobs
        if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_RECRUITER")) &&
                !authentication.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {

            String authenticatedRecruiterId = authentication.getName();

            if (!recruiterId.equals(authenticatedRecruiterId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }

        return ResponseEntity.ok(jobService.getJobsByRecruiterId(recruiterId));
    }

    @PostMapping("/{id}/application")
    public ResponseEntity<Void> incrementApplicationCount(@PathVariable String id) {
        jobService.incrementApplicationCount(id);
        return ResponseEntity.ok().build();
    }
}