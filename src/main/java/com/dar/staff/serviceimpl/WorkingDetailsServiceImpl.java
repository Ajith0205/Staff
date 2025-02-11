/**
 * 
 */
package com.dar.staff.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dar.staff.dto.ResponseDto;
import com.dar.staff.model.Department;
import com.dar.staff.model.UserInfo;
import com.dar.staff.model.WorkingDetails;
import com.dar.staff.repository.DepartmentRepo;
import com.dar.staff.repository.UserInfoRepo;
import com.dar.staff.repository.WorkingDetailsRepository;
import com.dar.staff.service.WorkingDetailsService;
import com.dar.staff.util.Constants;
import com.dar.staff.util.ResponseJson;

@Service
public class WorkingDetailsServiceImpl implements WorkingDetailsService {

	@Autowired
	private UserInfoRepo userInfoRepo;

	@Autowired
	private WorkingDetailsRepository workingDetailsRepository;
	@Autowired
	private DepartmentRepo departmentRepo;

	private ResponseJson responseJson = new ResponseJson();

	@Override
	public ResponseEntity<Object> createWorkDetails(String token, WorkingDetails workingDetails) {
		String jwtToken = token.replaceFirst("Bearer", "");
		try {
			DecodedJWT jwt = JWT.decode(jwtToken.trim());
			Optional<UserInfo> loggedInUser = userInfoRepo.findByUsername(jwt.getSubject());
			ResponseDto responseDto = new ResponseDto();
			if (loggedInUser.isPresent()) {
				if (workingDetails != null) {
					if (workingDetails.getId() != null) {
						if(workingDetails.getUserId() !=null) {
							Optional<UserInfo>user=userInfoRepo.findById(workingDetails.getUserId());
							if(user.isPresent()) {
								workingDetails.setUserInfo(user.get());
							}else {
								return responseJson.responseCreation(false, "User  is not exist", "faild", null);
							}
						}else {
							return responseJson.responseCreation(false, "User Id is null", "faild", null);
						}
					
						if(workingDetails.getDepartmentId() !=null) {
							Optional<Department>dept=departmentRepo.findById(workingDetails.getDepartmentId());
							if(dept.isPresent()) {
								workingDetails.setDepartment(dept.get());
							}else {
								return responseJson.responseCreation(false, "Department  is not exist", "faild", null);
							}
						}else {
							return responseJson.responseCreation(false, "Department Id is null", "faild", null);
						}
						WorkingDetails workingDetails2Details = workingDetailsRepository.saveAndFlush(workingDetails);
						responseDto.setWorkingDetails(workingDetails2Details);
						return responseJson.responseCreation(true, "Work details Update", "success", responseDto);
					} else {
						
						if(workingDetails.getUserId() !=null) {
							Optional<UserInfo>user=userInfoRepo.findById(workingDetails.getUserId());
							if(user.isPresent()) {
								workingDetails.setUserInfo(user.get());
							}else {
								return responseJson.responseCreation(false, "User  is not exist", "faild", null);
							}
						}else {
							return responseJson.responseCreation(false, "User Id is null", "faild", null);
						}
					
						if(workingDetails.getDepartmentId() !=null) {
							Optional<Department>dept=departmentRepo.findById(workingDetails.getDepartmentId());
							if(dept.isPresent()) {
								workingDetails.setDepartment(dept.get());
							}else {
								return responseJson.responseCreation(false, "Department  is not exist", "faild", null);
							}
						}else {
							return responseJson.responseCreation(false, "Department Id is null", "faild", null);
						}
						
						WorkingDetails workingDetails2Details = workingDetailsRepository.saveAndFlush(workingDetails);
						responseDto.setWorkingDetails(workingDetails2Details);
						return responseJson.responseCreation(true, "Work details save", "success", responseDto);
					}
				}
				return responseJson.responseCreation(false, "working details is empty", "faild", null);
			}

			return responseJson.responseCreation(false, "login user is not present", "faild", null);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return responseJson.responseCreation(false, "faild", "faild", null);
	}

	@Override
	public ResponseEntity<Object> workingDetailsFetch(String token, Long id) {
		String jwtToken = token.replaceFirst("Bearer", "");

		try {
			DecodedJWT jwt = JWT.decode(jwtToken.trim());
			Optional<UserInfo> loggedInUser = userInfoRepo.findByUsername(jwt.getSubject());
			ResponseDto responseDto = new ResponseDto();
			if (loggedInUser.isPresent()) {
				if (id != null) {
					Optional<WorkingDetails> workingDtails = workingDetailsRepository.findById(id);
					if (workingDtails.isPresent()) {
						responseDto.setWorkingDetails(workingDtails.get());
						return responseJson.responseCreation(true, "Wokring Details Fetch", "success", responseDto);
					}
					return responseJson.responseCreation(false, "Details not fetched against your id value ", "faild",
							null);
				}
				return responseJson.responseCreation(false, "Id value is not present", "faild", null);
			}
			return responseJson.responseCreation(false, "login user is not present", "faild", null);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return responseJson.responseCreation(false, "faild", "faild", null);
	}

	@Override
	public ResponseEntity<Object> findAll(String token) {
		String jwtToken = token.replaceFirst("Bearer", "");
		try {
			DecodedJWT jwt = JWT.decode(jwtToken.trim());
			Optional<UserInfo> loggedInUser = userInfoRepo.findByUsername(jwt.getSubject());
			ResponseDto responseDto = new ResponseDto();
			if (loggedInUser.isPresent()) {
				if(loggedInUser.get().getRole().getRole().equals(Constants.USERROLES.get(0))) {
					List<WorkingDetails> workingDetails = workingDetailsRepository.findAll();
					responseDto.setWorkingDetailsList(workingDetails);	
				}else if(loggedInUser.get().getRole().getRole().equals(Constants.USERROLES.get(1))) {
					List<WorkingDetails> workingDetails = workingDetailsRepository.findByDepartment(loggedInUser.get().getDepartment());
					responseDto.setWorkingDetailsList(workingDetails);	
				}else {
					List<WorkingDetails> workingDetails=workingDetailsRepository.findByUserInfo(loggedInUser.get());
					responseDto.setWorkingDetailsList(workingDetails);
				}
				
				return responseJson.responseCreation(true, "working details fetch success", "success", responseDto);
			}
			return responseJson.responseCreation(false, "login user is not present", "faild", null);

		} catch (Exception e) {
			// TODO: handle exception
		}
		return responseJson.responseCreation(false, "faild", "faild", null);
	}

	@Override
	public ResponseEntity<Object> deleteWorkingDetails(String token, Long id) {
		String jwtToken = token.replaceFirst("Bearer", "");
		try {
			DecodedJWT jwt = JWT.decode(jwtToken.trim());
			Optional<UserInfo> loggedInUser = userInfoRepo.findByUsername(jwt.getSubject());
			if (loggedInUser.isPresent()) {
				if (id != null) {
					Optional<WorkingDetails> workingDtails = workingDetailsRepository.findById(id);
					if (workingDtails.isPresent()) {
						workingDetailsRepository.deleteById(id);
						return responseJson.responseCreation(true, "Wokring Details delete", "success", null);
					}
					return responseJson.responseCreation(false, "Details not fetched against your id value ", "faild",
							null);
				}
				return responseJson.responseCreation(false, "Id value is not present", "faild", null);
			}
			return responseJson.responseCreation(false, "login user is not present", "faild", null);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return responseJson.responseCreation(false, "faild", "faild", null);
	}

}
