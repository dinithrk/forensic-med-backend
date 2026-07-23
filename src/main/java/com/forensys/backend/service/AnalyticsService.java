package com.forensys.backend.service;

import com.forensys.backend.dto.DashboardAnalyticsDto;

import java.time.LocalDate;

public interface AnalyticsService {
    DashboardAnalyticsDto getDashboardAnalytics(String preset, LocalDate startDate, LocalDate endDate, boolean isAdminOrJmo);
}
