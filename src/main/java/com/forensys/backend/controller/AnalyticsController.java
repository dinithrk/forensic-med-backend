package com.forensys.backend.controller;

import com.forensys.backend.dto.DashboardAnalyticsDto;
import com.forensys.backend.service.AnalyticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/analytics")
@RequiredArgsConstructor
@Tag(name = "Analytics & Statistics", description = "Endpoints for aggregated forensic department statistics, KPI metrics, and caseload breakdowns")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/dashboard")
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER', 'CLERICAL_OFFICER')")
    @Operation(summary = "Get aggregated dashboard analytics and statistics")
    public ResponseEntity<DashboardAnalyticsDto> getDashboardAnalytics(
            @RequestParam(required = false, defaultValue = "this_year") String preset,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Authentication authentication) {

        boolean isAdminOrJmo = false;
        if (authentication != null) {
            isAdminOrJmo = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(auth -> auth.equalsIgnoreCase("ROLE_ADMIN") || auth.equalsIgnoreCase("ROLE_JMO") ||
                            auth.equalsIgnoreCase("ADMIN") || auth.equalsIgnoreCase("JMO"));
        }

        DashboardAnalyticsDto analytics = analyticsService.getDashboardAnalytics(preset, startDate, endDate, isAdminOrJmo);
        return ResponseEntity.ok(analytics);
    }
}
