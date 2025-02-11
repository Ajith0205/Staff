package com.dar.staff.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.dar.staff.model.AuthRequest;
import com.dar.staff.model.UserInfo;
import com.dar.staff.model.UserPrincipal;
import com.dar.staff.repository.UserInfoRepo;
import com.dar.staff.util.JwtProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	private AuthRequest authRequest = null;

	private UserInfoRepo userInfoRepository;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, UserInfoRepo userInfoRepository) {
		super.setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
		super.setAuthenticationManager(authenticationManager);
		this.authenticationManager = authenticationManager;
		this.userInfoRepository = userInfoRepository;
		setFilterProcessesUrl("/auth/login");
	}

	// login POST method will directly come here...
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			this.authRequest = new ObjectMapper().readValue(request.getInputStream(), AuthRequest.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
				this.authRequest.getUsername(), this.authRequest.getPassword(), new ArrayList<>());
		Authentication auth = null;
		try {
			auth = authenticationManager.authenticate(authToken);
		} catch (AuthenticationException ex) {
			System.out.println(ex.getLocalizedMessage());

			if (ex.getLocalizedMessage().equals("User is disabled")) {
				// Authentication failed
				String jsonResponse = "{\"response\": " + "\"failed\"" + ", " + "\"status\": " + false + ", "
						+ "\"message\": " + "\"User is disabled\"" + "}";
				response.setContentType("application/json");
				PrintWriter out = null;
				try {
					out = response.getWriter();
				} catch (IOException e) {
					e.printStackTrace();
				}
				out.print(jsonResponse);
				out.flush();
			} else {
				try {
					unsuccessfulAuthentication(request, response, ex);
				} catch (IOException | ServletException e) {
					e.printStackTrace();
				}
				return auth;
			}
		}
		return auth;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		UserPrincipal userPrincipal = (UserPrincipal) authResult.getPrincipal();
		Optional<UserInfo> user = userInfoRepository.findByEmail(userPrincipal.getUsername());
		String id = "id";
		String role = "role";
		String username = "username";
		String name = "name";
		String email = "email";
		String token = JWT.create().withSubject(userPrincipal.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION))
				.sign(Algorithm.HMAC512(JwtProperties.SECRET.getBytes()));
		response.addHeader(JwtProperties.HEADER, JwtProperties.PREFIX + token);

		String tokenConstruct = null;

		tokenConstruct = "{" + "\"" + JwtProperties.HEADER + "\":\"" + JwtProperties.PREFIX + token + "\"," + "\"" + id
				+ "\":\"" + user.get().getId() + "\"," + "\"" + role + "\":\"" + user.get().getRole() + "\"," + "\""
				+ username + "\":\"" + user.get().getName() + "\"," + "\"" + email + "\":\"" + user.get().getEmail()
				+ "\"," + "\"code\":" + 200 + "," + "\"rights\":" + "\"All\"" + "}";

		String jsonResponse = "{\"response\": " + tokenConstruct + ", " + "\"status\": " + true + "}";
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(jsonResponse);
		out.flush();

	}

}
