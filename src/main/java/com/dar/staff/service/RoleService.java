/**
 * 
 */
package com.dar.staff.service;

import org.springframework.http.ResponseEntity;

/**
 * Ajith A
 */
public interface RoleService {

	ResponseEntity<Object> getAllRoles(String token);

}
