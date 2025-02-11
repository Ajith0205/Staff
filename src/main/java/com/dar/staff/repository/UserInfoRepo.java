package com.dar.staff.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dar.staff.model.UserInfo;

@Repository
public interface UserInfoRepo extends JpaRepository<UserInfo, Long> {

	Optional<UserInfo> findByUsername(String username);

	@Query("SELECT u FROM UserInfo u WHERE u.email = :email AND u.id != :id")
	Optional<UserInfo> findByEmailAndNotId(@Param("email") String email, @Param("id") Long id);

	Optional<UserInfo> findByEmail(String email);
}
