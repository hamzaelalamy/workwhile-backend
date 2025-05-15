package com.recruitment.applicationservice.dataaccess.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

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
    private LocalDateTime applicationDate;
    private ApplicationStatus status;
    private String coverLetter;
    private String resumeUrl;
    private String recruiterNotes;
    private LocalDateTime lastUpdated;

    public enum ApplicationStatus {
        PENDING, VIEWED, SHORTLISTED, REJECTED, INTERVIEW, HIRED
    }
}