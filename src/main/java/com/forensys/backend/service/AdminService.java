package com.forensys.backend.service;

import com.forensys.backend.dto.StaffRequestDto;
import com.forensys.backend.dto.StaffResponseDto;

import java.util.List;

public interface AdminService {
    StaffResponseDto createStaff(StaffRequestDto requestDto);
    List<StaffResponseDto> getAllStaff();
    StaffResponseDto getStaffById(Long userId);
    StaffResponseDto updateStaffRoles(Long userId, StaffRequestDto requestDto);
    void updateUserCredentials(Long userId, com.forensys.backend.dto.ProfileUpdateRequestDto requestDto);
    void deactivateUser(Long userId);
}
