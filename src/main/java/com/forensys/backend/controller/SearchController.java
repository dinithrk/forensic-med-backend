package com.forensys.backend.controller;

import com.forensys.backend.dto.SearchResultDto;
import com.forensys.backend.repository.PostMortemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
public class SearchController {

    private final com.forensys.backend.repository.MlefRecordRepository mlefRecordRepository;
    private final PostMortemRepository postMortemRepository;

    @GetMapping("/cases")
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER', 'LABORATORY_STAFF', 'POLICE_OFFICER')")
    public ResponseEntity<List<SearchResultDto>> searchCases(@RequestParam String query) {
        List<SearchResultDto> results = mlefRecordRepository.findTop10ByPatient_FullNameContainingIgnoreCase(query)
                .stream()
                .map(m -> SearchResultDto.builder()
                        .id(m.getMlefId())
                        .label("MLEF #" + m.getMlefId() + " - " + m.getPatient().getFullName())
                        .type("CASE")
                        .build())
                .toList();
        return ResponseEntity.ok(results);
    }

    @GetMapping("/postmortems")
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER', 'LABORATORY_STAFF', 'POLICE_OFFICER')")
    public ResponseEntity<List<SearchResultDto>> searchPostmortems(@RequestParam String query) {
        List<SearchResultDto> results = postMortemRepository.findTop10ByDeceased_FullNameContainingIgnoreCase(query)
                .stream()
                .map(p -> SearchResultDto.builder()
                        .id(p.getPmSerialNo())
                        .label("PM #" + p.getPmSerialNo() + " - " + p.getDeceased().getFullName())
                        .type("POSTMORTEM")
                        .build())
                .toList();
        return ResponseEntity.ok(results);
    }
}
