package com.dar.staff.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dar.staff.model.Department;
import com.dar.staff.model.UserInfo;
import com.dar.staff.model.WorkingDetails;

public interface WorkingDetailsRepository extends JpaRepository<WorkingDetails,Long> {

	List<WorkingDetails> findByDepartment(Department department);

	List<WorkingDetails> findByUserInfo(UserInfo userInfo);

}
