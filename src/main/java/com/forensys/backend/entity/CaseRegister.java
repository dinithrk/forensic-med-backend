package com.forensys.backend.entity;

import com.forensys.backend.entity.enums.SubjectType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "case_register")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CaseRegister {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long registerId;

    private String autopsyRefNo;

    @Enumerated(EnumType.STRING)
    private SubjectType subjectType;

    private LocalDate dateOfIncident;
    private LocalDate dateOfDeath;
    private LocalDate dateOfAutopsy;
}
