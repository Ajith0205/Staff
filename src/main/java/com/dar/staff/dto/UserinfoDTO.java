package com.dar.staff.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserinfoDTO {
	private Long id;
	private String name;
	private String addressingname;
	private String email;
	private String mobilenumber;
	private String username;
	private String password;
	private String permissions;
    private boolean status;
    private Long departmentId;
    private Long roleId;
}
