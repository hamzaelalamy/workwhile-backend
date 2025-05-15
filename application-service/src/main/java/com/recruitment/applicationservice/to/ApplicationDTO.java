package com.recruitment.applicationservice.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDTO {
    private String id;
    private String userId;
    private String jobId;
    private String jobTitle; // From job service
    private String company; // From job service
    private String userName; // From user service
    private String userEmail; // From user service
    private LocalDateTime applicationDate;
    private String status;
    private String coverLetter;
    private String resumeUrl;
    private LocalDateTime lastUpdated;
}