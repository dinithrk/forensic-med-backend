package com.forensys.backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreAutopsyInformationDto {
    private Long recordId;
    private String recordDetails;
    private String informationCategory;
}
