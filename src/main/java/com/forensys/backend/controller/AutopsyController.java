package com.forensys.backend.controller;

import com.forensys.backend.dto.AutopsyExamDto;
import com.forensys.backend.dto.PostMortemDto;
import com.forensys.backend.service.AutopsyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/deceased/{deceasedId}/post-mortems")
@RequiredArgsConstructor
public class AutopsyController {

    private final AutopsyService autopsyService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER')")
    public ResponseEntity<PostMortemDto> createPostMortem(@PathVariable Long deceasedId, @RequestBody PostMortemDto dto) {
        dto.setDeceasedId(deceasedId);
        return new ResponseEntity<>(autopsyService.createPostMortem(dto), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER', 'CLERICAL_OFFICER')")
    public ResponseEntity<java.util.List<PostMortemDto>> getAllPostMortemsForDeceased(@PathVariable Long deceasedId) {
        return ResponseEntity.ok(autopsyService.getAllPostMortemsForDeceased(deceasedId));
    }

    @GetMapping("/{pmSerialNo}")
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER', 'CLERICAL_OFFICER')")
    public ResponseEntity<PostMortemDto> getPostMortemById(@PathVariable Long deceasedId, @PathVariable Long pmSerialNo) {
        return ResponseEntity.ok(autopsyService.getPostMortemById(pmSerialNo));
    }

    @PutMapping("/{pmSerialNo}")
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER')")
    public ResponseEntity<PostMortemDto> updatePostMortem(
            @PathVariable Long deceasedId, 
            @PathVariable Long pmSerialNo, 
            @RequestBody PostMortemDto dto) {
        return ResponseEntity.ok(autopsyService.updatePostMortem(deceasedId, pmSerialNo, dto));
    }

    @PutMapping("/{pmSerialNo}/finalize")
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER')")
    public ResponseEntity<PostMortemDto> finalizeAutopsyExam(@PathVariable Long deceasedId, @PathVariable Long pmSerialNo, @RequestBody AutopsyExamDto dto) {
        return ResponseEntity.ok(autopsyService.finalizeAutopsyExam(pmSerialNo, dto));
    }
}
