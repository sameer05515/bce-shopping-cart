package com.p.bce.shopping.cart.rpc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.p.bce.shopping.cart.rpc.pojo.UserAuthDTO;
import com.p.bce.shopping.cart.rpc.pojo.UserProfileDTO;


public class UserProfileDAO extends AbstractDAO {

	public boolean keyExists(UserProfileDTO objUserProfileDTO) {

		boolean keyExists = false;

		try {
			ResultSet rs = null;
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement("select *  from user_profile where username=?");

			ps.setString(1, objUserProfileDTO.getUserName());

			rs = ps.executeQuery();
			if (rs.next()) {
				keyExists = true;
			}
			closeConnection(con);
		} catch (Exception ex) {
			keyExists = false;
			ex.printStackTrace();
		}
		System.out.println("keyExists  " + keyExists);
		return keyExists;
	}

	public void save(UserProfileDTO objCategoryDTO) throws Exception {
		Connection con = getConnection();
		PreparedStatement ps1 = con.prepareStatement("insert into user_auth(UserName, Password) values (?,?)");
		int k=1;
		ps1.setString(k++, objCategoryDTO.getUserName());
		ps1.setString(k++, objCategoryDTO.getPassword());
		
		PreparedStatement ps = con
				.prepareStatement("INSERT INTO user_profile(UserName, Password, FirstName, MiddleName, "
						+ "LastName, Address1, Address2, City, State, PinCode, Email, Phone) "
						+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");

		int j = 1;
		
		ps.setString(j++, objCategoryDTO.getUserName());
		ps.setString(j++, objCategoryDTO.getPassword());
		ps.setString(j++, objCategoryDTO.getFirstName());
		ps.setString(j++, objCategoryDTO.getMiddleName());
		ps.setString(j++, objCategoryDTO.getLastName());
		ps.setString(j++, objCategoryDTO.getAddress1());
		ps.setString(j++, objCategoryDTO.getAddress2());
		ps.setString(j++, objCategoryDTO.getCity());
		ps.setString(j++, objCategoryDTO.getState());
		ps.setString(j++, objCategoryDTO.getPinCode());
		ps.setString(j++, objCategoryDTO.getEmail());
		ps.setString(j++, objCategoryDTO.getPhone());
		

//		java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
//		ps.setTimestamp(j++, date);
//		ps.setTimestamp(j++, date);

		
		ps1.executeUpdate();
		ps.executeUpdate();
		System.out.println("save");
		closeConnection(con);
	}

	public UserAuthDTO validate(UserAuthDTO objUserAuthDTO) {

		UserAuthDTO dtoFromDb=null;
		try {
			ResultSet rs = null;
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement("select *  from user_auth where username=? and password=?");

			ps.setString(1, objUserAuthDTO.getUserName());
			ps.setString(2, objUserAuthDTO.getPassword());

			rs = ps.executeQuery();
			if (rs.next()) {
				dtoFromDb = new UserAuthDTO(rs.getString("username"), rs.getString("password"));
			}
			closeConnection(con);
		} catch (Exception ex) {
			dtoFromDb = null;
			ex.printStackTrace();
		}
		return dtoFromDb;
	}

}
