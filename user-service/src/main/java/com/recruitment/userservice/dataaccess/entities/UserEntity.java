package com.recruitment.userservice.dataaccess.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class UserEntity {

    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private Role role;

    @Builder.Default
    private boolean active = true;

    @Builder.Default
    private NotificationPreferences notificationPreferences = new NotificationPreferences();

    public enum Role {
        CANDIDATE,
        RECRUITER,
        ADMIN
    }
}