/**
 * 
 */
package com.dar.staff.util;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;

import com.dar.staff.dto.ResponseDto;
import com.dar.staff.exception.ExceptionBean;


/**
 * Ajith A
 */
public class ResponseJson {
	private ResponseModel responseModel=new ResponseModel();
	
	private MappingJacksonValue mappingJacksonValue;
	
	public ResponseEntity<Object>responseCreation(Boolean status, String message,String description 
			 ,ResponseDto responseDto){
		responseModel.setStatus(status);
		responseModel.setInformation(new ExceptionBean(new Date(), message, description));
		if(responseDto !=null) {
			responseModel.setResponseDto(responseDto);
			mappingJacksonValue = FilterUtil.filterFields(responseModel, new String[] { "status", "information", "responseDto" });
			
		}else {
			mappingJacksonValue = FilterUtil.filterFields(responseModel, new String[] { "status", "information" });
		}
		return new ResponseEntity<Object>(mappingJacksonValue, HttpStatus.OK);
		
		
				
		
	}

}
