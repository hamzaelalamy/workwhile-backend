package com.recruitment.applicationservice.dataaccess.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "applications")
@CompoundIndex(name = "user_job_idx", def = "{'userId': 1, 'jobId': 1}", unique = true)
public class ApplicationEntity {

    @Id
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
    private String coverLetter;
    private String resumeUrl;
    private List<String> additionalFiles;

    // Application tracking
    private LocalDateTime applicationDate;
    private ApplicationStatus status;
    private String recruiterNotes;
    private LocalDateTime lastUpdated;

    public enum ApplicationStatus {
        PENDING, VIEWED, SHORTLISTED, REJECTED, INTERVIEW, HIRED
    }
}