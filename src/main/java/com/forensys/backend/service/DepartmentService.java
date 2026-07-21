package com.forensys.backend.service;

import com.forensys.backend.entity.Department;
import java.util.List;

public interface DepartmentService {
    Department createDepartment(Department department);
    Department getDepartmentById(Long id);
    List<Department> getAllDepartments();
    Department updateDepartment(Long id, Department departmentDetails);
    void deleteDepartment(Long id);
}
