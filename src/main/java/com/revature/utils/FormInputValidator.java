package com.revature.utils;

import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;

import jakarta.validation.constraints.Email;

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
		return password.length() >= 4;
	}

	public static boolean checkName(String name) {
		return name.matches("^[a-zA-Z]{2,24}");
	}


  public static boolean isValidAmount(String amount) {
		return amount.matches("([0-9]{1,3},)*[0-9]*\\.[0-9]{2}");
  }

  public static boolean isValidDescription(String desciption) {
		return desciption.length() <= 255;
  }

	public static boolean isValidType(String type) {
		try {
			ReimbursementType.valueOf(type.toUpperCase());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static long parseAmount(String amount) throws NumberFormatException {
    // Enforced 2 decimal places, so we can round to the nearest cent
    // optional commas are allowed, so we need to remove them
    if(isValidAmount(amount)){
      throw new NumberFormatException("Invalid amount");
    }
    return Math.round(Double.parseDouble(amount.replace(",", "")) * 100);
  }

  public static boolean isValidStatus(String status) {
		try {
			ReimbursementStatus.valueOf(status.toUpperCase());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static void main(String[] args) {
		System.out.println(isValidAmount("420.69"));
		System.out.println(checkEmail("Manager@mail.com"));
	}

}
