package com.dar.staff.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dar.staff.model.Department;

public interface DepartmentRepo extends JpaRepository<Department, Long> {

    Optional<Department> findByDepartmentName(String departmentName);

	List<Department> findByStatus(boolean b);



}
