package com.forensys.backend.entity;

import com.forensys.backend.entity.enums.ModeOfPoisoning;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "toxicology_request")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ToxicologyRequest extends LaboratoryRequest {

    @Enumerated(EnumType.STRING)
    private ModeOfPoisoning modeOfPoisoning;

    private String medicalHistory;
    private String analysisRequired;
}
