package com.gestion.service.matchingservice.model;



import java.util.Date;
import java.util.List;
import java.util.Map;

public class Candidate {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String location;
    private String profileSummary;
    private List<String> skills;
    private List<String> preferredJobTypes;
    private Integer yearsOfExperience;
    private Map<String, Integer> skillLevels;
    private List<Education> education;
    private List<WorkExperience> workExperience;
    private List<Certification> certifications;
    private Date registrationDate;
    private Date lastUpdated;
    private String profileStatus; // "ACTIVE", "PASSIVE", "UNAVAILABLE"
    private String cvUrl;
    private Map<String, Object> additionalAttributes;

    // Constructeurs
    public Candidate() {
    }

    public Candidate(String id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    // Getters et Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProfileSummary() {
        return profileSummary;
    }

    public void setProfileSummary(String profileSummary) {
        this.profileSummary = profileSummary;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<String> getPreferredJobTypes() {
        return preferredJobTypes;
    }

    public void setPreferredJobTypes(List<String> preferredJobTypes) {
        this.preferredJobTypes = preferredJobTypes;
    }

    public Integer getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(Integer yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public Map<String, Integer> getSkillLevels() {
        return skillLevels;
    }

    public void setSkillLevels(Map<String, Integer> skillLevels) {
        this.skillLevels = skillLevels;
    }

    public List<Education> getEducation() {
        return education;
    }

    public void setEducation(List<Education> education) {
        this.education = education;
    }

    public List<WorkExperience> getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(List<WorkExperience> workExperience) {
        this.workExperience = workExperience;
    }

    public List<Certification> getCertifications() {
        return certifications;
    }

    public void setCertifications(List<Certification> certifications) {
        this.certifications = certifications;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getProfileStatus() {
        return profileStatus;
    }

    public void setProfileStatus(String profileStatus) {
        this.profileStatus = profileStatus;
    }

    public String getCvUrl() {
        return cvUrl;
    }

    public void setCvUrl(String cvUrl) {
        this.cvUrl = cvUrl;
    }

    public Map<String, Object> getAdditionalAttributes() {
        return additionalAttributes;
    }

    public void setAdditionalAttributes(Map<String, Object> additionalAttributes) {
        this.additionalAttributes = additionalAttributes;
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", location='" + location + '\'' +
                ", yearsOfExperience=" + yearsOfExperience +
                ", skills=" + skills +
                ", profileStatus='" + profileStatus + '\'' +
                '}';
    }

    // Classes internes pour les informations détaillées
    public static class Education {
        private String degree;
        private String institution;
        private String field;
        private Date startDate;
        private Date endDate;
        private Double grade;
        private String description;

        // Constructeurs, getters et setters
        public Education() {
        }

        public String getDegree() {
            return degree;
        }

        public void setDegree(String degree) {
            this.degree = degree;
        }

        public String getInstitution() {
            return institution;
        }

        public void setInstitution(String institution) {
            this.institution = institution;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public Date getStartDate() {
            return startDate;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }

        public Double getGrade() {
            return grade;
        }

        public void setGrade(Double grade) {
            this.grade = grade;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public static class WorkExperience {
        private String title;
        private String company;
        private String location;
        private Date startDate;
        private Date endDate;
        private Boolean currentlyWorking;
        private String description;
        private List<String> responsibilities;
        private List<String> technologies;

        // Constructeurs, getters et setters
        public WorkExperience() {
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public Date getStartDate() {
            return startDate;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }

        public Boolean getCurrentlyWorking() {
            return currentlyWorking;
        }

        public void setCurrentlyWorking(Boolean currentlyWorking) {
            this.currentlyWorking = currentlyWorking;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<String> getResponsibilities() {
            return responsibilities;
        }

        public void setResponsibilities(List<String> responsibilities) {
            this.responsibilities = responsibilities;
        }

        public List<String> getTechnologies() {
            return technologies;
        }

        public void setTechnologies(List<String> technologies) {
            this.technologies = technologies;
        }

    }

    public static class Certification {
        private String name;
        private String issuingOrganization;
        private Date issueDate;
        private Date expirationDate;
        private String credentialId;
        private String credentialUrl;

        // Constructeurs, getters et setters
        public Certification() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIssuingOrganization() {
            return issuingOrganization;
        }

        public void setIssuingOrganization(String issuingOrganization) {
            this.issuingOrganization = issuingOrganization;
        }

        public Date getIssueDate() {
            return issueDate;
        }

        public void setIssueDate(Date issueDate) {
            this.issueDate = issueDate;
        }

        public Date getExpirationDate() {
            return expirationDate;
        }

        public void setExpirationDate(Date expirationDate) {
            this.expirationDate = expirationDate;
        }

        public String getCredentialId() {
            return credentialId;
        }

        public void setCredentialId(String credentialId) {
            this.credentialId = credentialId;
        }

        public String getCredentialUrl() {
            return credentialUrl;
        }

        public void setCredentialUrl(String credentialUrl) {
            this.credentialUrl = credentialUrl;
        }
    }
}