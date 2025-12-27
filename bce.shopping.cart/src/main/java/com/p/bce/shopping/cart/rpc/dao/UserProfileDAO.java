package com.p.bce.shopping.cart.rpc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.p.bce.shopping.cart.rpc.pojo.UserAuthDTO;
import com.p.bce.shopping.cart.rpc.pojo.UserProfileDTO;
import com.p.bce.shopping.cart.util.PasswordEncoderUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserProfileDAO extends AbstractDAO {
	
	@Autowired
	private PasswordEncoderUtil passwordEncoder;

	public boolean keyExists(UserProfileDTO objUserProfileDTO) {
		try {
			Integer count = jdbcTemplate.queryForObject(
					"SELECT COUNT(*) FROM user_profile WHERE username = ?",
					Integer.class,
					objUserProfileDTO.getUserName());
			return count != null && count > 0;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public void save(UserProfileDTO objCategoryDTO) throws Exception {
		// Hash the password before storing
		String hashedPassword = passwordEncoder.encode(objCategoryDTO.getPassword());
		
		// Insert into user_auth
		jdbcTemplate.update(
				"INSERT INTO user_auth(UserName, Password) VALUES (?,?)",
				objCategoryDTO.getUserName(),
				hashedPassword);

		// Insert into user_profile
		jdbcTemplate.update(
				"INSERT INTO user_profile(UserName, Password, FirstName, MiddleName, "
						+ "LastName, Address1, Address2, City, State, PinCode, Email, Phone) "
						+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)",
				objCategoryDTO.getUserName(),
				hashedPassword,
				objCategoryDTO.getFirstName(),
				objCategoryDTO.getMiddleName(),
				objCategoryDTO.getLastName(),
				objCategoryDTO.getAddress1(),
				objCategoryDTO.getAddress2(),
				objCategoryDTO.getCity(),
				objCategoryDTO.getState(),
				objCategoryDTO.getPinCode(),
				objCategoryDTO.getEmail(),
				objCategoryDTO.getPhone());
		
		System.out.println("save");
	}

	public UserAuthDTO validate(UserAuthDTO objUserAuthDTO) {
		try {
			// Get the hashed password from database
			String hashedPassword = jdbcTemplate.queryForObject(
					"SELECT password FROM user_auth WHERE username = ?",
					String.class,
					objUserAuthDTO.getUserName());
			
			// Verify the provided password against the hashed password
			if (hashedPassword != null && passwordEncoder.matches(objUserAuthDTO.getPassword(), hashedPassword)) {
				return new UserAuthDTO(objUserAuthDTO.getUserName(), hashedPassword);
			}
			return null;
		} catch (EmptyResultDataAccessException ex) {
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Get user profile by username
	 */
	public UserProfileDTO getUserProfile(String userName) {
		try {
			return jdbcTemplate.queryForObject(
					"SELECT UserName, FirstName, MiddleName, LastName, Address1, Address2, " +
					"City, State, PinCode, Email, Phone FROM user_profile WHERE UserName = ?",
					new RowMapper<UserProfileDTO>() {
						@Override
						public UserProfileDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
							UserProfileDTO dto = new UserProfileDTO();
							dto.setUserName(rs.getString("UserName"));
							dto.setFirstName(rs.getString("FirstName"));
							dto.setMiddleName(rs.getString("MiddleName"));
							dto.setLastName(rs.getString("LastName"));
							dto.setAddress1(rs.getString("Address1"));
							dto.setAddress2(rs.getString("Address2"));
							dto.setCity(rs.getString("City"));
							dto.setState(rs.getString("State"));
							dto.setPincode(rs.getString("PinCode"));
							dto.setEmail(rs.getString("Email"));
							dto.setPhone(rs.getString("Phone"));
							return dto;
						}
					},
					userName);
		} catch (EmptyResultDataAccessException ex) {
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Update user profile
	 */
	public boolean updateProfile(UserProfileDTO userProfile) {
		try {
			int rowsAffected = jdbcTemplate.update(
					"UPDATE user_profile SET FirstName = ?, MiddleName = ?, LastName = ?, " +
					"Address1 = ?, Address2 = ?, City = ?, State = ?, PinCode = ?, Email = ?, Phone = ? " +
					"WHERE UserName = ?",
					userProfile.getFirstName(),
					userProfile.getMiddleName(),
					userProfile.getLastName(),
					userProfile.getAddress1(),
					userProfile.getAddress2(),
					userProfile.getCity(),
					userProfile.getState(),
					userProfile.getPinCode(),
					userProfile.getEmail(),
					userProfile.getPhone(),
					userProfile.getUserName());
			return rowsAffected > 0;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Change user password
	 */
	public boolean changePassword(String userName, String newHashedPassword) {
		try {
			// Update password in both user_auth and user_profile tables
			int rowsAuth = jdbcTemplate.update(
					"UPDATE user_auth SET Password = ? WHERE UserName = ?",
					newHashedPassword, userName);
			
			int rowsProfile = jdbcTemplate.update(
					"UPDATE user_profile SET Password = ? WHERE UserName = ?",
					newHashedPassword, userName);
			
			return rowsAuth > 0 && rowsProfile > 0;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Get current password hash for validation
	 */
	public String getPasswordHash(String userName) {
		try {
			return jdbcTemplate.queryForObject(
					"SELECT password FROM user_auth WHERE username = ?",
					String.class,
					userName);
		} catch (EmptyResultDataAccessException ex) {
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

}
