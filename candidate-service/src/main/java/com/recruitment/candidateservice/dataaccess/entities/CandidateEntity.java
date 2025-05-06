package com.recruitment.candidateservice.dataaccess.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "candidates")
public class CandidateEntity {

    @Id
    private String id;

    @Indexed(unique = true)
    private String userId;

    private String title;

    private String summary;

    @Builder.Default
    private List<Skill> skills = new ArrayList<>();

    @Builder.Default
    private List<Experience> experiences = new ArrayList<>();

    @Builder.Default
    private List<Education> education = new ArrayList<>();

    private Availability availability;

    private boolean remoteOnly;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Skill {
        private String name;
        private SkillLevel level;
        private int yearsOfExperience;

        public enum SkillLevel {
            BEGINNER, INTERMEDIATE, ADVANCED, EXPERT
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Experience {
        private String company;
        private String position;
        private String description;
        private String location;
        private String startDate;
        private String endDate;
        private boolean current;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Education {
        private String institution;
        private String degree;
        private String field;
        private String startDate;
        private String endDate;
        private boolean current;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Availability {
        private boolean availableForFullTime;
        private boolean availableForPartTime;
        private boolean availableForFreelance;
        private boolean availableForContract;
        private String availableFrom;
        private String noticePeriod;
    }
}