package com.forensys.backend.mapper;

import com.forensys.backend.dto.IndividualInjuryDto;
import com.forensys.backend.dto.MlefRecordDto;
import com.forensys.backend.dto.MlrReportDto;
import com.forensys.backend.dto.ReferralDto;
import com.forensys.backend.entity.IndividualInjury;
import com.forensys.backend.entity.MlefRecord;
import com.forensys.backend.entity.MlrReport;
import com.forensys.backend.entity.Referral;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClinicalForensicMapper {

    @Mapping(source = "patient.patientId", target = "patientId")
    @Mapping(source = "broughtByOfficer.officerId", target = "broughtByOfficerId")
    @Mapping(source = "assignedMedicalOfficer.officerId", target = "assignedMedicalOfficerId")
    MlefRecordDto toDto(MlefRecord mlefRecord);

    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "broughtByOfficer", ignore = true)
    @Mapping(target = "assignedMedicalOfficer", ignore = true)
    MlefRecord toEntity(MlefRecordDto dto);
    
    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "broughtByOfficer", ignore = true)
    @Mapping(target = "assignedMedicalOfficer", ignore = true)
    @Mapping(target = "mlefId", ignore = true)
    void updateEntityFromDto(MlefRecordDto dto, @org.mapstruct.MappingTarget MlefRecord entity);

    @Mapping(target = "mlefRecord", ignore = true)
    IndividualInjury toEntity(IndividualInjuryDto dto);
    IndividualInjuryDto toDto(IndividualInjury entity);

    @Mapping(target = "mlefRecord", ignore = true)
    Referral toEntity(ReferralDto dto);
    ReferralDto toDto(Referral entity);

    @Mapping(source = "mlefRecord.mlefId", target = "mlefRecordId")
    MlrReportDto toDto(MlrReport entity);

    @Mapping(target = "mlefRecord", ignore = true)
    MlrReport toEntity(MlrReportDto dto);
}
