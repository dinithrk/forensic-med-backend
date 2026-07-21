package com.forensys.backend.controller;

import com.forensys.backend.dto.ProfileUpdateRequestDto;
import com.forensys.backend.dto.StaffResponseDto;
import com.forensys.backend.entity.User;
import com.forensys.backend.repository.UserRepository;
import com.forensys.backend.service.AdminService;
import lombok.RequiredArgsConstructor;
import com.forensys.backend.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final AdminService adminService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<StaffResponseDto> getMyProfile() {
        User user = getAuthenticatedUser();
        return ResponseEntity.ok(adminService.getStaffById(user.getUserId()));
    }

    @PutMapping("/credentials")
    public ResponseEntity<String> updateMyCredentials(@RequestBody ProfileUpdateRequestDto requestDto) {
        User user = getAuthenticatedUser();

        if (requestDto.getUsername() != null && !requestDto.getUsername().isBlank()) {
            user.setUserName(requestDto.getUsername());
        }
        if (requestDto.getPassword() != null && !requestDto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        }
        userRepository.save(user);

        return ResponseEntity.ok("Credentials updated successfully. Please log in again.");
    }

    private User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new ResourceNotFoundException("User not authenticated");
        }
        return userRepository.findByUserName(auth.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
