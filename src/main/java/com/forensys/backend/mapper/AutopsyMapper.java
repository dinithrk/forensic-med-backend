package com.forensys.backend.mapper;

import com.forensys.backend.dto.AutopsyExamDto;
import com.forensys.backend.dto.DeceasedDto;
import com.forensys.backend.dto.PostMortemDto;
import com.forensys.backend.entity.AutopsyExam;
import com.forensys.backend.entity.Deceased;
import com.forensys.backend.entity.PostMortem;
import com.forensys.backend.entity.PreAutopsyInformation;
import com.forensys.backend.dto.PreAutopsyInformationDto;
import com.forensys.backend.entity.InquestOrder;
import com.forensys.backend.entity.Identifier;
import com.forensys.backend.dto.InquestOrderDto;
import com.forensys.backend.dto.IdentifierDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AutopsyMapper {

    DeceasedDto toDto(Deceased deceased);
    Deceased toEntity(DeceasedDto dto);
    
    @Mapping(target = "deceasedId", ignore = true)
    @Mapping(target = "inquestOrder", ignore = true)
    @Mapping(target = "identifiers", ignore = true)
    void updateDeceasedFromDto(DeceasedDto dto, @MappingTarget Deceased entity);

    @Mapping(target = "deceased", ignore = true)
    InquestOrder toEntity(InquestOrderDto dto);

    @Mapping(target = "deceased", ignore = true)
    Identifier toEntity(IdentifierDto dto);

    @Mapping(source = "deceased.deceasedId", target = "deceasedId")
    @Mapping(target = "medicalOfficerIds", expression = "java(pm.getMedicalOfficers() != null ? pm.getMedicalOfficers().stream().map(com.forensys.backend.entity.MedicalOfficer::getOfficerId).toList() : null)")
    PostMortemDto toDto(PostMortem pm);
    
    @Mapping(target = "medicalOfficers", ignore = true)
    @Mapping(target = "deceased", ignore = true)
    PostMortem toEntity(PostMortemDto dto);
    
    @Mapping(target = "pmSerialNo", ignore = true)
    @Mapping(target = "medicalOfficers", ignore = true)
    @Mapping(target = "deceased", ignore = true)
    void updatePostMortemFromDto(PostMortemDto dto, @MappingTarget PostMortem entity);

    @Mapping(target = "postMortem", ignore = true)
    PreAutopsyInformation toEntity(PreAutopsyInformationDto dto);

    @Mapping(target = "postMortem", ignore = true)
    AutopsyExam toEntity(AutopsyExamDto dto);
    AutopsyExamDto toDto(AutopsyExam entity);
}
