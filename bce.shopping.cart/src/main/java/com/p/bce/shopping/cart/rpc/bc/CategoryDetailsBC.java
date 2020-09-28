package com.p.bce.shopping.cart.rpc.bc;

import java.util.List;

import com.p.bce.shopping.cart.rpc.dao.CategoryDetailsDAO;
import com.p.bce.shopping.cart.rpc.pojo.CategoryDetailsDTO;

public class CategoryDetailsBC {
	private CategoryDetailsDAO objCategoryDetailsDAO;
	
	public CategoryDetailsBC() {
		objCategoryDetailsDAO=new CategoryDetailsDAO();
	}

	public List<CategoryDetailsDTO> getAllCategoryDetails() {
		
		return objCategoryDetailsDAO.getAllCategoryDetails();
	}

}
