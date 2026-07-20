package com.forensys.backend.entity;

import com.forensys.backend.entity.enums.ReportStatus;
import com.forensys.backend.entity.enums.ReportType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "forensic_report")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ForensicReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportType reportType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportStatus status;

    @Builder.Default
    @Column(nullable = false)
    private Integer versionNumber = 1;

    private String caseType; // "CLINICAL" or "POSTMORTEM"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mlef_id")
    private MlefRecord mlefRecord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pm_serial_no")
    private PostMortem postMortem;

    private String serialNo;
    private String courtName;
    private String courtCaseNo;
    private String policeStation;
    private String policeRefNo;
    private LocalDate dateOfTrial;
    private LocalDateTime examinationDate;

    @Column(columnDefinition = "TEXT")
    private String opinion;

    @Column(columnDefinition = "TEXT")
    private String detailsJson;

    private String doctorName;
    private String doctorDesignation;
    private String doctorSlmcNo;

    // Date stamps for lifecycle tracking
    private LocalDateTime draftDate;
    private LocalDateTime finalizedDate;
    private LocalDateTime dispatchedDate;
    private LocalDateTime receiptConfirmedDate;

    // Versioning
    private Long parentReportId;
    
    @Column(columnDefinition = "TEXT")
    private String amendmentReason;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (draftDate == null && status == ReportStatus.DRAFT) {
            draftDate = LocalDateTime.now();
        }
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }
        if (versionNumber == null) {
            versionNumber = 1;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
