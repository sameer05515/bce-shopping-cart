package com.p.bce.shopping.cart.rpc.pojo;

/**
 * Data Transfer Object for password change requests
 */
public class PasswordChangeDTO {
	
	private String userName;
	private String currentPassword;
	private String newPassword;
	private String confirmPassword;
	
	public PasswordChangeDTO() {
		super();
	}
	
	public PasswordChangeDTO(String userName, String currentPassword, String newPassword, String confirmPassword) {
		super();
		this.userName = userName;
		this.currentPassword = currentPassword;
		this.newPassword = newPassword;
		this.confirmPassword = confirmPassword;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getCurrentPassword() {
		return currentPassword;
	}
	
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	
	public String getNewPassword() {
		return newPassword;
	}
	
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	public String getConfirmPassword() {
		return confirmPassword;
	}
	
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	/**
	 * Validates that new password and confirm password match
	 * @return true if passwords match
	 */
	public boolean passwordsMatch() {
		return newPassword != null && newPassword.equals(confirmPassword);
	}
}

