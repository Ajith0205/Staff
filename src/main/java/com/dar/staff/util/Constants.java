package com.dar.staff.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constants {

	public final static String SYSTEMADMINUSERNAME = "Admin";
	public final static String SYSTEMADMINPASSWORD = "Admin@123";
	public final static String SYSTEMADMINEMAIL  = "amalanive628@gmail.com";
	
	public final static List<String> USERROLES = new ArrayList<>(
			Arrays.asList("ADMIN", "HOD","STAFF"));
	public final static String PERMISSIONS = "ADD,EDIT,DELETE,LIST";
}
