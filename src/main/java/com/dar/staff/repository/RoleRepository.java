/**
 * 
 */
package com.dar.staff.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dar.staff.model.Role;

/**
 * Ajith A
 */
public interface RoleRepository extends JpaRepository<Role,Long>{

	Optional<Role> findByRole(String role);

}
