package com.recruitment.userservice.to;

import com.recruitment.userservice.dataaccess.entities.NotificationPreferences;
import com.recruitment.userservice.dataaccess.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserDTO userEntityToUserDTO(UserEntity userEntity);

    @Mapping(target = "password", ignore = true)
    UserEntity userDTOToUserEntity(UserDTO userDTO);

    @Mapping(target = "password", ignore = true)
    void updateUserEntityFromDTO(UserDTO userDTO, @MappingTarget UserEntity userEntity);

    NotificationPreferencesDTO preferencesToDTO(NotificationPreferences preferences);

    NotificationPreferences dtoToPreferences(NotificationPreferencesDTO dto);
}