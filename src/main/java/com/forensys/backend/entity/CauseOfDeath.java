package com.forensys.backend.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CauseOfDeath {
    private String causeDescription;
    private String severity;
    private String aproxTFromOnsetToDeath;
}
