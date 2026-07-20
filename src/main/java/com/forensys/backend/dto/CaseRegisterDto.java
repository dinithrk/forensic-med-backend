package com.forensys.backend.dto;

import com.forensys.backend.entity.enums.SubjectType;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CaseRegisterDto {
    private Long registerId;
    private String autopsyRefNo;
    private SubjectType subjectType;
    private LocalDate dateOfIncident;
    private LocalDate dateOfDeath;
    private LocalDate dateOfAutopsy;

}
