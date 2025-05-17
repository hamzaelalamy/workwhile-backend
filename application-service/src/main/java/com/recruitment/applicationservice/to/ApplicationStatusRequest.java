package com.recruitment.applicationservice.to;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ApplicationStatusRequest {
    @NotBlank(message = "Status is required")
    private String status;
}