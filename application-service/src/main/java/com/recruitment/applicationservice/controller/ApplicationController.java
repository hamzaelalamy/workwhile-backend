package com.recruitment.applicationservice.controller;

import com.recruitment.applicationservice.to.ApplicationDTO;
import com.recruitment.applicationservice.to.ApplicationRequest;
import com.recruitment.applicationservice.to.ApplicationStatusRequest;
import com.recruitment.applicationservice.dataaccess.entities.ApplicationEntity;
import com.recruitment.applicationservice.logic.api.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/v1/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @GetMapping
    public ResponseEntity<List<ApplicationDTO>> getAllApplications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(applicationService.getAllApplications(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationDTO> getApplicationById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(applicationService.getApplicationById(id));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Application not found with ID: " + id);
        }
    }

    @PostMapping
    public ResponseEntity<ApplicationDTO> createApplication(@RequestBody @Valid ApplicationRequest request) {
        try {
            // Using a default or extracted user ID for testing
            String userId = "user-id"; // In production, extract from security context

            ApplicationDTO applicationDTO = applicationService.createApplication(
                    userId, request, new ArrayList<>());
            return ResponseEntity.status(HttpStatus.CREATED).body(applicationDTO);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Error creating application: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApplicationDTO> updateApplicationStatus(
            @PathVariable String id,
            @RequestBody ApplicationStatusRequest request) {
        try {
            ApplicationEntity.ApplicationStatus status = ApplicationEntity.ApplicationStatus.valueOf(request.getStatus());
            return ResponseEntity.ok(applicationService.updateApplicationStatus(id, status));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid status value. Valid values are: " +
                            Arrays.toString(ApplicationEntity.ApplicationStatus.values()));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ApplicationDTO>> getUserApplications(@PathVariable String userId) {
        return ResponseEntity.ok(applicationService.getUserApplications(userId));
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<ApplicationDTO>> getJobApplications(@PathVariable String jobId) {
        return ResponseEntity.ok(applicationService.getJobApplications(jobId));
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> hasUserAppliedToJob(
            @RequestParam String userId,
            @RequestParam String jobId) {
        return ResponseEntity.ok(applicationService.hasUserAppliedToJob(userId, jobId));
    }
}