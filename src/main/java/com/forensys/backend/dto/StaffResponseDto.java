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
public class StaffResponseDto {
    private Long userId;
    private String username;
    private Set<String> roles;
    private Long staffId;
    private String fullName;
    private String slmcRegNo;
    private String designation;
    private String qualifications;
    private Long departmentId;
    private String departmentName;
    private Set<String> contactNumbers;
    private String specialization;
}
