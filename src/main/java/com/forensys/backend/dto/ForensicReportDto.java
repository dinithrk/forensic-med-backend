package com.forensys.backend.dto;

import com.forensys.backend.entity.enums.ReportStatus;
import com.forensys.backend.entity.enums.ReportType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ForensicReportDto {
    private Long id;
    private ReportType reportType;
    private ReportStatus status;
    private Integer versionNumber;
    private String caseType;
    private Long mlefRecordId;
    private Long pmSerialNo;
    private String serialNo;
    private String courtName;
    private String courtCaseNo;
    private String policeStation;
    private String policeRefNo;
    private LocalDate dateOfTrial;
    private LocalDateTime examinationDate;
    private String opinion;
    private String detailsJson;
    private String doctorName;
    private String doctorDesignation;
    private String doctorSlmcNo;
    private LocalDateTime draftDate;
    private LocalDateTime finalizedDate;
    private LocalDateTime dispatchedDate;
    private LocalDateTime receiptConfirmedDate;
    private Long parentReportId;
    private String amendmentReason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Display fields for UI ease
    private String subjectName;
    private String subjectIdentifier; // NIC or hospital BHT or Reg No
}
