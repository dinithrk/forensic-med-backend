package com.forensys.backend.dto;

import lombok.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AutopsyExamDto {
    private Long autopsyId;
    private String autopsyReportPdf;
    private String health1135aDoc;
    private String maternalDeathCategory;
    private Boolean underInvestigation;
    
    // Nested children
    private Set<CauseOfDeathDto> causesOfDeath;
    private Set<String> comments;
}
