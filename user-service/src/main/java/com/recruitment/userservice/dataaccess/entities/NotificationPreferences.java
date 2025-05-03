package com.recruitment.userservice.dataaccess.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationPreferences {

    @Builder.Default
    private boolean emailNotifications = true;

    @Builder.Default
    private boolean applicationUpdates = true;

    @Builder.Default
    private boolean jobRecommendations = true;

    @Builder.Default
    private boolean marketingEmails = false;
}