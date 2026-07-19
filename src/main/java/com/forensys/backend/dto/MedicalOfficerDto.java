package com.forensys.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalOfficerDto {
    private Long officerId;
    private String fullName;
    private String qualifications;
    private String slmcRegNo;
    private String designation;
}
