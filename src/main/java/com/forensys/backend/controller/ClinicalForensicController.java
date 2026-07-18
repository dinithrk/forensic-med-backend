package com.forensys.backend.controller;

import com.forensys.backend.dto.MlefRecordDto;
import com.forensys.backend.dto.MlrReportDto;
import com.forensys.backend.service.ClinicalForensicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/clinical")
@RequiredArgsConstructor
public class ClinicalForensicController {

    private final ClinicalForensicService clinicalForensicService;

    @PostMapping("/mlef")
    @PreAuthorize("hasAnyRole('JMO', 'MEDICAL_OFFICER')")
    public ResponseEntity<MlefRecordDto> createMlefRecord(@RequestBody MlefRecordDto dto) {
        return new ResponseEntity<>(clinicalForensicService.createMlefRecord(dto), HttpStatus.CREATED);
    }

    @GetMapping("/mlef/{id}")
    @PreAuthorize("hasAnyRole('JMO', 'MEDICAL_OFFICER', 'ADMIN')")
    public ResponseEntity<MlefRecordDto> getMlefRecordById(@PathVariable Long id) {
        return ResponseEntity.ok(clinicalForensicService.getMlefRecordById(id));
    }

    @PostMapping("/mlef/{id}/mlr-report")
    @PreAuthorize("hasAnyRole('JMO', 'MEDICAL_OFFICER')")
    public ResponseEntity<MlrReportDto> generateMlrReport(@PathVariable Long id, @RequestBody MlrReportDto dto) {
        return new ResponseEntity<>(clinicalForensicService.generateMlrReport(id, dto), HttpStatus.CREATED);
    }
}
