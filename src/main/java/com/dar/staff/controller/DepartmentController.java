/**
 * 
 */
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
import com.dar.staff.service.DepartmentService;

/**
 */
@RestController
@RequestMapping("/dept/")
public class DepartmentController {
	
	@Autowired
	private DepartmentService departmentService;
	
	/**
	 * create and update
	 * @param token
	 * @param departmentDto
	 * @return
	 */
	@PostMapping("/createdept")
	public ResponseEntity<Object>createDepet(@RequestHeader("Authorization") String token,@RequestBody DepartmentDto departmentDto){
		return departmentService.createDepartment(token,departmentDto);
	}
	
	/**
	 * department details fetch 
	 * @param token
	 * @param id
	 * @return
	 */
	@GetMapping("find/{id}")
	public ResponseEntity<Object>getByDepartment(@RequestHeader("Authorization") String token,@RequestParam Long id){
		return departmentService.getDepartmentDetails(token,id);	
	}
	/**
	 * department status true  only datas fetch
	 * @param token
	 * @return
	 */
	@GetMapping("findAll")
	public ResponseEntity<Object>getAllDepartment(@RequestHeader("Authorization") String token){
		return departmentService.getList(token);	
	}
	
	/**
	 * status will be changes
	 * @param token
	 * @param id
	 * @param status
	 * @return
	 */
	@GetMapping("statuschange/{id}/{status}")
	public ResponseEntity<Object>departmentStatusChange(@RequestHeader("Authorization") String token,@PathVariable Long id,@PathVariable boolean status){
		return departmentService.statusChange(token,id,status);
	}
	/**
	 * delete department
	 * @param token
	 * @param id
	 * @return
	 */
	@DeleteMapping("delete/{id}")
	public ResponseEntity<Object>deleteBydepartment(@RequestHeader("Authorization") String token,@PathVariable Long id){
		return departmentService.deleteById(token,id);
		
	}
	

}
