package com.recruitment.candidateservice.to;

import com.recruitment.candidateservice.dataaccess.entities.CandidateEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CandidateDTO {

    private String id;

    @NotBlank(message = "User ID is required")
    private String userId;

    private String title;

    private String summary;

    @Builder.Default
    private List<@Valid SkillDTO> skills = new ArrayList<>();

    @Builder.Default
    private List<@Valid ExperienceDTO> experiences = new ArrayList<>();

    @Builder.Default
    private List<@Valid EducationDTO> education = new ArrayList<>();

    @Valid
    private AvailabilityDTO availability;

    private boolean remoteOnly;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SkillDTO {
        @NotBlank(message = "Skill name is required")
        private String name;

        @NotNull(message = "Skill level is required")
        private CandidateEntity.Skill.SkillLevel level;

        private int yearsOfExperience;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExperienceDTO {
        @NotBlank(message = "Company name is required")
        private String company;

        @NotBlank(message = "Position is required")
        private String position;

        private String description;
        private String location;

        @NotBlank(message = "Start date is required")
        private String startDate;

        private String endDate;
        private boolean current;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EducationDTO {
        @NotBlank(message = "Institution name is required")
        private String institution;

        @NotBlank(message = "Degree is required")
        private String degree;

        @NotBlank(message = "Field of study is required")
        private String field;

        @NotBlank(message = "Start date is required")
        private String startDate;

        private String endDate;
        private boolean current;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AvailabilityDTO {
        private boolean availableForFullTime;
        private boolean availableForPartTime;
        private boolean availableForFreelance;
        private boolean availableForContract;
        private String availableFrom;
        private String noticePeriod;
    }
}