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
    public ResponseEntity<JobDTO> createJob(@RequestBody JobPostingRequest request, Authentication authentication) {
        // Set recruiter ID from the authentication
        String recruiterId = authentication.getName();
        request.setRecruiterId(recruiterId);

        return ResponseEntity.status(HttpStatus.CREATED).body(jobService.createJob(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobDTO> updateJob(
            @PathVariable String id,
            @RequestBody JobPostingRequest request,
            Authentication authentication) {

        // Get the authenticated user
        String currentUserId = authentication.getName();

        try {
            // Get job to check ownership
            JobDTO job = jobService.getJobById(id);

            // Check if user is ADMIN or the owner of the job
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

            if (isAdmin || job.getRecruiterId().equals(currentUserId)) {
                return ResponseEntity.ok(jobService.updateJob(id, request));
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable String id, Authentication authentication) {
        // Get the authenticated user
        String currentUserId = authentication.getName();

        try {
            // Get job to check ownership
            JobDTO job = jobService.getJobById(id);

            // Check if user is ADMIN or the owner of the job
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

            if (isAdmin || job.getRecruiterId().equals(currentUserId)) {
                jobService.deleteJob(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activateJob(@PathVariable String id, Authentication authentication) {
        // Get the authenticated user
        String currentUserId = authentication.getName();

        try {
            // Get job to check ownership
            JobDTO job = jobService.getJobById(id);

            // Check if user is ADMIN or the owner of the job
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

            if (isAdmin || job.getRecruiterId().equals(currentUserId)) {
                jobService.activateJob(id);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateJob(@PathVariable String id, Authentication authentication) {
        // Get the authenticated user
        String currentUserId = authentication.getName();

        try {
            // Get job to check ownership
            JobDTO job = jobService.getJobById(id);

            // Check if user is ADMIN or the owner of the job
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

            if (isAdmin || job.getRecruiterId().equals(currentUserId)) {
                jobService.deactivateJob(id);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
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
    public ResponseEntity<Void> incrementApplicationCount(
            @PathVariable String id,
            Authentication authentication) {
        // Only authenticated users can increment application count
        if (authentication != null) {
            jobService.incrementApplicationCount(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}