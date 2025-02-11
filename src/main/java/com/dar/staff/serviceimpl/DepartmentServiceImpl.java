package com.dar.staff.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dar.staff.dto.DepartmentDto;
import com.dar.staff.dto.ResponseDto;
import com.dar.staff.model.Department;
import com.dar.staff.model.UserInfo;
import com.dar.staff.repository.DepartmentRepo;
import com.dar.staff.repository.UserInfoRepo;
import com.dar.staff.service.DepartmentService;
import com.dar.staff.util.ResponseJson;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentRepo departmentRepo;
	@Autowired
	private UserInfoRepo userInfoRepo;

	private ResponseJson responseJson = new ResponseJson();

	@Override
	public ResponseEntity<Object> createDepartment(String token, DepartmentDto department) {

		String jwtToken = token.replaceFirst("Bearer", "");
		try {
			DecodedJWT jwt = JWT.decode(jwtToken.trim());
			Optional<UserInfo> loggedInUser = userInfoRepo.findByUsername(jwt.getSubject());
			ResponseDto responseDto = new ResponseDto();
			if (loggedInUser.isPresent()) {
				if (department.getId() != null) {
					Optional<Department> departmentopt = departmentRepo.findById(department.getId());
					if (departmentopt.isPresent()) {
						if (department.getDepartmentName() != null) {
							if (!departmentopt.get().getDepartmentName().equals(department.getDepartmentName())) {
								Optional<Department> department1 = departmentRepo
										.findByDepartmentName(department.getDepartmentName());
								if (department1.isEmpty()) {
									Department departmentNew = new Department();
									departmentNew.setId(departmentopt.get().getId());
									departmentNew.setDepartmentName(department.getDepartmentName());
									departmentNew.setStatus(departmentopt.get().isStatus());
									departmentNew = departmentRepo.saveAndFlush(departmentNew);
									responseDto.setDepartment(departmentNew);
									return responseJson.responseCreation(true, "Department Update Success ", null,
											responseDto);
								} else {
									return responseJson.responseCreation(false, "Department is already exist ",
											"Department is already exist", null);
								}

							} else {
								Department departmentNew = new Department();
								departmentNew.setId(departmentopt.get().getId());
								departmentNew.setDepartmentName(department.getDepartmentName());
								departmentNew.setStatus(departmentopt.get().isStatus());
								departmentNew = departmentRepo.save(departmentNew);
								responseDto.setDepartment(departmentNew);
								return responseJson.responseCreation(true, "Department Update Success ", null,
										responseDto);

							}

						} else {
							return responseJson.responseCreation(false, "Department name is empty ",
									"Department name is empty", null);
						}

					}
				} else {
					if (department.getDepartmentName() != null) {
						Optional<Department> department1 = departmentRepo
								.findByDepartmentName(department.getDepartmentName());
						if (department1.isEmpty()) {
							Department departmentNew = new Department();
							departmentNew.setDepartmentName(department.getDepartmentName());
							departmentNew.setStatus(true);
							departmentNew = departmentRepo.save(departmentNew);
							responseDto.setDepartment(departmentNew);
							return responseJson.responseCreation(true, "Department create Success ", null, responseDto);
						} else {
							return responseJson.responseCreation(false, "Department is already exist ",
									"Department is already exist", null);
						}
					} else {
						return responseJson.responseCreation(false, "Department name is empty ",
								"Department name is empty", null);
					}
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return responseJson.responseCreation(false, "User is not presnt ", "failed", null);

	}

	@Override
	public ResponseEntity<Object> getList(String token) {

		String jwtToken = token.replaceFirst("Bearer", "");
		try {
			DecodedJWT jwt = JWT.decode(jwtToken.trim());
			Optional<UserInfo> loggedInUser = userInfoRepo.findByUsername(jwt.getSubject());
			ResponseDto responseDto = new ResponseDto();
			if (loggedInUser.isPresent()) {
				List<Department> departments = departmentRepo.findByStatus(true);
				responseDto.setDepartments(departments);
				return responseJson.responseCreation(true, "department list get ", "success", responseDto);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return responseJson.responseCreation(false, "User is not presnt ", "failed", null);
	}

	@Override
	public ResponseEntity<Object> getDepartmentDetails(String token, Long id) {
		// TODO Auto-generated method stub
		String jwtToken = token.replaceFirst("Bearer", "");
		try {
			DecodedJWT jwt = JWT.decode(jwtToken.trim());
			Optional<UserInfo> loggedInUser = userInfoRepo.findByUsername(jwt.getSubject());
			ResponseDto responseDto = new ResponseDto();
			if (loggedInUser.isPresent()) {
				Optional<Department> departmentopt = departmentRepo.findById(id);
				if (departmentopt.isPresent()) {
					responseDto.setDepartment(departmentopt.get());
					return responseJson.responseCreation(true, "Department Details Success", "found", responseDto);
				}
				return responseJson.responseCreation(false, "Department is not exists", "failed", null);
			}
			return responseJson.responseCreation(false, "User is not presnt ", "failed", null);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return responseJson.responseCreation(false, "User is not presnt ", "failed", null);
	}

	@Override
	public ResponseEntity<Object> statusChange(String token, Long id, boolean status) {
		String jwtToken = token.replaceFirst("Bearer", "");
		try {
			DecodedJWT jwt = JWT.decode(jwtToken.trim());
			Optional<UserInfo> loggedInUser = userInfoRepo.findByUsername(jwt.getSubject());
			ResponseDto responseDto = new ResponseDto();
			if (loggedInUser.isPresent()) {
				Optional<Department> departmentopt = departmentRepo.findById(id);
				if (departmentopt.isPresent()) {
					departmentopt.get().setStatus(status);
					Department department = departmentRepo.saveAndFlush(departmentopt.get());
					return responseJson.responseCreation(true, "status update", "success", responseDto);
				}
				return responseJson.responseCreation(false, "Department is not exists", "failed", null);
			}
			return responseJson.responseCreation(false, "User is not presnt ", "failed", null);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return responseJson.responseCreation(false, "User is not presnt ", "failed", null);
	}

	@Override
	public ResponseEntity<Object> deleteById(String token, Long id) {
		// TODO Auto-generated method stub
		String jwtToken = token.replaceFirst("Bearer", "");
		try {
			DecodedJWT jwt = JWT.decode(jwtToken.trim());
			Optional<UserInfo> loggedInUser = userInfoRepo.findByUsername(jwt.getSubject());
			ResponseDto responseDto = new ResponseDto();
			if (loggedInUser.isPresent()) {
				Optional<Department> departmentopt = departmentRepo.findById(id);
				if (departmentopt.isPresent()) {
					departmentRepo.deleteById(id);
					return responseJson.responseCreation(true, "Department Delete Success", "Failed", responseDto);
				}
				return responseJson.responseCreation(false, "Department is not exists", "Failed", null);
			}
			return responseJson.responseCreation(false, "User is not presnt ", "Failed", null);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return responseJson.responseCreation(false, "User is not presnt ", "failed", null);
	}

}
