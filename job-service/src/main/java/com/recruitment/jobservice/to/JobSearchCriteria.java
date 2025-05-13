package com.recruitment.jobservice.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobSearchCriteria {

    private String title;
    private String location;
    private JobDTO.JobType jobType;
    private JobDTO.WorkplaceType workplaceType;
    private JobDTO.ExperienceLevel experienceLevel;
    private String skill;
    private String salaryMinimum;
    private boolean activeOnly = true;
}