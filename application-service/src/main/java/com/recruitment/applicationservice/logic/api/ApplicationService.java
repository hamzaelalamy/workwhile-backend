package com.recruitment.applicationservice.logic.api;

import com.recruitment.applicationservice.controller.request.ApplicationRequest;
import com.recruitment.applicationservice.dataaccess.entities.ApplicationEntity.ApplicationStatus;
import com.recruitment.applicationservice.to.ApplicationDTO;

import java.util.List;

public interface ApplicationService {
    ApplicationDTO createApplication(String userId, ApplicationRequest request, List<String> additionalFiles);
    List<ApplicationDTO> getUserApplications(String userId);
    List<ApplicationDTO> getJobApplications(String jobId);
    ApplicationDTO getApplicationById(String id);
    ApplicationDTO updateApplicationStatus(String id, ApplicationStatus status);
    boolean hasUserAppliedToJob(String userId, String jobId);
}