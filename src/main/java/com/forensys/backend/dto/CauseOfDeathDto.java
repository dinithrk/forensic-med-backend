package com.forensys.backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CauseOfDeathDto {
    private String causeDescription;
    private String severity;
    private String aproxTFromOnsetToDeath;
}
