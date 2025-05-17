package com.recruitment.applicationservice.logic.api;

import com.recruitment.applicationservice.to.ApplicationDTO;
import com.recruitment.applicationservice.to.ApplicationRequest;
import com.recruitment.applicationservice.dataaccess.entities.ApplicationEntity.ApplicationStatus;

import java.util.List;

public interface ApplicationService {
    /**
     * Get all applications with pagination
     *
     * @param page page number (zero-based)
     * @param size number of items per page
     * @return list of application DTOs
     */
    List<ApplicationDTO> getAllApplications(int page, int size);

    /**
     * Create a new job application
     *
     * @param userId user ID submitting the application
     * @param request application request data
     * @param additionalFiles list of file references
     * @return the created application as DTO
     */
    ApplicationDTO createApplication(String userId, ApplicationRequest request, List<String> additionalFiles);

    /**
     * Get all applications for a specific user
     *
     * @param userId the user ID
     * @return list of application DTOs
     */
    List<ApplicationDTO> getUserApplications(String userId);

    /**
     * Get all applications for a specific job
     *
     * @param jobId the job ID
     * @return list of application DTOs
     */
    List<ApplicationDTO> getJobApplications(String jobId);

    /**
     * Get an application by its ID
     *
     * @param id application ID
     * @return application DTO
     */
    ApplicationDTO getApplicationById(String id);

    /**
     * Update the status of an application
     *
     * @param id application ID
     * @param status new application status
     * @return updated application DTO
     */
    ApplicationDTO updateApplicationStatus(String id, ApplicationStatus status);

    /**
     * Check if a user has already applied to a specific job
     *
     * @param userId user ID
     * @param jobId job ID
     * @return true if user has already applied, false otherwise
     */
    boolean hasUserAppliedToJob(String userId, String jobId);
}