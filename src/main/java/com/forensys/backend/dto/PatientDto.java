package com.forensys.backend.dto;

import com.forensys.backend.entity.enums.Gender;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientDto {
    private Long patientId;
    private String fullName;
    private String address;
    private LocalDate dateOfBirth;
    private Integer age;
    private Gender sex;
    private String nicNo;
    private String passportNo;
    private Boolean consentGiven;
}
