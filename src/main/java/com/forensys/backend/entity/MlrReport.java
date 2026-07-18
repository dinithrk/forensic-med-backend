package com.forensys.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "mlr_report")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MlrReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mlrId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mlef_id")
    private MlefRecord mlefRecord;

    private String serialNo;
    private String courtName;
    private String courtCaseNo;
    private LocalDate dateOfIssue;
    private String policeStation;
    private LocalDate dateOfTrial;
    private String compatibilityVerdict;
    private Boolean considerSelfInfliction;
    private LocalDate dateReportDispatched;
    private LocalDate receivedByCourtDate;
}
