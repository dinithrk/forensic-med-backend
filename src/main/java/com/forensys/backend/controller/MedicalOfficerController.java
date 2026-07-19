package com.forensys.backend.controller;

import com.forensys.backend.dto.MedicalOfficerDto;
import com.forensys.backend.repository.MedicalOfficerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/staff/medical-officers")
@RequiredArgsConstructor
public class MedicalOfficerController {

    private final MedicalOfficerRepository medicalOfficerRepository;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER', 'CLERICAL_OFFICER')")
    public ResponseEntity<List<MedicalOfficerDto>> getAllMedicalOfficers() {
        List<MedicalOfficerDto> officers = medicalOfficerRepository.findAll().stream().map(mo -> 
            MedicalOfficerDto.builder()
                .officerId(mo.getOfficerId())
                .fullName(mo.getFullName())
                .qualifications(mo.getQualifications())
                .slmcRegNo(mo.getSlmcRegNo())
                .designation(mo.getDesignation())
                .build()
        ).toList();
        return ResponseEntity.ok(officers);
    }
}
