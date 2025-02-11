package com.dar.staff.configuration;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dar.staff.model.Role;
import com.dar.staff.model.UserInfo;
import com.dar.staff.repository.RoleRepository;
import com.dar.staff.repository.UserInfoRepo;
import com.dar.staff.util.Constants;

@Service
public class InitDatabase implements CommandLineRunner {
	
	@Autowired
	UserInfoRepo userInfoRepo;
	@Autowired
	RoleRepository roleRepository;

	@Override
	public void run(String... args) throws Exception {
		
		createRole(Constants.USERROLES.get(0));
		createRole(Constants.USERROLES.get(1));
		createRole(Constants.USERROLES.get(2));
		
//	Create a system Admin
		
			Optional<UserInfo> userOptional =userInfoRepo.findByUsername(Constants.SYSTEMADMINEMAIL);
			if(!userOptional.isPresent()) {
				UserInfo userInfo = new UserInfo();
				Optional<Role>roleoptional=roleRepository.findByRole(Constants.USERROLES.get(0));
				if(roleoptional.isPresent()) {
					userInfo.setName(Constants.SYSTEMADMINUSERNAME);
					userInfo.setUsername(Constants.SYSTEMADMINEMAIL);
					userInfo.setEmail(Constants.SYSTEMADMINEMAIL);
                    userInfo.setRole(roleoptional.get());
					userInfo.setStatus(true);
					userInfo.setPassword(new BCryptPasswordEncoder().encode(Constants.SYSTEMADMINPASSWORD));
					userInfo.setPermissions(Constants.PERMISSIONS);
					userInfoRepo.save(userInfo);
				}
				
				
				
			}

		
	}

	private void createRole(String role) {
		
		if(role !=null) {
			Optional<Role>roleoptional=roleRepository.findByRole(role);
			if(roleoptional.isEmpty()) {
				Role role2=new Role();
				role2.setDescription(role);
				role2.setRole(role);
				role2.setStatus(true);
				roleRepository.saveAndFlush(role2);
			}
		}
	}

	

	
	
}
