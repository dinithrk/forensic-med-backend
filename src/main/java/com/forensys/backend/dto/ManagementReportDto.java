package com.forensys.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

public class ManagementReportDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DailyReport {
        private String date;
        private int totalClinicalCases;
        private int totalPostmortemCases;
        private int totalCases;
        private List<DailyCaseItem> items;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DailyCaseItem {
        private String caseType; // "CLINICAL" or "POSTMORTEM"
        private Long caseId;
        private String referenceNo;
        private String subjectName;
        private String policeStation;
        private String dateTimeExamined;
        private String doctorName;
        private String status;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MonthlyReport {
        private int year;
        private int month;
        private String monthName;
        private int totalClinical;
        private int totalPostmortem;
        private int totalFinalized;
        private int totalDispatched;
        private List<DoctorWorkloadItem> doctorWorkload;
        private List<StationDistributionItem> stationDistribution;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DoctorWorkloadItem {
        private String doctorName;
        private int clinicalCount;
        private int postmortemCount;
        private int totalCount;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class StationDistributionItem {
        private String policeStation;
        private int count;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PendingReport {
        private int overdueDraftsCount;
        private int pendingDispatchesCount;
        private List<ReportNotificationDto.OverdueReportItem> overdueDrafts;
        private List<ReportNotificationDto.PendingDispatchItem> pendingDispatches;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CourtReport {
        private int upcomingTrialsCount;
        private int pendingDispatchesCount;
        private int receiptConfirmedCount;
        private List<ReportNotificationDto.UpcomingCourtCaseItem> upcomingTrials;
        private List<ReportNotificationDto.PendingDispatchItem> pendingDispatches;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class StatisticalReport {
        private int totalCases;
        private Map<String, Integer> genderDistribution;
        private Map<String, Integer> ageDistribution;
        private Map<String, Integer> bodilyHarmFrequencies;
        private Map<String, Integer> substanceStats;
        private Map<String, Integer> caseTypeBreakdown;
    }
}
