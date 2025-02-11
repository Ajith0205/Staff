package com.dar.staff.service;

import org.springframework.http.ResponseEntity;

import com.dar.staff.model.WorkingDetails;

public interface WorkingDetailsService {

	ResponseEntity<Object> createWorkDetails(String token, WorkingDetails workingDetails);

	ResponseEntity<Object> workingDetailsFetch(String token, Long id);

	ResponseEntity<Object> findAll(String token);

	ResponseEntity<Object> deleteWorkingDetails(String token, Long id);

}
