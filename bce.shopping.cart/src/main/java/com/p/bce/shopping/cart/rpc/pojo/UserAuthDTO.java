package com.p.bce.shopping.cart.rpc.pojo;

public class UserAuthDTO {
	private String userName;
	private String password;
	public UserAuthDTO() {
		super();
	}
	public UserAuthDTO(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "UserAuthDTO [userName=" + userName + ", password=" + password + "]";
	}
	
}
