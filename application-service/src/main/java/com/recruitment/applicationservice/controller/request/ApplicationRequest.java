package com.recruitment.applicationservice.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationRequest {
    @NotBlank(message = "Job ID is required")
    private String jobId;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email address is required")
    @Email(message = "Email must be valid")
    private String email;

    private String coverLetterUrl; // Changed from coverLetter for consistency

    @NotBlank(message = "Resume is required")
    private String resumeUrl;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    // Optional fields
    private String availability;
    private String salaryExpectations;
    private String coverLetter;

}