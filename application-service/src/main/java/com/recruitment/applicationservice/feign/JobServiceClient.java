package com.recruitment.applicationservice.feign;

import com.recruitment.applicationservice.to.JobDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "job-service")
public interface JobServiceClient {

    @GetMapping("/api/jobs/{id}")
    JobDTO getJobById(@PathVariable String id);

    @PostMapping("/api/jobs/{id}/increment-applications")
    void incrementApplicationCount(@PathVariable String id);
}