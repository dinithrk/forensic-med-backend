package com.forensys.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

public class AnalyticsDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DashboardSummary {
        private long totalCases;
        private long totalClinicalCases;
        private long totalPostmortemCases;
        private long pendingReports;
        private long completedReports;
        private double averageReportTurnaroundTime;
        private double averageCasesPerDay;
        private long activeMedicalOfficers;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MonthlyVolumeItem {
        private String month; // YYYY-MM
        private long clinical;
        private long postmortem;
        private long total;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CategoryBreakdownItem {
        private String category;
        private long count;
        private double percentage;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class StatusBreakdownItem {
        private String status;
        private long count;
        private double percentage;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TurnaroundMonthlyItem {
        private String month;
        private double averageDays;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TurnaroundMetrics {
        private double averageDays;
        private double medianDays;
        private double minimum;
        private double maximum;
        private List<TurnaroundMonthlyItem> monthlyAverages;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OfficerWorkload {
        private Long officerId;
        private String officerName;
        private long totalCases;
        private long pendingReports;
        private long completedReports;
        private double averageTurnaroundDays;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ExaminationTrends {
        private String month;
        private long examinationsPerformed;
        private long reportsIssued;
        private long caseCompletions;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TimeDistribution {
        private Map<String, Long> weekdayDistribution; // e.g. "Monday" -> 15
        private Map<String, Long> monthDistribution;   // e.g. "January" -> 120
        private Map<String, Long> yearDistribution;    // e.g. "2026" -> 800
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AllAnalyticsData {
        private DashboardSummary summary;
        private List<MonthlyVolumeItem> monthlyVolume;
        private List<CategoryBreakdownItem> categoryBreakdown;
        private List<StatusBreakdownItem> statusBreakdown;
        private TurnaroundMetrics turnaroundMetrics;
        private List<OfficerWorkload> officerWorkload;
        private List<ExaminationTrends> examinationTrends;
        private TimeDistribution timeDistribution;
    }
}
