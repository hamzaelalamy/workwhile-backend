package com.recruitment.jobservice.dataaccess.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "jobs")
public class JobEntity {

    @Id
    private String id;
    private String title;
    private String description;
    private String company;
    private String location;

    @Indexed
    private String recruiterId;
    private JobType jobType;
    private WorkplaceType workplaceType;
    private ExperienceLevel experienceLevel;

    @Builder.Default
    private List<String> requirements = new ArrayList<>();
    private String salaryMin;
    private String salaryMax;
    private String currency;
    private LocalDate postedDate;
    private LocalDate expiryDate;
    
    @Builder.Default
    private boolean active = true;
    
    @Builder.Default
    private int applicationCount = 0;
    
    @Builder.Default
    private List<Benefit> benefits = new ArrayList<>();
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Benefit {
        private String name;
        private String description;
    }
    
    public enum JobType {
        FULL_TIME,
        PART_TIME,
        CONTRACT,
        FREELANCE,
        INTERNSHIP
    }
    
    public enum WorkplaceType {
        ONSITE,
        REMOTE,
        HYBRID
    }
    
    public enum ExperienceLevel {
        ENTRY,
        JUNIOR,
        MID,
        SENIOR,
        LEAD,
        EXECUTIVE
    }
}