package com.recruitment.applicationservice.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDTO {
    private String id;
    private String userId;
    private String jobId;

    // Personal information
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    // Application details
    private String availability;
    private String salaryExpectations;
    private String coverLetterUrl; // Changed from coverLetter to coverLetterUrl
    private String resumeUrl;
    private List<String> additionalFilesUrls; // Changed from additionalFiles to additionalFilesUrls

    // Application tracking
    private LocalDateTime applicationDate;
    private String status;
    private String recruiterNotes;
    private LocalDateTime lastUpdated;

    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}