package com.recruitment.jobservice.dataaccess.entities;

import com.recruitment.jobservice.to.JobDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
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
    private String recruiterId;
    private JobDTO.JobType jobType;
    private JobDTO.WorkplaceType workplaceType;
    private JobDTO.ExperienceLevel experienceLevel;
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
}