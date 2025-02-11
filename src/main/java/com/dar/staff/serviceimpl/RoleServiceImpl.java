package com.dar.staff.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dar.staff.dto.ResponseDto;
import com.dar.staff.model.Role;
import com.dar.staff.model.UserInfo;
import com.dar.staff.repository.RoleRepository;
import com.dar.staff.repository.UserInfoRepo;
import com.dar.staff.service.RoleService;
import com.dar.staff.util.ResponseJson;
@Service
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserInfoRepo userInfoRepo;
	
	private ResponseJson responseJson =new ResponseJson();

	@Override
	public ResponseEntity<Object> getAllRoles(String token) {
		// TODO Auto-generated method stub
		String jwtToken = token.replaceFirst("Bearer", "");
		try {
			DecodedJWT jwt = JWT.decode(jwtToken.trim());
			Optional<UserInfo> loggedInUser = userInfoRepo.findByUsername(jwt.getSubject());
			ResponseDto responseDto=new ResponseDto();
			if(loggedInUser.isPresent()) {
				List<Role>roles=roleRepository.findAll();
				responseDto.setRoles(roles);
				return responseJson.responseCreation(true, "roles get ", "success", responseDto);
			}
			return responseJson.responseCreation(false, "login User is present", "Failed", null);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return responseJson.responseCreation(false, "login User is present", "Failed", null);
	}

}
