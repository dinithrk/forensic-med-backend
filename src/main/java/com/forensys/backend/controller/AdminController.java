package com.forensys.backend.controller;

import com.forensys.backend.dto.StaffRequestDto;
import com.forensys.backend.dto.StaffResponseDto;
import com.forensys.backend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/staff")
@RequiredArgsConstructor
@Tag(name = "Admin / Staff Management", description = "Endpoints for managing staff members and their roles")
public class AdminController {

    private final AdminService adminService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new staff member")
    public ResponseEntity<StaffResponseDto> createStaff(@Valid @RequestBody StaffRequestDto requestDto) {
        return ResponseEntity.ok(adminService.createStaff(requestDto));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all staff members")
    public ResponseEntity<List<StaffResponseDto>> getAllStaff() {
        return ResponseEntity.ok(adminService.getAllStaff());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get staff member by ID")
    public ResponseEntity<StaffResponseDto> getStaffById(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getStaffById(id));
    }

    @PutMapping("/{id}/profile")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update staff profile (roles, department, etc.)")
    public ResponseEntity<StaffResponseDto> updateStaffProfile(@PathVariable Long id, @Valid @RequestBody StaffRequestDto requestDto) {
        return ResponseEntity.ok(adminService.updateStaffRoles(id, requestDto));
    }

    @PutMapping("/{id}/credentials")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update staff credentials (username/password)")
    public ResponseEntity<Void> updateStaffCredentials(@PathVariable Long id, @Valid @RequestBody com.forensys.backend.dto.ProfileUpdateRequestDto requestDto) {
        adminService.updateUserCredentials(id, requestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deactivate/Delete a staff member")
    public ResponseEntity<Void> deleteStaff(@PathVariable Long id) {
        adminService.deactivateUser(id);
        return ResponseEntity.ok().build();
    }
}
