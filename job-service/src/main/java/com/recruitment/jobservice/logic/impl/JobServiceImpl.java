package com.recruitment.jobservice.logic.impl;

import com.recruitment.jobservice.dataaccess.dao.JobRepository;
import com.recruitment.jobservice.dataaccess.entities.JobEntity;
import com.recruitment.jobservice.logic.api.JobService;
import com.recruitment.jobservice.service.rest.exception.ResourceAccessDeniedException;
import com.recruitment.jobservice.to.JobDTO;
import com.recruitment.jobservice.to.JobPostingRequest;
import com.recruitment.jobservice.to.JobSearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the JobService that provides business logic for job-related operations.
 * This service works with the JobRepository to perform CRUD operations and more complex
 * business logic for job listings.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    /**
     * Create a new job posting
     *
     * @param request The job posting request with all job details
     * @return The newly created job as a DTO
     */
    @Override
    public JobDTO createJob(JobPostingRequest request) {
        // Validate request
        validateJobRequest(request);

        // Map request to entity
        JobEntity jobEntity = mapToEntity(request);

        // Set default values
        jobEntity.setPostedDate(LocalDate.now());
        jobEntity.setActive(true);
        jobEntity.setApplicationCount(0);

        // Save to database
        JobEntity savedJob = jobRepository.save(jobEntity);
        return mapToDTO(savedJob);
    }

    /**
     * Retrieve a job by its ID
     *
     * @param id The ID of the job to retrieve
     * @return The job data as a DTO
     * @throws RuntimeException if no job with the given ID exists
     */
    @Override
    @Transactional(readOnly = true)
    public JobDTO getJobById(String id) {
        JobEntity jobEntity = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + id));
        return mapToDTO(jobEntity);
    }

    /**
     * Get a paginated list of all jobs
     *
     * @param page The page number (0-based)
     * @param size The page size
     * @return A list of jobs for the requested page
     */
    @Override
    @Transactional(readOnly = true)
    public List<JobDTO> getAllJobs(int page, int size) {
        return jobRepository.findAll(PageRequest.of(page, size, Sort.by("postedDate").descending()))
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get all jobs posted by a specific recruiter
     *
     * @param recruiterId The ID of the recruiter
     * @return A list of jobs posted by the recruiter
     */
    @Override
    @Transactional(readOnly = true)
    public List<JobDTO> getJobsByRecruiterId(String recruiterId) {
        if (recruiterId == null || recruiterId.isEmpty()) {
            throw new IllegalArgumentException("Recruiter ID cannot be null or empty");
        }

        return jobRepository.findByRecruiterId(recruiterId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing job
     *
     * @param id      The ID of the job to update
     * @param request The updated job data
     * @return The updated job as a DTO
     * @throws RuntimeException if no job with the given ID exists
     */
    @Override
    public JobDTO updateJob(String id, JobPostingRequest request) {
        // The controller already checks ownership, but it's good to have a backup check
        JobEntity jobEntity = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + id));

        // In JobServiceImpl.java
        if (!jobEntity.getRecruiterId().equals(request.getRecruiterId())) {
            throw new ResourceAccessDeniedException("You can only update your own job postings");
        }

        updateEntityFromRequest(jobEntity, request);
        JobEntity updatedJob = jobRepository.save(jobEntity);
        return mapToDTO(updatedJob);
    }

    /**
     * Delete a job by its ID
     *
     * @param id The ID of the job to delete
     * @throws RuntimeException if no job with the given ID exists
     */
    @Override
    public void deleteJob(String id) {
        if (!jobRepository.existsById(id)) {
            throw new RuntimeException("Job not found with id: " + id);
        }
        jobRepository.deleteById(id);
    }

    /**
     * Activate a job (make it visible in searches)
     *
     * @param id The ID of the job to activate
     * @throws RuntimeException if no job with the given ID exists
     */
    @Override
    public void activateJob(String id) {
        JobEntity jobEntity = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + id));

        jobEntity.setActive(true);
        jobRepository.save(jobEntity);
    }

    /**
     * Deactivate a job (hide it from searches)
     *
     * @param id The ID of the job to deactivate
     * @throws RuntimeException if no job with the given ID exists
     */
    @Override
    public void deactivateJob(String id) {
        JobEntity jobEntity = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + id));

        jobEntity.setActive(false);
        jobRepository.save(jobEntity);
    }

    /**
     * Search for jobs based on various criteria
     *
     * @param criteria The search criteria
     * @return A list of jobs matching the criteria
     */
    @Override
    @Transactional(readOnly = true)
    public List<JobDTO> searchJobs(JobSearchCriteria criteria) {
        // Start with active jobs unless specified otherwise
        List<JobEntity> jobs = criteria.isActiveOnly()
                ? jobRepository.findByActiveTrue()
                : jobRepository.findAll();

        // Apply filters based on criteria
        if (criteria.getTitle() != null && !criteria.getTitle().isEmpty()) {
            jobs = jobs.stream()
                    .filter(job -> job.getTitle().toLowerCase().contains(criteria.getTitle().toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (criteria.getLocation() != null && !criteria.getLocation().isEmpty()) {
            jobs = jobs.stream()
                    .filter(job -> job.getLocation().toLowerCase().contains(criteria.getLocation().toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (criteria.getJobType() != null) {
            jobs = jobs.stream()
                    .filter(job -> job.getJobType() == criteria.getJobType())
                    .collect(Collectors.toList());
        }

        if (criteria.getWorkplaceType() != null) {
            jobs = jobs.stream()
                    .filter(job -> job.getWorkplaceType() == criteria.getWorkplaceType())
                    .collect(Collectors.toList());
        }

        if (criteria.getExperienceLevel() != null) {
            jobs = jobs.stream()
                    .filter(job -> job.getExperienceLevel() == criteria.getExperienceLevel())
                    .collect(Collectors.toList());
        }

        if (criteria.getSkill() != null && !criteria.getSkill().isEmpty()) {
            jobs = jobs.stream()
                    .filter(job -> job.getRequirements().stream()
                            .anyMatch(skill -> skill.toLowerCase().contains(criteria.getSkill().toLowerCase())))
                    .collect(Collectors.toList());
        }

        if (criteria.getSalaryMinimum() != null && !criteria.getSalaryMinimum().isEmpty()) {
            try {
                double minSalary = Double.parseDouble(criteria.getSalaryMinimum());
                jobs = jobs.stream()
                        .filter(job -> {
                            try {
                                double jobMinSalary = Double.parseDouble(job.getSalaryMin());
                                return jobMinSalary >= minSalary;
                            } catch (NumberFormatException e) {
                                return false;
                            }
                        })
                        .collect(Collectors.toList());
            } catch (NumberFormatException e) {
                // Skip salary filtering if the minimum salary is not a valid number
            }
        }

        return jobs.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Increment the application count for a job
     *
     * @param id The ID of the job
     * @throws RuntimeException if no job with the given ID exists
     */
    @Override
    public void incrementApplicationCount(String id) {
        JobEntity jobEntity = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + id));

        jobEntity.setApplicationCount(jobEntity.getApplicationCount() + 1);
        jobRepository.save(jobEntity);
    }

    /**
     * Get featured jobs (could be based on various factors)
     *
     * @param limit The maximum number of jobs to return
     * @return A list of featured jobs
     */
    @Override
    @Transactional(readOnly = true)
    public List<JobDTO> getFeaturedJobs(int limit) {
        // This could be based on various factors like premium listings, high salary, etc.
        // For simplicity, we're just returning active jobs sorted by posted date
        return jobRepository.findByActiveTrue().stream()
                .sorted((j1, j2) -> j2.getPostedDate().compareTo(j1.getPostedDate()))
                .limit(limit)
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get most recently posted jobs
     *
     * @param limit The maximum number of jobs to return
     * @return A list of the most recent jobs
     */
    @Override
    @Transactional(readOnly = true)
    public List<JobDTO> getRecentJobs(int limit) {
        return jobRepository.findByActiveTrue().stream()
                .sorted((j1, j2) -> j2.getPostedDate().compareTo(j1.getPostedDate()))
                .limit(limit)
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Validate job posting request
     *
     * @param request The job posting request to validate
     * @throws IllegalArgumentException if the request is invalid
     */
    private void validateJobRequest(JobPostingRequest request) {
        if (request.getTitle() == null || request.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Job title cannot be empty");
        }

        if (request.getDescription() == null || request.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Job description cannot be empty");
        }

        if (request.getCompany() == null || request.getCompany().trim().isEmpty()) {
            throw new IllegalArgumentException("Company name cannot be empty");
        }

        if (request.getRecruiterId() == null || request.getRecruiterId().trim().isEmpty()) {
            throw new IllegalArgumentException("Recruiter ID cannot be empty");
        }

        if (request.getJobType() == null) {
            throw new IllegalArgumentException("Job type must be specified");
        }
    }

    /**
     * Map a job posting request to a job entity
     *
     * @param request The request to map
     * @return A new job entity with data from the request
     */
    private JobEntity mapToEntity(JobPostingRequest request) {
        return JobEntity.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .company(request.getCompany())
                .location(request.getLocation())
                .recruiterId(request.getRecruiterId())
                .jobType(request.getJobType())
                .workplaceType(request.getWorkplaceType())
                .experienceLevel(request.getExperienceLevel())
                .requirements(request.getRequirements())
                .salaryMin(request.getSalaryMin())
                .salaryMax(request.getSalaryMax())
                .currency(request.getCurrency())
                .expiryDate(request.getExpiryDate())
                .benefits(request.getBenefits().stream()
                        .map(b -> new JobEntity.Benefit(b.getName(), b.getDescription()))
                        .collect(Collectors.toList()))
                .build();
    }

    /**
     * Update a job entity with data from a job posting request
     *
     * @param entity  The entity to update
     * @param request The request with new data
     */
    private void updateEntityFromRequest(JobEntity entity, JobPostingRequest request) {
        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription());
        entity.setCompany(request.getCompany());
        entity.setLocation(request.getLocation());
        entity.setJobType(request.getJobType());
        entity.setWorkplaceType(request.getWorkplaceType());
        entity.setExperienceLevel(request.getExperienceLevel());
        entity.setRequirements(request.getRequirements());
        entity.setSalaryMin(request.getSalaryMin());
        entity.setSalaryMax(request.getSalaryMax());
        entity.setCurrency(request.getCurrency());
        entity.setExpiryDate(request.getExpiryDate());
        entity.setBenefits(request.getBenefits().stream()
                .map(b -> new JobEntity.Benefit(b.getName(), b.getDescription()))
                .collect(Collectors.toList()));
    }

    /**
     * Map a job entity to a job DTO
     *
     * @param entity The entity to map
     * @return A job DTO with data from the entity
     */
    private JobDTO mapToDTO(JobEntity entity) {
        return JobDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .company(entity.getCompany())
                .location(entity.getLocation())
                .recruiterId(entity.getRecruiterId())
                .jobType(entity.getJobType())
                .workplaceType(entity.getWorkplaceType())
                .experienceLevel(entity.getExperienceLevel())
                .requirements(entity.getRequirements())
                .salaryMin(entity.getSalaryMin())
                .salaryMax(entity.getSalaryMax())
                .currency(entity.getCurrency())
                .postedDate(entity.getPostedDate())
                .expiryDate(entity.getExpiryDate())
                .active(entity.isActive())
                .applicationCount(entity.getApplicationCount())
                .benefits(entity.getBenefits().stream()
                        .map(b -> new JobDTO.Benefit(b.getName(), b.getDescription()))
                        .collect(Collectors.toList()))
                .build();
    }
}