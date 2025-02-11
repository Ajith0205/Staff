package com.dar.staff.dto;

import java.util.List;

import com.dar.staff.model.Department;
import com.dar.staff.model.Role;
import com.dar.staff.model.UserInfo;
import com.dar.staff.model.WorkingDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {
	private UserInfo userInfo;
	private List<UserInfo>userInfos;
	private Department department;
	private List<Department>departments;
	private List<Role>roles;
	private WorkingDetails workingDetails;
	private List<WorkingDetails>workingDetailsList;

}
