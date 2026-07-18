package com.forensys.backend.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MlrReportDto {
    private Long mlrId;
    private Long mlefRecordId;
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
