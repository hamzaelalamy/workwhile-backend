package com.recruitment.applicationservice.controller;

import com.recruitment.applicationservice.controller.request.ApplicationRequest;
import com.recruitment.applicationservice.to.ApplicationStatusRequest;
import com.recruitment.applicationservice.dataaccess.entities.ApplicationEntity;
import com.recruitment.applicationservice.logic.api.ApplicationService;
import com.recruitment.applicationservice.to.ApplicationDTO;
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

    @PostMapping
    public ResponseEntity<ApplicationDTO> createApplication(
            @RequestHeader(value = "userId", required = false) String userIdHeader,
            @RequestBody @Valid ApplicationRequest request,
            Authentication authentication) {

        try {
            // Get userId from header or authentication
            String userId = userIdHeader;
            if ((userId == null || userId.isEmpty()) && authentication != null) {
                userId = authentication.getName();
            }
            if (userId == null || userId.isEmpty()) {
                userId = "admin-user-id-123"; // Default for testing
            }

            // Create the application with an empty additional files list
            ApplicationDTO applicationDTO = applicationService.createApplication(userId, request, new ArrayList<>());
            return ResponseEntity.status(HttpStatus.CREATED).body(applicationDTO);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Error processing application: " + e.getMessage(), e);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<List<ApplicationDTO>> getUserApplications(
            @RequestHeader(value = "userId", required = false) String userIdHeader,
            Authentication authentication) {

        // Get userId from header or authentication
        String userId = userIdHeader;
        if ((userId == null || userId.isEmpty()) && authentication != null) {
            userId = authentication.getName();
        }
        if (userId == null || userId.isEmpty()) {
            userId = "admin-user-id-123";
        }

        return ResponseEntity.ok(applicationService.getUserApplications(userId));
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<ApplicationDTO>> getJobApplications(@PathVariable String jobId) {
        return ResponseEntity.ok(applicationService.getJobApplications(jobId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationDTO> getApplicationById(@PathVariable String id) {
        return ResponseEntity.ok(applicationService.getApplicationById(id));
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

    @GetMapping("/check")
    public ResponseEntity<Boolean> hasUserAppliedToJob(
            @RequestParam(required = false) String userId,
            @RequestParam String jobId,
            Authentication authentication) {

        // Get userId from request param or authentication
        String effectiveUserId = userId;
        if ((effectiveUserId == null || effectiveUserId.isEmpty()) && authentication != null) {
            effectiveUserId = authentication.getName();
        }
        if (effectiveUserId == null || effectiveUserId.isEmpty()) {
            effectiveUserId = "admin-user-id-123";
        }

        return ResponseEntity.ok(applicationService.hasUserAppliedToJob(effectiveUserId, jobId));
    }
}