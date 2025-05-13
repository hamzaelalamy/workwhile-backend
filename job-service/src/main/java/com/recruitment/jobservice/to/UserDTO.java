package com.recruitment.jobservice.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
    private boolean active;
    private NotificationPreferencesDTO notificationPreferences;

    public enum Role {
        CANDIDATE,
        RECRUITER,
        ADMIN
    }
}