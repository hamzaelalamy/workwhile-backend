package com.recruitment.jobservice.service.rest.impl;

import com.recruitment.jobservice.logic.api.JobService;
import com.recruitment.jobservice.to.JobDTO;
import com.recruitment.jobservice.to.JobPostingRequest;
import com.recruitment.jobservice.to.JobSearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_RECRUITER')")
    public ResponseEntity<JobDTO> createJob(
            @RequestBody JobPostingRequest request,
            Authentication authentication) {
        
        // Extract user details from authentication
        String recruiterId = authentication.getName(); // This is the user ID from JWT
        
        // Ensure only the authenticated recruiter can create their own job
        request.setRecruiterId(recruiterId);
        
        JobDTO createdJob = jobService.createJob(request);
        return new ResponseEntity<>(createdJob, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable String id) {
        JobDTO job = jobService.getJobById(id);
        return ResponseEntity.ok(job);
    }

    @GetMapping
    public ResponseEntity<List<JobDTO>> getAllJobs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<JobDTO> jobs = jobService.getAllJobs(page, size);
        return ResponseEntity.ok(jobs);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_RECRUITER')")
    public ResponseEntity<JobDTO> updateJob(
            @PathVariable String id,
            @RequestBody JobPostingRequest request,
            Authentication authentication) {
        
        // Get existing job to check ownership
        JobDTO existingJob = jobService.getJobById(id);
        
        // Only allow the original recruiter to update the job
        if (!existingJob.getRecruiterId().equals(authentication.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        // Don't allow changing the recruiterId
        request.setRecruiterId(existingJob.getRecruiterId());
        
        JobDTO updatedJob = jobService.updateJob(id, request);
        return ResponseEntity.ok(updatedJob);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteJob(@PathVariable String id) {
        jobService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activate")
    @PreAuthorize("hasAuthority('ROLE_RECRUITER') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> activateJob(
            @PathVariable String id,
            Authentication authentication) {
        
        // Allow activation by recruiter (if they own the job) or any admin
        if (hasAuthority(authentication, "ROLE_RECRUITER")) {
            JobDTO job = jobService.getJobById(id);
            if (!job.getRecruiterId().equals(authentication.getName())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }
        
        jobService.activateJob(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasAuthority('ROLE_RECRUITER') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deactivateJob(
            @PathVariable String id,
            Authentication authentication) {
        
        // Allow deactivation by recruiter (if they own the job) or any admin
        if (hasAuthority(authentication, "ROLE_RECRUITER")) {
            JobDTO job = jobService.getJobById(id);
            if (!job.getRecruiterId().equals(authentication.getName())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }
        
        jobService.deactivateJob(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/recruiter")
    @PreAuthorize("hasAuthority('ROLE_RECRUITER')")
    public ResponseEntity<List<JobDTO>> getMyJobs(Authentication authentication) {
        String recruiterId = authentication.getName();
        List<JobDTO> jobs = jobService.getJobsByRecruiterId(recruiterId);
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/search")
    public ResponseEntity<List<JobDTO>> searchJobs(@RequestBody JobSearchCriteria criteria) {
        List<JobDTO> jobs = jobService.searchJobs(criteria);
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/public/featured")
    public ResponseEntity<List<JobDTO>> getFeaturedJobs(
            @RequestParam(defaultValue = "5") int limit) {
        List<JobDTO> jobs = jobService.getFeaturedJobs(limit);
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/public/recent")
    public ResponseEntity<List<JobDTO>> getRecentJobs(
            @RequestParam(defaultValue = "10") int limit) {
        List<JobDTO> jobs = jobService.getRecentJobs(limit);
        return ResponseEntity.ok(jobs);
    }
    
    // Helper method to check if authentication has a specific authority
    private boolean hasAuthority(Authentication authentication, String authority) {
        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(authority));
    }
}