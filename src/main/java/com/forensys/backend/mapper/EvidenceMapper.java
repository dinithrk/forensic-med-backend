package com.forensys.backend.mapper;

import com.forensys.backend.dto.CaseRegisterDto;
import com.forensys.backend.dto.ChainOfCustodyDto;
import com.forensys.backend.dto.ForensicSampleDto;
import com.forensys.backend.entity.CaseRegister;
import com.forensys.backend.entity.ChainOfCustody;
import com.forensys.backend.entity.ForensicSample;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EvidenceMapper {

    CaseRegisterDto toDto(CaseRegister caseRegister);
    CaseRegister toEntity(CaseRegisterDto dto);

    @Mapping(target = "caseRegister", ignore = true)
    @Mapping(target = "autopsyExam", ignore = true)
    ForensicSample toEntity(ForensicSampleDto dto);
    ForensicSampleDto toDto(ForensicSample entity);

    @Mapping(target = "forensicSample", ignore = true)
    ChainOfCustody toEntity(ChainOfCustodyDto dto);
    ChainOfCustodyDto toDto(ChainOfCustody entity);
}
