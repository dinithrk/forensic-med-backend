package com.forensys.backend.dto;

import com.forensys.backend.entity.enums.SpecimenType;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ForensicSampleDto {
    private Long sampleId;
    private SpecimenType specimenType;
    private String organSource;
    private Integer numberOfTissues;
    private String productionNumber;
    private String referredInstitution;

    // Nested children
    private List<ChainOfCustodyDto> chainOfCustody;
}
