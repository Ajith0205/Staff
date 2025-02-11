package com.dar.staff.serviceimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dar.staff.model.UserInfo;
import com.dar.staff.model.UserPrincipal;
import com.dar.staff.repository.UserInfoRepo;

@Service
public class UserPrincipalDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserInfoRepo userInfoRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			Optional<UserInfo> user = userInfoRepository.findByUsername(username);
			if (user.isPresent()) {
				UserPrincipal userPrincipal = new UserPrincipal(user.get());
				System.out.println(userPrincipal.getAuthorities());
				return userPrincipal;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		throw new UsernameNotFoundException(username);
	}
}
