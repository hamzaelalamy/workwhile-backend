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
public class JobDTO {

    private String id;
    private String title;
    private String description;
    private String company;
    private String location;
    private String recruiterId;
    private JobType jobType;
    private WorkplaceType workplaceType;
    private ExperienceLevel experienceLevel;
    private List<String> requirements;
    private String salaryMin;
    private String salaryMax;
    private String currency;
    private LocalDate postedDate;
    private LocalDate expiryDate;
    private boolean active;
    private int applicationCount;
    private List<Benefit> benefits;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Benefit {
        private String name;
        private String description;
    }

    public enum JobType {
        FULL_TIME, PART_TIME, CONTRACT, TEMPORARY, INTERNSHIP
    }

    public enum WorkplaceType {
        REMOTE, ONSITE, HYBRID
    }

    public enum ExperienceLevel {
        ENTRY_LEVEL, MID_LEVEL, SENIOR, EXECUTIVE
    }
}