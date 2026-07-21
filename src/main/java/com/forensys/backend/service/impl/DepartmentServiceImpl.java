package com.forensys.backend.service.impl;

import com.forensys.backend.entity.Department;
import com.forensys.backend.repository.DepartmentRepository;
import com.forensys.backend.service.DepartmentService;
import com.forensys.backend.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    @Transactional
    public Department createDepartment(Department department) {
        if (departmentRepository.existsByDeptName(department.getDeptName())) {
            throw new IllegalArgumentException("Department name already exists");
        }
        return departmentRepository.save(department);
    }

    @Override
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    @Transactional
    public Department updateDepartment(Long id, Department departmentDetails) {
        Department existing = getDepartmentById(id);
        existing.setDeptName(departmentDetails.getDeptName());
        return departmentRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }
}
