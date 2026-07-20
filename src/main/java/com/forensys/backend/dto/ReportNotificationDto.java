package com.forensys.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportNotificationDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OverdueReportItem {
        private String caseType;
        private Long caseId;
        private String referenceNo;
        private String subjectName;
        private LocalDateTime examinationDate;
        private long daysOverdue;
        private String assignedDoctor;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpcomingCourtCaseItem {
        private Long reportId;
        private String caseType;
        private Long caseId;
        private String courtName;
        private String courtCaseNo;
        private LocalDate dateOfTrial;
        private long daysUntilTrial;
        private String subjectName;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PendingDispatchItem {
        private Long reportId;
        private String reportType;
        private String serialNo;
        private String caseType;
        private Long caseId;
        private String courtName;
        private String courtCaseNo;
        private LocalDateTime finalizedDate;
        private String doctorName;
    }

    private List<OverdueReportItem> overdueReports;
    private List<UpcomingCourtCaseItem> upcomingCourtDates;
    private List<PendingDispatchItem> pendingDispatches;
}
