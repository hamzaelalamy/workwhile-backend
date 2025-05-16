package com.recruitment.applicationservice.logic.impl;

import com.recruitment.applicationservice.controller.request.ApplicationRequest;
import com.recruitment.applicationservice.dataaccess.dao.ApplicationRepository;
import com.recruitment.applicationservice.dataaccess.entities.ApplicationEntity;
import com.recruitment.applicationservice.dataaccess.entities.ApplicationEntity.ApplicationStatus;
import com.recruitment.applicationservice.to.ApplicationDTO;
import com.recruitment.applicationservice.exception.ApplicationNotFoundException;
import com.recruitment.applicationservice.exception.DuplicateApplicationException;
import com.recruitment.applicationservice.logic.api.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;

    @Override
    public ApplicationDTO createApplication(String userId, ApplicationRequest request, List<String> additionalFiles) {
        // Check if the user has already applied to this job
        if (userId != null && !userId.isEmpty() &&
                applicationRepository.findByUserIdAndJobId(userId, request.getJobId()).isPresent()) {
            throw new DuplicateApplicationException("You have already applied to this job");
        }

        ApplicationEntity application = ApplicationEntity.builder()
                .userId(userId)
                .jobId(request.getJobId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .availability(request.getAvailability())
                .salaryExpectations(request.getSalaryExpectations())
                .coverLetter(request.getCoverLetter())
                .resumeUrl(request.getResumeUrl())
                .additionalFiles(additionalFiles)
                .applicationDate(LocalDateTime.now())
                .status(ApplicationStatus.PENDING)
                .lastUpdated(LocalDateTime.now())
                .build();

        ApplicationEntity savedApplication = applicationRepository.save(application);
        return mapToDTO(savedApplication);
    }

    @Override
    public List<ApplicationDTO> getUserApplications(String userId) {
        return applicationRepository.findByUserId(userId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ApplicationDTO> getJobApplications(String jobId) {
        return applicationRepository.findByJobId(jobId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ApplicationDTO getApplicationById(String id) {
        return applicationRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found with id: " + id));
    }

    @Override
    public ApplicationDTO updateApplicationStatus(String id, ApplicationStatus status) {
        ApplicationEntity application = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found with id: " + id));

        application.setStatus(status);
        application.setLastUpdated(LocalDateTime.now());

        ApplicationEntity updatedApplication = applicationRepository.save(application);
        return mapToDTO(updatedApplication);
    }

    @Override
    public boolean hasUserAppliedToJob(String userId, String jobId) {
        return applicationRepository.findByUserIdAndJobId(userId, jobId).isPresent();
    }

    private ApplicationDTO mapToDTO(ApplicationEntity entity) {
        return ApplicationDTO.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .jobId(entity.getJobId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .phoneNumber(entity.getPhoneNumber())
                .availability(entity.getAvailability())
                .salaryExpectations(entity.getSalaryExpectations())
                .coverLetter(entity.getCoverLetter())
                .resumeUrl(entity.getResumeUrl())
                .additionalFiles(entity.getAdditionalFiles())
                .applicationDate(entity.getApplicationDate())
                .status(entity.getStatus())
                .recruiterNotes(entity.getRecruiterNotes())
                .lastUpdated(entity.getLastUpdated())
                .build();
    }
}