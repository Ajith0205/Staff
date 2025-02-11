package com.dar.staff.util;

public class JwtProperties {
	public static final String SECRET = "5A7134743777217A25432A462D4A614E635266556A586E3272357538782F413F";
	public static final int EXPIRATION = 84600000; //1 day
//	public static final long EXPIRATION = 2538000000L; //30 days
	public static final String PREFIX = "Bearer ";
	public static final String HEADER = "Authorization";
}
