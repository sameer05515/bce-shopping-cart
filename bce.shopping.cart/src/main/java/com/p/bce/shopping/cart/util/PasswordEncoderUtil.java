package com.p.bce.shopping.cart.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Utility class for password encoding and verification using BCrypt
 */
@Component
public class PasswordEncoderUtil {
	
	private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
	
	/**
	 * Encodes a raw password using BCrypt
	 * @param rawPassword The plain text password
	 * @return The hashed password
	 */
	public String encode(String rawPassword) {
		if (rawPassword == null || rawPassword.isEmpty()) {
			throw new IllegalArgumentException("Password cannot be null or empty");
		}
		return passwordEncoder.encode(rawPassword);
	}
	
	/**
	 * Verifies if a raw password matches the encoded password
	 * @param rawPassword The plain text password
	 * @param encodedPassword The hashed password from database
	 * @return true if passwords match, false otherwise
	 */
	public boolean matches(String rawPassword, String encodedPassword) {
		if (rawPassword == null || encodedPassword == null) {
			return false;
		}
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}
	
	/**
	 * Checks if a password needs to be re-encoded (for password upgrade scenarios)
	 * @param encodedPassword The hashed password
	 * @return true if password needs re-encoding
	 */
	public boolean needsRehash(String encodedPassword) {
		return passwordEncoder.upgradeEncoding(encodedPassword);
	}
}

