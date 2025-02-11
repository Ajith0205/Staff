package com.dar.staff.service;

import org.springframework.http.ResponseEntity;

import com.dar.staff.dto.DepartmentDto;

public interface DepartmentService {

	ResponseEntity<Object> createDepartment(String token, DepartmentDto department);

	ResponseEntity<Object> getList(String token);

	ResponseEntity<Object> getDepartmentDetails(String token, Long id);

	ResponseEntity<Object> statusChange(String token, Long id, boolean status);
	
	ResponseEntity<Object>deleteById(String token,Long id);
}
