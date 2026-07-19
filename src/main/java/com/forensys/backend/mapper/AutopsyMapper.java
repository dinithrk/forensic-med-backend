package com.forensys.backend.mapper;

import com.forensys.backend.dto.AutopsyExamDto;
import com.forensys.backend.dto.DeceasedDto;
import com.forensys.backend.dto.PostMortemDto;
import com.forensys.backend.entity.AutopsyExam;
import com.forensys.backend.entity.Deceased;
import com.forensys.backend.entity.PostMortem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AutopsyMapper {

    DeceasedDto toDto(Deceased deceased);
    Deceased toEntity(DeceasedDto dto);

    @Mapping(source = "deceased.deceasedId", target = "deceasedId")
    @Mapping(target = "medicalOfficerIds", expression = "java(pm.getMedicalOfficers() != null ? pm.getMedicalOfficers().stream().map(com.forensys.backend.entity.MedicalOfficer::getOfficerId).toList() : null)")
    PostMortemDto toDto(PostMortem pm);
    
    @Mapping(target = "medicalOfficers", ignore = true)
    @Mapping(target = "deceased", ignore = true)
    PostMortem toEntity(PostMortemDto dto);

    @Mapping(target = "postMortem", ignore = true)
    AutopsyExam toEntity(AutopsyExamDto dto);
    AutopsyExamDto toDto(AutopsyExam entity);
}
