package com.recruitment.jobservice.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobPostingRequest {

    private String title;
    private String description;
    private String company;
    private String location;
    private String recruiterId;
    private JobDTO.JobType jobType;
    private JobDTO.WorkplaceType workplaceType;
    private JobDTO.ExperienceLevel experienceLevel;
    private List<String> requirements;
    private String salaryMin;
    private String salaryMax;
    private String currency;
    private LocalDate expiryDate;
    private List<JobDTO.Benefit> benefits;
}