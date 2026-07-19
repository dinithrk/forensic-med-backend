package com.forensys.backend.controller;

import com.forensys.backend.dto.DeceasedDto;
import com.forensys.backend.service.AutopsyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/deceased")
@RequiredArgsConstructor
public class DeceasedController {

    private final AutopsyService autopsyService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER')")
    public ResponseEntity<DeceasedDto> registerDeceased(@RequestBody DeceasedDto dto) {
        return new ResponseEntity<>(autopsyService.registerDeceased(dto), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER', 'CLERICAL_OFFICER')")
    public ResponseEntity<List<DeceasedDto>> getAllDeceased() {
        return ResponseEntity.ok(autopsyService.getAllDeceased());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER', 'CLERICAL_OFFICER')")
    public ResponseEntity<DeceasedDto> getDeceasedById(@PathVariable Long id) {
        return ResponseEntity.ok(autopsyService.getDeceasedById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER')")
    public ResponseEntity<DeceasedDto> updateDeceased(@PathVariable Long id, @RequestBody DeceasedDto dto) {
        return ResponseEntity.ok(autopsyService.updateDeceased(id, dto));
    }
}
