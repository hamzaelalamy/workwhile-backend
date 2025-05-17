package com.recruitment.applicationservice.security;

import com.recruitment.applicationservice.dataaccess.dao.ApplicationRepository;
import com.recruitment.applicationservice.dataaccess.entities.ApplicationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationAuthorizationService {

    private final ApplicationRepository applicationRepository;

    /**
     * Checks if the provided user ID is the owner of the application
     *
     * @param applicationId The application ID to check
     * @param userId The user ID to verify ownership
     * @return true if the user is the owner, false otherwise
     */
    public boolean isApplicationOwner(String applicationId, String userId) {
        if (applicationId == null || userId == null) {
            return false;
        }

        Optional<ApplicationEntity> application = applicationRepository.findById(applicationId);

        return application.map(app -> userId.equals(app.getUserId())).orElse(false);
    }
}