package com.dar.staff.service;

import org.springframework.http.ResponseEntity;

import com.dar.staff.dto.UserinfoDTO;


public interface UserinfoService {

	ResponseEntity<Object> createUser(UserinfoDTO userinfoDTO);

	ResponseEntity<Object> getById(String token, Long id);

	ResponseEntity<Object> getAllUsers(String token);

	ResponseEntity<Object> deletebyUser(String token, Long id);
}
