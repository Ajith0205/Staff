package com.dar.staff.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dar.staff.dto.ResponseDto;
import com.dar.staff.dto.UserinfoDTO;
import com.dar.staff.model.Department;
import com.dar.staff.model.Role;
import com.dar.staff.model.UserInfo;
import com.dar.staff.repository.DepartmentRepo;
import com.dar.staff.repository.RoleRepository;
import com.dar.staff.repository.UserInfoRepo;
import com.dar.staff.service.UserinfoService;
import com.dar.staff.util.Constants;
import com.dar.staff.util.ResponseJson;


@Service
public class UserinfoServiceImpl implements UserinfoService {

	@Autowired
	UserInfoRepo userinfoRepo;

	@Autowired
	DepartmentRepo departmentRepo;

	@Autowired
	RoleRepository roleRepository;
	
	private ResponseJson responseJson = new ResponseJson();

	@Override
	public ResponseEntity<Object> createUser(UserinfoDTO userinfoDTO) {

		if (userinfoDTO != null) {
			if (userinfoDTO.getRoleId() != null) {
				Optional<Role> role = roleRepository.findById(userinfoDTO.getRoleId());
				if (role.isPresent()) {
					/**
					 * User Update
					 */
					ResponseDto responseDto=new ResponseDto();
					if (userinfoDTO.getId() != null) {
						Optional<UserInfo> userOld = userinfoRepo.findById(userinfoDTO.getId());

						if (userOld.isEmpty()) {
							return responseJson.responseCreation(false, "Failed", "User Doesn't exist", null);

						}

						if (!userOld.get().getEmail().equals(userinfoDTO.getEmail())) {
							Optional<UserInfo> emilUser = userinfoRepo.findByEmailAndNotId(userinfoDTO.getEmail(),
									userinfoDTO.getId());
							if (emilUser.isPresent()) {
								return responseJson.responseCreation(false, "Failed", "This email is already exist", null);
								
							}
							userOld.get().setEmail(userinfoDTO.getEmail());

						}

						if (userinfoDTO.getDepartmentId() > 0 && userinfoDTO.getDepartmentId() != null) {
							Optional<Department> optional = departmentRepo.findById(userinfoDTO.getDepartmentId());
							if (optional.isEmpty()) {
								return responseJson.responseCreation(false, "Failed", "Department doesn't exist", null);
								
							}
							userOld.get().setDepartment(optional.get());
						}
                         
						userOld.get().setAddressingname(userinfoDTO.getAddressingname());
						userOld.get().setMobilenumber(userinfoDTO.getMobilenumber());
						userOld.get().setName(userinfoDTO.getName());
						userOld.get().setRole(role.get());
						userOld.get().setStatus(userinfoDTO.isStatus());
						if(!userOld.get().getPassword().equals(userinfoDTO.getPassword())){
							userOld.get().setPassword(new BCryptPasswordEncoder().encode(userinfoDTO.getPassword()));
						}
					UserInfo userInfo=	userinfoRepo.save(userOld.get());
					responseDto.setUserInfo(userInfo);
						return responseJson.responseCreation(true,"success", "User Update",responseDto);
//						return responseCreation(true, "Sucess", "User Updated", null, null);

					} else {
						/**
						 * create a user
						 */
						UserInfo newUser = new UserInfo();

						Optional<UserInfo> emilUser = userinfoRepo.findByEmail(userinfoDTO.getEmail());
						if (emilUser.isPresent()) {
							return responseJson.responseCreation(false, "Failed", "This email is already exist", null);
						}
						newUser.setEmail(userinfoDTO.getEmail());
						newUser.setUsername(userinfoDTO.getEmail());
						Optional<Department> dept = Optional.empty();
						if (userinfoDTO.getDepartmentId() > 0 && userinfoDTO.getDepartmentId() != null) {
							dept = departmentRepo.findById(userinfoDTO.getDepartmentId());
							if (dept.isEmpty()) {
								return responseJson.responseCreation(false, "Failed", "Department doesn't exist", null);
							}
							newUser.setDepartment(dept.get());
						}

						newUser.setAddressingname(userinfoDTO.getAddressingname());
						newUser.setMobilenumber(userinfoDTO.getMobilenumber());
						newUser.setName(userinfoDTO.getName());
						newUser.setStatus(userinfoDTO.isStatus());
						newUser.setPassword(new BCryptPasswordEncoder().encode(userinfoDTO.getPassword()));
						newUser.setRole(role.get());
						newUser.setPermissions(Constants.PERMISSIONS);
						newUser=userinfoRepo.saveAndFlush(newUser);
						responseDto.setUserInfo(newUser);
						return responseJson.responseCreation(true,"success", "User Create",responseDto);
						
					}
				}
			}

		}
		return null;

	}

	@Override
	public ResponseEntity<Object> getById(String token, Long id) {
		// TODO Auto-generated method stub
		String jwtToken = token.replaceFirst("Bearer", "");
		try {
			DecodedJWT jwt = JWT.decode(jwtToken.trim());
			Optional<UserInfo> loggedInUser = userinfoRepo.findByUsername(jwt.getSubject());
			ResponseDto responseDto=new ResponseDto();
			if (loggedInUser.isPresent()) {
				Optional<UserInfo> useinfo = userinfoRepo.findById(id);
				if (useinfo.isPresent()) {
					responseDto.setUserInfo(useinfo.get());
					return responseJson.responseCreation(true,"success", "Userupdate",responseDto);
				}
				return responseJson.responseCreation(false, "User is not  valid ", "faild", null);
			}
			return responseJson.responseCreation(false, "User is not  valid ", "faild", null);

		} catch (Exception e) {
			// TODO: handle exception
		}
		return responseJson.responseCreation(false, "User is not  valid ", "faild", null);
	}

	@Override
	public ResponseEntity<Object> getAllUsers(String token) {
		String jwtToken = token.replaceFirst("Bearer", "");
		try {
			DecodedJWT jwt = JWT.decode(jwtToken.trim());
			Optional<UserInfo> loggedInUser = userinfoRepo.findByUsername(jwt.getSubject());
			ResponseDto responseDto=new ResponseDto();
			if (loggedInUser.isPresent()) {
				List<UserInfo>userInfos = userinfoRepo.findAll();
				responseDto.setUserInfos(userInfos);
				return responseJson.responseCreation(true, "user found ", "success", responseDto);
					
			
			}
			return responseJson.responseCreation(false, "User is not  valid ", "faild", null);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return responseJson.responseCreation(false, "User is not  valid ", "faild", null);
	}

	@Override
	public ResponseEntity<Object> deletebyUser(String token, Long id) {
		String jwtToken = token.replaceFirst("Bearer", "");
		try {
			DecodedJWT jwt = JWT.decode(jwtToken.trim());
			Optional<UserInfo> loggedInUser = userinfoRepo.findByUsername(jwt.getSubject());
			if (loggedInUser.isPresent()) {
				Optional<UserInfo>userInfo=userinfoRepo.findById(id);
				if(userInfo.isPresent()) {
					userinfoRepo.deleteById(id);
					return responseJson.responseCreation(false, "User deleted successfully", "faild", null);
				}
				return responseJson.responseCreation(false, "User is not  valid ", "faild", null);
			}
			return responseJson.responseCreation(false, "User is not  valid ", "faild", null);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return responseJson.responseCreation(false, "User is not  valid ", "faild", null);
	}

}
