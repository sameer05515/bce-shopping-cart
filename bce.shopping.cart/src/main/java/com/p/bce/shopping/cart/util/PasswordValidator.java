package com.p.bce.shopping.cart.util;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * Utility class for password strength validation
 */
@Component
public class PasswordValidator {
	
	// Password must be at least 8 characters, contain at least one uppercase,
	// one lowercase, one digit, and one special character
	private static final String PASSWORD_PATTERN = 
			"^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
	
	private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
	
	// Minimum length requirement
	private static final int MIN_LENGTH = 8;
	
	// Maximum length requirement
	private static final int MAX_LENGTH = 50;
	
	/**
	 * Validates password strength
	 * @param password The password to validate
	 * @return ValidationResult with isValid flag and error message
	 */
	public ValidationResult validate(String password) {
		if (password == null || password.isEmpty()) {
			return new ValidationResult(false, "Password cannot be empty");
		}
		
		if (password.length() < MIN_LENGTH) {
			return new ValidationResult(false, 
				String.format("Password must be at least %d characters long", MIN_LENGTH));
		}
		
		if (password.length() > MAX_LENGTH) {
			return new ValidationResult(false, 
				String.format("Password must not exceed %d characters", MAX_LENGTH));
		}
		
		if (!pattern.matcher(password).matches()) {
			return new ValidationResult(false, 
				"Password must contain at least one uppercase letter, one lowercase letter, " +
				"one digit, and one special character (@$!%*?&)");
		}
		
		return new ValidationResult(true, "Password is valid");
	}
	
	/**
	 * Checks if password meets minimum requirements (simpler validation)
	 * @param password The password to check
	 * @return true if password meets minimum requirements
	 */
	public boolean meetsMinimumRequirements(String password) {
		if (password == null || password.length() < MIN_LENGTH) {
			return false;
		}
		return true;
	}
	
	/**
	 * Result class for password validation
	 */
	public static class ValidationResult {
		private final boolean valid;
		private final String message;
		
		public ValidationResult(boolean valid, String message) {
			this.valid = valid;
			this.message = message;
		}
		
		public boolean isValid() {
			return valid;
		}
		
		public String getMessage() {
			return message;
		}
	}
}

