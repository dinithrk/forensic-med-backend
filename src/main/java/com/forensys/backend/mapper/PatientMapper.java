package com.forensys.backend.mapper;

import com.forensys.backend.dto.PatientDto;
import com.forensys.backend.entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    @Mapping(target = "nicNo", source = "identification.nicNo")
    @Mapping(target = "passportNo", source = "identification.passportNo")
    PatientDto toDto(Patient patient);
    
    @Mapping(target = "identification.nicNo", source = "nicNo")
    @Mapping(target = "identification.passportNo", source = "passportNo")
    Patient toEntity(PatientDto dto);
}
