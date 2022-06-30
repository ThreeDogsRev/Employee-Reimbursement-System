package com.revature.utils;

public class FormInputValidator {

  public static boolean checkUsername(String username) {
		if(username == null || username.isEmpty()) {
			return false;
		}
		return username.matches("[a-zA-Z10-9]{4,24}");
	}
	
	public static boolean checkEmail(String email) {
		if(email == null || email.isEmpty()) {
			return false;
		}
		return email.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
	}

	public static boolean checkPassword(String password) {
		if(password == null || password.isEmpty()) {
			return false;
		}
		return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,24}$");
	}

	public static boolean checkName(String name) {
		return name.matches("^[a-zA-Z]{2,24}");
	}
  
}
