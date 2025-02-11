package com.dar.staff.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dar.staff.dto.DepartmentDto;
import com.dar.staff.dto.UserinfoDTO;
import com.dar.staff.model.UserInfo;
import com.dar.staff.service.DepartmentService;
import com.dar.staff.service.UserinfoService;

@RestController
@RequestMapping("/user")
public class UserInfoController {
	
	@Autowired
	UserinfoService userinfoService;
	@Autowired
	DepartmentService departmentService;
	
	
	/**
	 * create /update
	 * @param token
	 * @param userInfoDto
	 * @return
	 */
	@PostMapping("/create")
	public  ResponseEntity<Object> createUser(@RequestHeader("Authorization") String token, @RequestBody UserinfoDTO userInfoDto) {

		return userinfoService.createUser(userInfoDto);
	}
	/**
	 * find by user 
	 * @param token
	 * @param id
	 * @return
	 */
	@GetMapping("/findby/{id}")
	public ResponseEntity<Object>getByUserId(@RequestHeader("Authorization") String token,@PathVariable Long id){
		return userinfoService.getById(token,id);
		
	}
	/**
	 * find All users
	 * @param token
	 * @return
	 */
	@GetMapping("/findAll")
	public ResponseEntity<Object>findAll(@RequestHeader("Authorization") String token){
		return userinfoService.getAllUsers(token);
		
	}
	/**
	 * delete user
	 * @param token
	 * @param id
	 * @return
	 */
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object>deleteUser(@RequestHeader("Authorization") String token,@PathVariable Long id){
		return userinfoService.deletebyUser(token,id);
		
	}
	
	
	

}
