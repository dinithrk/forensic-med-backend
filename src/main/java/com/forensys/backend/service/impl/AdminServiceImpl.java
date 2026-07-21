package com.forensys.backend.service.impl;

import com.forensys.backend.dto.StaffRequestDto;
import com.forensys.backend.dto.StaffResponseDto;
import com.forensys.backend.entity.Department;
import com.forensys.backend.entity.MedicalOfficer;
import com.forensys.backend.entity.Role;
import com.forensys.backend.entity.Staff;
import com.forensys.backend.entity.User;
import com.forensys.backend.exception.ResourceNotFoundException;
import com.forensys.backend.repository.DepartmentRepository;
import com.forensys.backend.repository.RoleRepository;
import com.forensys.backend.repository.StaffRepository;
import com.forensys.backend.repository.UserRepository;
import com.forensys.backend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private static final String USER_NOT_FOUND_MSG = "User not found: ";

    private final UserRepository userRepository;
    private final StaffRepository staffRepository;
    private final RoleRepository roleRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public StaffResponseDto createStaff(StaffRequestDto requestDto) {
        if (userRepository.existsByUserName(requestDto.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        // 1. Create User
        Set<Role> userRoles = new HashSet<>();
        if (requestDto.getRoles() != null) {
            for (String roleName : requestDto.getRoles()) {
                Role role = roleRepository.findByRoleName(roleName)
                        .orElseGet(() -> roleRepository.save(new Role(null, roleName, null)));
                userRoles.add(role);
            }
        }

        User user = User.builder()
                .userName(requestDto.getUsername())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .roles(userRoles)
                .build();
        user = userRepository.save(user);

        // 2. Create Staff
        Department dept = null;
        if (requestDto.getDepartmentId() != null) {
            dept = departmentRepository.findById(requestDto.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        }

        Staff staff = Staff.builder()
                .user(user)
                .department(dept)
                .specialization(requestDto.getSpecialization())
                .contactNumbers(requestDto.getContactNumbers())
                .build();

        // 3. Create MedicalOfficer if role implies it
        boolean hasProfessionalDetails = (requestDto.getFullName() != null && !requestDto.getFullName().isBlank()) || 
                                         (requestDto.getDesignation() != null && !requestDto.getDesignation().isBlank());

        if (hasProfessionalDetails) {
            String slmc = requestDto.getSlmcRegNo();
            if (slmc != null && slmc.trim().isEmpty()) {
                slmc = null;
            }
            
            MedicalOfficer mo = MedicalOfficer.builder()
                    .fullName(requestDto.getFullName() != null ? requestDto.getFullName() : requestDto.getUsername())
                    .qualifications(requestDto.getQualifications())
                    .slmcRegNo(slmc)
                    .designation(requestDto.getDesignation())
                    .build();
            staff.setMedicalOfficer(mo);
        }

        staffRepository.save(staff);

        return mapUserToDto(user);
    }

    @Override
    public List<StaffResponseDto> getAllStaff() {
        return userRepository.findAll().stream()
                .map(this::mapUserToDto)
                .toList();
    }

    @Override
    public StaffResponseDto getStaffById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_MSG + userId));
        return mapUserToDto(user);
    }

    @Override
    @Transactional
    public StaffResponseDto updateStaffRoles(Long userId, StaffRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_MSG + userId));
        
        updateUserRoles(user, requestDto.getRoles());
        userRepository.save(user);

        Staff staff = staffRepository.findByUser_UserId(userId).orElse(null);
        if (staff != null) {
            updateExistingStaff(staff, requestDto);
        } else if (requestDto.getFullName() != null && !requestDto.getFullName().isBlank()) {
            createNewStaff(user, requestDto);
        }
        
        return mapUserToDto(user);
    }

    private void updateUserRoles(User user, Set<String> roleNames) {
        Set<Role> userRoles = new HashSet<>();
        if (roleNames != null) {
            for (String roleName : roleNames) {
                Role role = roleRepository.findByRoleName(roleName)
                        .orElseGet(() -> roleRepository.save(new Role(null, roleName, null)));
                userRoles.add(role);
            }
        }
        user.setRoles(userRoles);
    }

    private void updateExistingStaff(Staff staff, StaffRequestDto requestDto) {
        if (requestDto.getDepartmentId() != null) {
            Department dept = departmentRepository.findById(requestDto.getDepartmentId()).orElse(null);
            staff.setDepartment(dept);
        }
        if (staff.getMedicalOfficer() != null) {
            if (requestDto.getFullName() != null) staff.getMedicalOfficer().setFullName(requestDto.getFullName());
            if (requestDto.getDesignation() != null) staff.getMedicalOfficer().setDesignation(requestDto.getDesignation());
            if (requestDto.getQualifications() != null) staff.getMedicalOfficer().setQualifications(requestDto.getQualifications());
            
            String slmc = requestDto.getSlmcRegNo();
            if (slmc != null && slmc.trim().isEmpty()) slmc = null;
            staff.getMedicalOfficer().setSlmcRegNo(slmc);
        } else if (requestDto.getFullName() != null && !requestDto.getFullName().isBlank()) {
            String slmc = requestDto.getSlmcRegNo();
            if (slmc != null && slmc.trim().isEmpty()) slmc = null;
            MedicalOfficer mo = MedicalOfficer.builder()
                    .fullName(requestDto.getFullName())
                    .qualifications(requestDto.getQualifications())
                    .slmcRegNo(slmc)
                    .designation(requestDto.getDesignation())
                    .build();
            staff.setMedicalOfficer(mo);
        }
        staffRepository.save(staff);
    }

    private void createNewStaff(User user, StaffRequestDto requestDto) {
        Department dept = null;
        if (requestDto.getDepartmentId() != null) {
            dept = departmentRepository.findById(requestDto.getDepartmentId()).orElse(null);
        }
        
        String slmc = requestDto.getSlmcRegNo();
        if (slmc != null && slmc.trim().isEmpty()) slmc = null;
        
        MedicalOfficer mo = MedicalOfficer.builder()
                .fullName(requestDto.getFullName())
                .qualifications(requestDto.getQualifications())
                .slmcRegNo(slmc)
                .designation(requestDto.getDesignation())
                .build();
                
        Staff newStaff = Staff.builder()
                .user(user)
                .department(dept)
                .medicalOfficer(mo)
                .build();
        staffRepository.save(newStaff);
    }

    @Override
    @Transactional
    public void updateUserCredentials(Long userId, com.forensys.backend.dto.ProfileUpdateRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_MSG + userId));
        if (requestDto.getUsername() != null && !requestDto.getUsername().isBlank()) {
            user.setUserName(requestDto.getUsername());
        }
        if (requestDto.getPassword() != null && !requestDto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        }
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deactivateUser(Long userId) {
        userRepository.deleteById(userId);
    }

    private StaffResponseDto mapUserToDto(User user) {
        StaffResponseDto dto = StaffResponseDto.builder()
                .userId(user.getUserId())
                .username(user.getUserName())
                .roles(user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toSet()))
                .build();

        staffRepository.findByUser_UserId(user.getUserId()).ifPresent(staff -> {
            dto.setStaffId(staff.getStaffId());
            dto.setSpecialization(staff.getSpecialization());
            dto.setContactNumbers(staff.getContactNumbers());
            
            if (staff.getDepartment() != null) {
                dto.setDepartmentId(staff.getDepartment().getDeptId());
                dto.setDepartmentName(staff.getDepartment().getDeptName());
            }

            if (staff.getMedicalOfficer() != null) {
                dto.setFullName(staff.getMedicalOfficer().getFullName());
                dto.setSlmcRegNo(staff.getMedicalOfficer().getSlmcRegNo());
                dto.setDesignation(staff.getMedicalOfficer().getDesignation());
                dto.setQualifications(staff.getMedicalOfficer().getQualifications());
            }
        });
        
        return dto;
    }
}
