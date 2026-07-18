package com.forensys.backend.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostMortemDto {
    private Long pmSerialNo;
    private LocalDateTime dateTimeOfPmExam;
    private String placeOfExamination;
    private String district;
    private Boolean underInvestigation;
    private Boolean specimensRetained;
    
    // M:N relationship IDs
    private List<Long> medicalOfficerIds;
    
    // Nested components
    private List<PreAutopsyInformationDto> preAutopsyInformation;
    private AutopsyExamDto autopsyExam;
}
