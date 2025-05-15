package com.recruitment.applicationservice.to;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationStatusRequest {
    
    @NotBlank(message = "Status is required")
    private String status;
    
    private String recruiterNotes;
}