package com.forensys.backend.controller;

import com.forensys.backend.dto.AutopsyExamDto;
import com.forensys.backend.dto.DeceasedDto;
import com.forensys.backend.dto.PostMortemDto;
import com.forensys.backend.service.AutopsyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/autopsies")
@RequiredArgsConstructor
public class AutopsyController {

    private final AutopsyService autopsyService;

    @PostMapping("/deceased")
    @PreAuthorize("hasAnyRole('JMO', 'MEDICAL_OFFICER')")
    public ResponseEntity<DeceasedDto> registerDeceased(@RequestBody DeceasedDto dto) {
        return new ResponseEntity<>(autopsyService.registerDeceased(dto), HttpStatus.CREATED);
    }

    @PostMapping("/post-mortem")
    @PreAuthorize("hasAnyRole('JMO', 'MEDICAL_OFFICER')")
    public ResponseEntity<PostMortemDto> createPostMortem(@RequestBody PostMortemDto dto) {
        return new ResponseEntity<>(autopsyService.createPostMortem(dto), HttpStatus.CREATED);
    }

    @PutMapping("/post-mortem/{pmSerialNo}/finalize")
    @PreAuthorize("hasAnyRole('JMO', 'MEDICAL_OFFICER')")
    public ResponseEntity<PostMortemDto> finalizeAutopsyExam(@PathVariable Long pmSerialNo, @RequestBody AutopsyExamDto dto) {
        return ResponseEntity.ok(autopsyService.finalizeAutopsyExam(pmSerialNo, dto));
    }
}
