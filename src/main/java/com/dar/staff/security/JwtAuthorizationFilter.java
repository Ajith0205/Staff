package com.dar.staff.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.dar.staff.exception.TokenInvalidorExpiredException;
import com.dar.staff.model.UserInfo;
import com.dar.staff.model.UserPrincipal;
import com.dar.staff.repository.UserInfoRepo;
import com.dar.staff.util.JwtProperties;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	private UserInfoRepo userInfoRepository;

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserInfoRepo userInfoRepository) {
		super(authenticationManager);
		this.userInfoRepository = userInfoRepository;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String header = request.getHeader(JwtProperties.HEADER);
		if (header == null || !header.startsWith(JwtProperties.PREFIX)) {
			chain.doFilter(request, response);
			return;
		}
		Authentication authentication = getUsernamePasswordAuthentication(request);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}
	
	private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {
		String token = request.getHeader(JwtProperties.HEADER).replace(JwtProperties.PREFIX, "");
		if (token != null) {
			String username = null;
			try {
				username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET.getBytes())).build().verify(token)
						.getSubject();
			} catch (JWTVerificationException | IllegalArgumentException e) {
				return null;
			}
			if (username != null) {
				Optional<UserInfo> user = userInfoRepository.findByUsername(username);
					boolean status = false;
					if (user.isPresent() && user.get().isStatus()) {
						UserPrincipal userPrincipal = new UserPrincipal(user.get());
						UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null,
								userPrincipal.getAuthorities());
						return auth;
					}  else {
						return null;
					}
				
			} else {
				throw new TokenInvalidorExpiredException("Token is not valid!");
			}
		}
		return null;
	}

}
