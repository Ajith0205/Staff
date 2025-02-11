/**
 * 
 */
package com.dar.staff.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dar.staff.service.RoleService;

/**
 * 
 */
@RestController
@RequestMapping("/role/")
public class RoleController {
	@Autowired
	private RoleService roleService;
	
	/**
	 * get AllRoles
	 * 
	 * @param token
	 * @return
	 */
	
	@GetMapping("findAllRole")
	public ResponseEntity<Object>getAllRoles(@RequestHeader("Authorization") String token){
		return roleService.getAllRoles(token);
		
	}

}
