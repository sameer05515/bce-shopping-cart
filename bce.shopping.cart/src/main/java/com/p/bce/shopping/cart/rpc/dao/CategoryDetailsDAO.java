package com.p.bce.shopping.cart.rpc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.p.bce.shopping.cart.rpc.pojo.CategoryDetailsDTO;

public class CategoryDetailsDAO extends AbstractDAO {

	public List<CategoryDetailsDTO> getAllCategoryDetails() {
		List<CategoryDetailsDTO> result=new ArrayList<CategoryDetailsDTO>();
		try {
			ResultSet rs = null;
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement("select CategoryId, CategoryName  from category_details");

			
			rs = ps.executeQuery();
			while (rs.next()) {
				CategoryDetailsDTO dtoFromDb = new CategoryDetailsDTO(rs.getInt("CategoryId"), 
						rs.getString("CategoryName"));
				result.add(dtoFromDb);
			}
			closeConnection(con);
		} catch (Exception ex) {
			result =new ArrayList<CategoryDetailsDTO>();
			ex.printStackTrace();
		}
		return result;
	}

}
