package com.dar.staff.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dar.staff.model.WorkingDetails;
import com.dar.staff.service.WorkingDetailsService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/workdetails/")
public class WorkingDetailsController {
	
	@Autowired
	private WorkingDetailsService workingDetailsService;
	
	/**
	 * create /update
	 * @param token
	 * @param workingDetails
	 * @return
	 */
	@PostMapping("create")
	public ResponseEntity<Object>createWorkDetails(@RequestHeader("Authorization")String token,@RequestBody WorkingDetails workingDetails){
		return workingDetailsService.createWorkDetails(token,workingDetails);
		
	}
	
	/**
	 * fetch Working Details 
	 * @param token
	 * @param id
	 * @return
	 */
	@GetMapping("find/{id}")
	public ResponseEntity<Object>fetchDetails(@RequestHeader("Authorization") String token,@PathVariable Long id){
		return workingDetailsService.workingDetailsFetch(token,id);
		
	}
	/**
	 * fetch list of Working Details
	 * @param token
	 * @return
	 */
	
	@GetMapping("findAll")
	public ResponseEntity<Object>getAllWorkingDetails(@RequestHeader("Authorization")String token){
		return workingDetailsService.findAll(token);	
	}
	
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<Object>deleteWorkingDetails(@RequestHeader("Authorization") String token,@PathVariable Long id){
		return workingDetailsService.deleteWorkingDetails(token,id);
	}

}
