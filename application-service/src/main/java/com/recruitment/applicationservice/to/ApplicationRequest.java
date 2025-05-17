package com.recruitment.applicationservice.to;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationRequest {

    @NotBlank(message = "Job ID is required")
    private String jobId;

    private String coverLetter;

    private String resumeUrl;
}