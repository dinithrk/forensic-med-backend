package com.forensys.backend.dto;

import com.forensys.backend.entity.enums.SpecimenType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ForensicSampleDto {
    private Long sampleId;
    
    @NotNull(message = "Specimen type is required")
    private SpecimenType specimenType;
    
    @NotBlank(message = "Organ source is required")
    private String organSource;
    private Integer numberOfTissues;
    private String productionNumber;
    private String referredInstitution;
    private LocalDate collectionDate;
    private String collectedBy;

    private Long caseId;
    private Long pmSerialNo;

    // Nested children
    private List<ChainOfCustodyDto> chainOfCustody;
}
