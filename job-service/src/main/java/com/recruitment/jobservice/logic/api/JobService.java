package com.recruitment.jobservice.logic.api;

import com.recruitment.jobservice.to.JobDTO;
import com.recruitment.jobservice.to.JobPostingRequest;
import com.recruitment.jobservice.to.JobSearchCriteria;

import java.util.List;

public interface JobService {

    // Create a new job posting
    JobDTO createJob(JobPostingRequest request);

    // Get a job by its ID
    JobDTO getJobById(String id);

    // Get all jobs (with optional pagination)
    List<JobDTO> getAllJobs(int page, int size);

    // Get jobs posted by a specific recruiter
    List<JobDTO> getJobsByRecruiterId(String recruiterId);

    // Update an existing job
    JobDTO updateJob(String id, JobPostingRequest request);

    // Delete a job
    void deleteJob(String id);

    // Activate a job
    void activateJob(String id);

    // Deactivate a job
    void deactivateJob(String id);

    // Search for jobs based on various criteria
    List<JobDTO> searchJobs(JobSearchCriteria criteria);

    // Increase the application count for a job
    void incrementApplicationCount(String id);

    // Get featured or recommended jobs
    List<JobDTO> getFeaturedJobs(int limit);

    // Get recent jobs
    List<JobDTO> getRecentJobs(int limit);
}