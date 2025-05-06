package com.recruitment.candidateservice.to;

import com.recruitment.candidateservice.dataaccess.entities.CandidateEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CandidateMapper {

    CandidateDTO candidateEntityToCandidateDTO(CandidateEntity candidateEntity);

    CandidateEntity candidateDTOToCandidateEntity(CandidateDTO candidateDTO);

    void updateCandidateEntityFromDTO(CandidateDTO candidateDTO, @MappingTarget CandidateEntity candidateEntity);

    CandidateDTO.SkillDTO skillToSkillDTO(CandidateEntity.Skill skill);

    CandidateEntity.Skill skillDTOToSkill(CandidateDTO.SkillDTO skillDTO);

    CandidateDTO.ExperienceDTO experienceToExperienceDTO(CandidateEntity.Experience experience);

    CandidateEntity.Experience experienceDTOToExperience(CandidateDTO.ExperienceDTO experienceDTO);

    CandidateDTO.EducationDTO educationToEducationDTO(CandidateEntity.Education education);

    CandidateEntity.Education educationDTOToEducation(CandidateDTO.EducationDTO educationDTO);

    CandidateDTO.AvailabilityDTO availabilityToAvailabilityDTO(CandidateEntity.Availability availability);

    CandidateEntity.Availability availabilityDTOToAvailability(CandidateDTO.AvailabilityDTO availabilityDTO);
}