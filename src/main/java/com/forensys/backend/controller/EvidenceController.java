package com.forensys.backend.controller;

import com.forensys.backend.dto.ChainOfCustodyDto;
import com.forensys.backend.dto.ForensicSampleDto;
import com.forensys.backend.service.EvidenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/evidence")
@RequiredArgsConstructor
public class EvidenceController {

    private final EvidenceService evidenceService;

    @PostMapping("/samples")
    @PreAuthorize("hasAnyRole('JMO', 'MEDICAL_OFFICER', 'LABORATORY_STAFF')")
    public ResponseEntity<ForensicSampleDto> registerSample(@RequestBody ForensicSampleDto dto) {
        return new ResponseEntity<>(evidenceService.registerSample(dto), HttpStatus.CREATED);
    }

    @PostMapping("/samples/{id}/chain-of-custody")
    @PreAuthorize("hasAnyRole('JMO', 'MEDICAL_OFFICER', 'LABORATORY_STAFF', 'POLICE_OFFICER')")
    public ResponseEntity<ChainOfCustodyDto> logCustodyTransfer(@PathVariable Long id, @RequestBody ChainOfCustodyDto dto) {
        return new ResponseEntity<>(evidenceService.logCustodyTransfer(id, dto), HttpStatus.CREATED);
    }
}
