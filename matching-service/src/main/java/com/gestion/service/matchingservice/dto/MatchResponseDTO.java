package com.gestion.service.matchingservice.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class MatchResponseDTO {

    private String id;
    private String candidateId;
    private String candidateName;
    private String jobId;
    private String jobTitle;
    private String companyName;
    private Double matchScore;
    private String status;
    private Date matchDate;
    private List<String> matchedSkills;
    private Map<String, String> matchDetails;
    private Boolean candidateInterested;
    private Boolean employerInterested;

    // Constructeurs
    public MatchResponseDTO() {
    }

    public MatchResponseDTO(String id, String candidateId, String candidateName,
                            String jobId, String jobTitle, String companyName,
                            Double matchScore, String status, Date matchDate) {
        this.id = id;
        this.candidateId = candidateId;
        this.candidateName = candidateName;
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.matchScore = matchScore;
        this.status = status;
        this.matchDate = matchDate;
    }

    // Getters et Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Double getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(Double matchScore) {
        this.matchScore = matchScore;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(Date matchDate) {
        this.matchDate = matchDate;
    }

    public List<String> getMatchedSkills() {
        return matchedSkills;
    }

    public void setMatchedSkills(List<String> matchedSkills) {
        this.matchedSkills = matchedSkills;
    }

    public Map<String, String> getMatchDetails() {
        return matchDetails;
    }

    public void setMatchDetails(Map<String, String> matchDetails) {
        this.matchDetails = matchDetails;
    }

    public Boolean getCandidateInterested() {
        return candidateInterested;
    }

    public void setCandidateInterested(Boolean candidateInterested) {
        this.candidateInterested = candidateInterested;
    }

    public Boolean getEmployerInterested() {
        return employerInterested;
    }

    public void setEmployerInterested(Boolean employerInterested) {
        this.employerInterested = employerInterested;
    }

    @Override
    public String toString() {
        return "MatchResponseDTO{" +
                "id='" + id + '\'' +
                ", candidateId='" + candidateId + '\'' +
                ", candidateName='" + candidateName + '\'' +
                ", jobId='" + jobId + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", companyName='" + companyName + '\'' +
                ", matchScore=" + matchScore +
                ", status='" + status + '\'' +
                ", matchDate=" + matchDate +
                ", matchedSkills=" + matchedSkills +
                ", matchDetails=" + matchDetails +
                ", candidateInterested=" + candidateInterested +
                ", employerInterested=" + employerInterested +
                '}';
    }

    // Builder pattern pour faciliter la création d'instances
    public static class Builder {
        private final MatchResponseDTO dto;

        public Builder() {
            dto = new MatchResponseDTO();
        }

        public Builder withId(String id) {
            dto.setId(id);
            return this;
        }

        public Builder withCandidateId(String candidateId) {
            dto.setCandidateId(candidateId);
            return this;
        }

        public Builder withCandidateName(String candidateName) {
            dto.setCandidateName(candidateName);
            return this;
        }

        public Builder withJobId(String jobId) {
            dto.setJobId(jobId);
            return this;
        }

        public Builder withJobTitle(String jobTitle) {
            dto.setJobTitle(jobTitle);
            return this;
        }

        public Builder withCompanyName(String companyName) {
            dto.setCompanyName(companyName);
            return this;
        }

        public Builder withMatchScore(Double matchScore) {
            dto.setMatchScore(matchScore);
            return this;
        }

        public Builder withStatus(String status) {
            dto.setStatus(status);
            return this;
        }

        public Builder withMatchDate(Date matchDate) {
            dto.setMatchDate(matchDate);
            return this;
        }

        public Builder withMatchedSkills(List<String> matchedSkills) {
            dto.setMatchedSkills(matchedSkills);
            return this;
        }

        public Builder withMatchDetails(Map<String, String> matchDetails) {
            dto.setMatchDetails(matchDetails);
            return this;
        }

        public Builder withCandidateInterested(Boolean candidateInterested) {
            dto.setCandidateInterested(candidateInterested);
            return this;
        }

        public Builder withEmployerInterested(Boolean employerInterested) {
            dto.setEmployerInterested(employerInterested);
            return this;
        }

        public MatchResponseDTO build() {
            return dto;
        }
    }
}