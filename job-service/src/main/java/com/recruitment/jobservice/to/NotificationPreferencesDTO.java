package com.recruitment.jobservice.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationPreferencesDTO {
    private boolean emailNotifications;
    private boolean applicationUpdates;
    private boolean jobRecommendations;
    private boolean marketingEmails;
}