package com.recruitment.applicationservice.logic.api;

import com.recruitment.applicationservice.dataaccess.entities.ApplicationEntity;
import com.recruitment.applicationservice.to.ApplicationDTO;
import com.recruitment.applicationservice.to.ApplicationRequest;

import java.util.List;

public interface ApplicationService {

    /**
     * Apply for a job
     *
     * @param userId The ID of the user applying
     * @param request The application details
     * @return The created application
     * @throws DuplicateApplicationException if the user has already applied to this job
     */
    ApplicationDTO applyForJob(String userId, ApplicationRequest request);

    /**
     * Get all applications submitted by a user
     *
     * @param userId The ID of the user
     * @return List of applications submitted by the user
     */
    List<ApplicationDTO> getUserApplications(String userId);

    /**
     * Get all applications for a specific job
     *
     * @param jobId The ID of the job
     * @return List of applications for the job
     */
    List<ApplicationDTO> getJobApplications(String jobId);

    /**
     * Get an application by ID
     *
     * @param applicationId The ID of the application
     * @return The application details
     * @throws ApplicationNotFoundException if no application with the given ID exists
     */
    ApplicationDTO getApplicationById(String applicationId);

    /**
     * Update the status of an application
     *
     * @param applicationId The ID of the application
     * @param status The new status
     * @param recruiterNotes Optional notes from recruiter
     * @return The updated application
     * @throws ApplicationNotFoundException if no application with the given ID exists
     */
    ApplicationDTO updateApplicationStatus(String applicationId, ApplicationEntity.ApplicationStatus status, String recruiterNotes);

    /**
     * Check if a user has already applied to a job
     *
     * @param userId The ID of the user
     * @param jobId The ID of the job
     * @return true if the user has already applied, false otherwise
     */
    boolean hasUserAppliedToJob(String userId, String jobId);
}