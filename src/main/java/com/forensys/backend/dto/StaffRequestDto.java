package com.forensys.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StaffRequestDto {
    private String username;
    private String password;
    private Set<String> roles;
    private String fullName;
    private String slmcRegNo;
    private String designation;
    private String qualifications;
    private Long departmentId;
    private Set<String> contactNumbers;
    private String specialization;
}
