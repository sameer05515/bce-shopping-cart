package com.p.bce.shopping.cart.rpc.pojo;

public class UserProfileDTO {
	
	private String userName;
	private String password;
	private String password2;
	private String firstName;
	private String middleName;
	private String lastName;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String pinCode;
	private String email;
	private String phone;
	
	public UserProfileDTO() {
		super();
	}

	public UserProfileDTO(String userName, String password, String password2, String firstName, String middleName,
			String lastName, String address1, String address2, String city, String state, String pincode, String email,
			String phone) {
		super();
		this.userName = userName;
		this.password = password;
		this.password2 = password2;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.state = state;
		this.pinCode = pincode;
		this.email = email;
		this.phone = phone;
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

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPincode(String pincode) {
		this.pinCode = pincode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "UserProfileDTO [userName=" + userName + ", password=" + password + ", password2=" + password2
				+ ", firstName=" + firstName + ", middleName=" + middleName + ", lastName=" + lastName + ", address1="
				+ address1 + ", address2=" + address2 + ", city=" + city + ", state=" + state + ", pincode=" + pinCode
				+ ", email=" + email + ", phone=" + phone + "]";
	}
	
	

}
