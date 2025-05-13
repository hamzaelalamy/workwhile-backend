package com.gestion.service.matchingservice.dto;



import java.util.List;

public class MatchRequestDTO {

    private String candidateId;
    private String jobId;
    private List<String> skills;
    private Integer experienceYears;
    private String location;
    private List<String> preferredJobTypes;

    // Constructeurs
    public MatchRequestDTO() {
    }

    public MatchRequestDTO(String candidateId, String jobId) {
        this.candidateId = candidateId;
        this.jobId = jobId;
    }

    public MatchRequestDTO(String candidateId, String jobId,
                           List<String> skills, Integer experienceYears,
                           String location, List<String> preferredJobTypes) {
        this.candidateId = candidateId;
        this.jobId = jobId;
        this.skills = skills;
        this.experienceYears = experienceYears;
        this.location = location;
        this.preferredJobTypes = preferredJobTypes;
    }

    // Getters et Setters
    public String getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getPreferredJobTypes() {
        return preferredJobTypes;
    }

    public void setPreferredJobTypes(List<String> preferredJobTypes) {
        this.preferredJobTypes = preferredJobTypes;
    }
}