package com.revature.utils;

import com.revature.models.ReimbursementType;

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


  public boolean isValidAmount(String amount) {
		try {
			Long.parseLong(amount);
			return true;
		} catch (NumberFormatException e) {
			return false;
		} catch (Exception e) {
			return false;
		}
  }

  public boolean isValidDescription(String desciption) {
		return desciption.length() <= 255;
  }

	public boolean isValidType(String type) {
		try {
			ReimbursementType.valueOf(type.toUpperCase());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
