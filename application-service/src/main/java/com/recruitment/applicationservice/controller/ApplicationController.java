package com.recruitment.applicationservice.controller;

import com.recruitment.applicationservice.dataaccess.entities.ApplicationEntity;
import com.recruitment.applicationservice.logic.api.ApplicationService;
import com.recruitment.applicationservice.to.ApplicationDTO;
import com.recruitment.applicationservice.to.ApplicationRequest;
import com.recruitment.applicationservice.to.ApplicationStatusRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for application-related operations.
 * Provides endpoints for applying to jobs, getting application lists, and updating application status.
 */
@RestController
@RequestMapping("/v1/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    /**
     * Apply for a job
     *
     * @param userId User ID from header
     * @param request The application request
     * @return The created application
     */
    @PostMapping
    public ResponseEntity<ApplicationDTO> applyForJob(
            @RequestHeader("user-id") String userId,
            @Valid @RequestBody ApplicationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(applicationService.applyForJob(userId, request));
    }

    /**
     * Get all applications for the current user
     *
     * @param userId User ID from header
     * @return List of applications submitted by the user
     */
    @GetMapping("/user")
    public ResponseEntity<List<ApplicationDTO>> getUserApplications(
            @RequestHeader("user-id") String userId) {
        return ResponseEntity.ok(applicationService.getUserApplications(userId));
    }

    /**
     * Get all applications for a specific job (for recruiters)
     *
     * @param jobId The job ID
     * @return List of applications for the job
     */
    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<ApplicationDTO>> getJobApplications(
            @PathVariable String jobId) {
        return ResponseEntity.ok(applicationService.getJobApplications(jobId));
    }

    /**
     * Get a specific application by ID
     *
     * @param id The application ID
     * @return The application details
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApplicationDTO> getApplicationById(
            @PathVariable String id) {
        return ResponseEntity.ok(applicationService.getApplicationById(id));
    }

    /**
     * Update the status of an application
     *
     * @param id The application ID
     * @param request The status update request
     * @return The updated application
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApplicationDTO> updateApplicationStatus(
            @PathVariable String id,
            @Valid @RequestBody ApplicationStatusRequest request) {

        ApplicationEntity.ApplicationStatus status =
                ApplicationEntity.ApplicationStatus.valueOf(request.getStatus());

        return ResponseEntity.ok(
                applicationService.updateApplicationStatus(id, status, request.getRecruiterNotes())
        );
    }

    /**
     * Check if a user has already applied to a job
     *
     * @param userId User ID from header
     * @param jobId The job ID
     * @return Boolean indicating if the user has already applied
     */
    @GetMapping("/check")
    public ResponseEntity<Boolean> hasUserAppliedToJob(
            @RequestHeader("user-id") String userId,
            @RequestParam String jobId) {
        return ResponseEntity.ok(applicationService.hasUserAppliedToJob(userId, jobId));
    }
}