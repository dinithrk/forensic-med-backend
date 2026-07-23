package com.forensys.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardAnalyticsDto {

    private KpiSummary kpis;
    private List<MonthlyVolumeItem> monthlyVolume;
    private List<CategoryCountItem> categoryBreakdown;
    private List<CategoryCountItem> mannerOfDeathBreakdown;
    private List<OfficerCaseloadItem> officerCaseload;
    private List<CategoryCountItem> reportStatusDistribution;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class KpiSummary {
        private int totalCases;
        private int clinicalCasesCount;
        private int postmortemCasesCount;
        private int pendingReportsCount;
        private double avgTurnaroundDays;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MonthlyVolumeItem {
        private String month;      // e.g. "Jan 2026"
        private String yearMonth;  // e.g. "2026-01"
        private int clinical;
        private int postmortem;
        private int total;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CategoryCountItem {
        private String category;
        private int count;
        private double percentage;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OfficerCaseloadItem {
        private String doctorName;
        private int clinicalCount;
        private int postmortemCount;
        private int totalCount;
    }
}
