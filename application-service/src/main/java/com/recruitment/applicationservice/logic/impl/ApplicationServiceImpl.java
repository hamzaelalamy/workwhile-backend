package com.recruitment.applicationservice.logic.impl;

import com.recruitment.applicationservice.dataaccess.dao.ApplicationRepository;
import com.recruitment.applicationservice.dataaccess.entities.ApplicationEntity;
import com.recruitment.applicationservice.exception.ApplicationNotFoundException;
import com.recruitment.applicationservice.exception.DuplicateApplicationException;
import com.recruitment.applicationservice.feign.JobServiceClient;
import com.recruitment.applicationservice.feign.UserServiceClient;
import com.recruitment.applicationservice.logic.api.ApplicationService;
import com.recruitment.applicationservice.to.ApplicationDTO;
import com.recruitment.applicationservice.to.ApplicationRequest;
import com.recruitment.applicationservice.to.JobDTO;
import com.recruitment.applicationservice.to.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the ApplicationService that provides business logic for application-related operations.
 * This service works with the ApplicationRepository to perform CRUD operations and more complex
 * business logic for job applications.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobServiceClient jobServiceClient;
    private final UserServiceClient userServiceClient;

    /**
     * Apply for a job
     *
     * @param userId The ID of the user applying
     * @param request The application details
     * @return The created application
     * @throws DuplicateApplicationException if the user has already applied to this job
     */
    @Override
    public ApplicationDTO applyForJob(String userId, ApplicationRequest request) {
        // Check if user has already applied to this job
        if (applicationRepository.existsByUserIdAndJobId(userId, request.getJobId())) {
            throw new DuplicateApplicationException("You have already applied for this job");
        }

        // Verify the job exists by calling the job service
        JobDTO job = jobServiceClient.getJobById(request.getJobId());

        // Verify the user exists by calling the user service
        UserDTO user = userServiceClient.getUserById(userId);

        // Create new application
        ApplicationEntity application = ApplicationEntity.builder()
                .userId(userId)
                .jobId(request.getJobId())
                .applicationDate(LocalDateTime.now())
                .coverLetter(request.getCoverLetter())
                .resumeUrl(request.getResumeUrl())
                .status(ApplicationEntity.ApplicationStatus.PENDING)
                .lastUpdated(LocalDateTime.now())
                .build();

        ApplicationEntity savedApplication = applicationRepository.save(application);

        // Increment application count in job service
        jobServiceClient.incrementApplicationCount(request.getJobId());

        return mapToDTO(savedApplication, job, user);
    }

    /**
     * Get all applications submitted by a user
     *
     * @param userId The ID of the user
     * @return List of applications submitted by the user
     */
    @Override
    @Transactional(readOnly = true)
    public List<ApplicationDTO> getUserApplications(String userId) {
        List<ApplicationEntity> applications = applicationRepository.findByUserId(userId);

        // Verify the user exists
        UserDTO user = userServiceClient.getUserById(userId);

        return applications.stream()
                .map(application -> {
                    JobDTO job = jobServiceClient.getJobById(application.getJobId());
                    return mapToDTO(application, job, user);
                })
                .collect(Collectors.toList());
    }

    /**
     * Get all applications for a specific job
     *
     * @param jobId The ID of the job
     * @return List of applications for the job
     */
    @Override
    @Transactional(readOnly = true)
    public List<ApplicationDTO> getJobApplications(String jobId) {
        List<ApplicationEntity> applications = applicationRepository.findByJobId(jobId);

        // Verify the job exists
        JobDTO job = jobServiceClient.getJobById(jobId);

        return applications.stream()
                .map(application -> {
                    UserDTO user = userServiceClient.getUserById(application.getUserId());
                    return mapToDTO(application, job, user);
                })
                .collect(Collectors.toList());
    }

    /**
     * Get an application by ID
     *
     * @param applicationId The ID of the application
     * @return The application details
     * @throws ApplicationNotFoundException if no application with the given ID exists
     */
    @Override
    @Transactional(readOnly = true)
    public ApplicationDTO getApplicationById(String applicationId) {
        ApplicationEntity application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found with id: " + applicationId));

        JobDTO job = jobServiceClient.getJobById(application.getJobId());
        UserDTO user = userServiceClient.getUserById(application.getUserId());

        return mapToDTO(application, job, user);
    }

    /**
     * Update the status of an application
     *
     * @param applicationId The ID of the application
     * @param status The new status
     * @param recruiterNotes Optional notes from recruiter
     * @return The updated application
     * @throws ApplicationNotFoundException if no application with the given ID exists
     */
    @Override
    public ApplicationDTO updateApplicationStatus(String applicationId, ApplicationEntity.ApplicationStatus status, String recruiterNotes) {
        ApplicationEntity application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found with id: " + applicationId));

        application.setStatus(status);
        application.setRecruiterNotes(recruiterNotes);
        application.setLastUpdated(LocalDateTime.now());

        ApplicationEntity updatedApplication = applicationRepository.save(application);

        JobDTO job = jobServiceClient.getJobById(application.getJobId());
        UserDTO user = userServiceClient.getUserById(application.getUserId());

        return mapToDTO(updatedApplication, job, user);
    }

    /**
     * Check if a user has already applied to a job
     *
     * @param userId The ID of the user
     * @param jobId The ID of the job
     * @return true if the user has already applied, false otherwise
     */
    @Override
    @Transactional(readOnly = true)
    public boolean hasUserAppliedToJob(String userId, String jobId) {
        return applicationRepository.existsByUserIdAndJobId(userId, jobId);
    }

    /**
     * Map an application entity to a DTO
     *
     * @param entity The application entity
     * @param job The job DTO
     * @param user The user DTO
     * @return The application DTO
     */
    private ApplicationDTO mapToDTO(ApplicationEntity entity, JobDTO job, UserDTO user) {
        return ApplicationDTO.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .jobId(entity.getJobId())
                .jobTitle(job.getTitle())
                .company(job.getCompany())
                .userName(user.getFirstName() + " " + user.getLastName())
                .userEmail(user.getEmail())
                .applicationDate(entity.getApplicationDate())
                .status(entity.getStatus().name())
                .coverLetter(entity.getCoverLetter())
                .resumeUrl(entity.getResumeUrl())
                .lastUpdated(entity.getLastUpdated())
                .build();
    }
}