package com.dar.staff.util;

import com.dar.staff.dto.ResponseDto;
import com.dar.staff.exception.ExceptionBean;
import com.fasterxml.jackson.annotation.JsonFilter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonFilter("ResponseModelFilter")
public class ResponseModel {

	private boolean status;
	private ExceptionBean information;
	private ResponseDto responseDto;

	
	
	
	
}
