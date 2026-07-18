package com.forensys.backend.entity;

import com.forensys.backend.entity.enums.HistopathologyRequestType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "histopathology_request")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class HistopathologyRequest extends LaboratoryRequest {

    @Enumerated(EnumType.STRING)
    private HistopathologyRequestType requestType;

    private String macroscopicAppearances;
    private String probableCauseOfDeath;
    private String specialProcedureRequest;
    private String microscopicAppearance;
    private String diagnosis;
}
