package com.forensys.backend.mapper;

import com.forensys.backend.dto.PatientDto;
import com.forensys.backend.entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    PatientDto toDto(Patient patient);
    
    @Mapping(target = "identification", ignore = true)
    Patient toEntity(PatientDto dto);
}
