package com.forensys.backend.controller;

import com.forensys.backend.dto.ChainOfCustodyDto;
import com.forensys.backend.dto.ForensicSampleDto;
import com.forensys.backend.service.EvidenceService;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/evidence")
@RequiredArgsConstructor
@Tag(name = "Forensic Evidence", description = "Endpoints for managing forensic samples and chain of custody")
public class EvidenceController {

    private final EvidenceService evidenceService;

    @PostMapping("/samples")
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER', 'LABORATORY_STAFF')")
    @Operation(summary = "Register a new forensic sample")
    public ResponseEntity<ForensicSampleDto> registerSample(@Valid @RequestBody ForensicSampleDto dto) {
        return new ResponseEntity<>(evidenceService.registerSample(dto), HttpStatus.CREATED);
    }

    @GetMapping("/samples")
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER', 'LABORATORY_STAFF', 'POLICE_OFFICER')")
    @Operation(summary = "Get all forensic samples")
    public ResponseEntity<java.util.List<ForensicSampleDto>> getAllSamples() {
        return ResponseEntity.ok(evidenceService.getAllSamples());
    }

    @GetMapping("/samples/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER', 'LABORATORY_STAFF', 'POLICE_OFFICER')")
    @Operation(summary = "Get forensic sample by ID")
    public ResponseEntity<ForensicSampleDto> getSampleById(@PathVariable Long id) {
        return ResponseEntity.ok(evidenceService.getSampleById(id));
    }

    @PutMapping("/samples/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER', 'LABORATORY_STAFF')")
    @Operation(summary = "Update forensic sample details")
    public ResponseEntity<ForensicSampleDto> updateSample(@PathVariable Long id, @Valid @RequestBody ForensicSampleDto dto) {
        return ResponseEntity.ok(evidenceService.updateSample(id, dto));
    }

    @PostMapping("/samples/{id}/chain-of-custody")
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER', 'LABORATORY_STAFF', 'POLICE_OFFICER')")
    @Operation(summary = "Log chain of custody transfer for a sample")
    public ResponseEntity<ChainOfCustodyDto> logCustodyTransfer(@PathVariable Long id, @Valid @RequestBody ChainOfCustodyDto dto) {
        return new ResponseEntity<>(evidenceService.logCustodyTransfer(id, dto), HttpStatus.CREATED);
    }
}
