package com.forensys.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchResultDto {
    private Long id;
    private String label;
    private String type; // e.g. "CASE" or "POSTMORTEM"
}
