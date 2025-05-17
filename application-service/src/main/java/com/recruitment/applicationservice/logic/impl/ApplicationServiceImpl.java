package com.recruitment.applicationservice.logic.impl;

import com.recruitment.applicationservice.dataaccess.dao.ApplicationRepository;
import com.recruitment.applicationservice.to.ApplicationDTO;
import com.recruitment.applicationservice.to.ApplicationRequest;
import com.recruitment.applicationservice.dataaccess.entities.ApplicationEntity;
import com.recruitment.applicationservice.dataaccess.entities.ApplicationEntity.ApplicationStatus;
import com.recruitment.applicationservice.logic.api.ApplicationService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;

    @Override
    public List<ApplicationDTO> getAllApplications(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ApplicationEntity> applicationPage = applicationRepository.findAll(pageable);
        return applicationPage.getContent().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ApplicationDTO createApplication(String userId, ApplicationRequest request, List<String> additionalFiles) {
        // Check if user already applied to this job
        Optional<ApplicationEntity> existingApplication = applicationRepository.findByUserIdAndJobId(userId, request.getJobId());
        if (existingApplication.isPresent()) {
            throw new IllegalStateException("User has already applied to this job");
        }

        // Create new application
        ApplicationEntity entity = new ApplicationEntity();
        entity.setUserId(userId);
        entity.setJobId(request.getJobId());
        entity.setResumeUrl(request.getResumeUrl());
        entity.setAdditionalFilesUrls(additionalFiles);
        entity.setStatus(ApplicationStatus.SUBMITTED);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setApplicationDate(LocalDateTime.now());
        entity.setLastUpdated(LocalDateTime.now());

        ApplicationEntity savedEntity = applicationRepository.save(entity);
        return mapToDTO(savedEntity);
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
                .orElseThrow(() -> new IllegalArgumentException("Application not found with ID: " + id));
    }

    @Override
    public ApplicationDTO updateApplicationStatus(String id, ApplicationStatus status) {
        ApplicationEntity entity = applicationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Application not found with ID: " + id));

        entity.setStatus(status);
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setLastUpdated(LocalDateTime.now());

        ApplicationEntity updatedEntity = applicationRepository.save(entity);
        return mapToDTO(updatedEntity);
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
                .resumeUrl(entity.getResumeUrl())
                .coverLetterUrl(entity.getCoverLetterUrl())
                .additionalFilesUrls(entity.getAdditionalFilesUrls())
                .status(entity.getStatus().name())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .applicationDate(entity.getApplicationDate())
                .recruiterNotes(entity.getRecruiterNotes())
                .lastUpdated(entity.getLastUpdated())
                .build();
    }
}