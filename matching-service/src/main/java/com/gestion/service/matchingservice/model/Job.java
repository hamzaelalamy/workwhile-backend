package com.gestion.service.matchingservice.model;



import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "jobs")
public class Job {
    @Id
    private String id;
    private String title;
    private String description;
    private String companyId;
    private String location;
    private List<String> requiredSkills;
    private Integer experienceRequired;
    private String jobType;
    private Double salary;
    private Date postingDate;
    private Date closingDate;
    private boolean isActive;

    // Constructors
    public Job() {
    }

    public Job(String title, String description, String companyId, String location,
               List<String> requiredSkills, Integer experienceRequired, String jobType,
               Double salary, Date postingDate, Date closingDate, boolean isActive) {
        this.title = title;
        this.description = description;
        this.companyId = companyId;
        this.location = location;
        this.requiredSkills = requiredSkills;
        this.experienceRequired = experienceRequired;
        this.jobType = jobType;
        this.salary = salary;
        this.postingDate = postingDate;
        this.closingDate = closingDate;
        this.isActive = isActive;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(List<String> requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public Integer getExperienceRequired() {
        return experienceRequired;
    }

    public void setExperienceRequired(Integer experienceRequired) {
        this.experienceRequired = experienceRequired;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Date getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(Date postingDate) {
        this.postingDate = postingDate;
    }

    public Date getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Date closingDate) {
        this.closingDate = closingDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}