package com.p.bce.shopping.cart.rpc.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.p.bce.shopping.cart.rpc.pojo.UserAuthDTO;
import com.p.bce.shopping.cart.rpc.pojo.UserProfileDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserProfileDAO extends AbstractDAO {

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
		// Insert into user_auth
		jdbcTemplate.update(
				"INSERT INTO user_auth(UserName, Password) VALUES (?,?)",
				objCategoryDTO.getUserName(),
				objCategoryDTO.getPassword());

		// Insert into user_profile
		jdbcTemplate.update(
				"INSERT INTO user_profile(UserName, Password, FirstName, MiddleName, "
						+ "LastName, Address1, Address2, City, State, PinCode, Email, Phone) "
						+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)",
				objCategoryDTO.getUserName(),
				objCategoryDTO.getPassword(),
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
			return jdbcTemplate.queryForObject(
					"SELECT username, password FROM user_auth WHERE username = ? AND password = ?",
					new RowMapper<UserAuthDTO>() {
						@Override
						public UserAuthDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
							return new UserAuthDTO(rs.getString("username"), rs.getString("password"));
						}
					},
					objUserAuthDTO.getUserName(),
					objUserAuthDTO.getPassword());
		} catch (EmptyResultDataAccessException ex) {
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

}
