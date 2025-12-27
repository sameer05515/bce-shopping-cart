package com.p.bce.shopping.cart.rpc.bc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.p.bce.shopping.cart.rpc.dao.UserProfileDAO;
import com.p.bce.shopping.cart.rpc.pojo.PasswordChangeDTO;
import com.p.bce.shopping.cart.rpc.pojo.UserAuthDTO;
import com.p.bce.shopping.cart.rpc.pojo.UserProfileDTO;
import com.p.bce.shopping.cart.util.PasswordEncoderUtil;
import com.p.bce.shopping.cart.util.PasswordValidator;

@Service
public class UserProfileBC {
	
	@Autowired
	private UserProfileDAO objUserProfileDAO;
	
	@Autowired
	private PasswordEncoderUtil passwordEncoder;
	
	@Autowired
	private PasswordValidator passwordValidator;
	
	public boolean keyExists(UserProfileDTO objUserProfileDTO){
		try {
			return objUserProfileDAO.keyExists(objUserProfileDTO);
		} catch (Exception e) {			
			e.printStackTrace();
			return true;
		}
	}

	public boolean save(UserProfileDTO objUserProfileDTO)  {
		// Validate password strength
		PasswordValidator.ValidationResult validation = passwordValidator.validate(objUserProfileDTO.getPassword());
		if (!validation.isValid()) {
			System.out.println("Password validation failed: " + validation.getMessage());
			// For now, we'll still save but log the warning
			// In production, you might want to throw an exception
		}
		
		// Validate password confirmation
		if (!objUserProfileDTO.getPassword().equals(objUserProfileDTO.getPassword2())) {
			System.out.println("Passwords do not match");
			return false;
		}
		
		try {
			objUserProfileDAO.save(objUserProfileDTO);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}		
	}

	public UserAuthDTO validate(UserAuthDTO objUserAuthDTO) {
		try {
			return objUserProfileDAO.validate(objUserAuthDTO);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * Get user profile by username
	 */
	public UserProfileDTO getUserProfile(String userName) {
		try {
			return objUserProfileDAO.getUserProfile(userName);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Update user profile
	 */
	public boolean updateProfile(UserProfileDTO userProfile) {
		try {
			return objUserProfileDAO.updateProfile(userProfile);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Change user password
	 */
	public String changePassword(PasswordChangeDTO passwordChangeDTO) {
		// Validate that new password and confirm password match
		if (!passwordChangeDTO.passwordsMatch()) {
			return "New password and confirm password do not match";
		}
		
		// Validate password strength
		PasswordValidator.ValidationResult validation = passwordValidator.validate(passwordChangeDTO.getNewPassword());
		if (!validation.isValid()) {
			return validation.getMessage();
		}
		
		// Verify current password
		String currentPasswordHash = objUserProfileDAO.getPasswordHash(passwordChangeDTO.getUserName());
		if (currentPasswordHash == null) {
			return "User not found";
		}
		
		if (!passwordEncoder.matches(passwordChangeDTO.getCurrentPassword(), currentPasswordHash)) {
			return "Current password is incorrect";
		}
		
		// Check if new password is same as current password
		if (passwordEncoder.matches(passwordChangeDTO.getNewPassword(), currentPasswordHash)) {
			return "New password must be different from current password";
		}
		
		// Hash new password and update
		try {
			String newPasswordHash = passwordEncoder.encode(passwordChangeDTO.getNewPassword());
			boolean success = objUserProfileDAO.changePassword(passwordChangeDTO.getUserName(), newPasswordHash);
			return success ? "SUCCESS" : "Failed to update password";
		} catch (Exception e) {
			e.printStackTrace();
			return "An error occurred while changing password";
		}
	}
	
	/**
	 * Validate password strength (for registration/change)
	 */
	public PasswordValidator.ValidationResult validatePasswordStrength(String password) {
		return passwordValidator.validate(password);
	}
}
