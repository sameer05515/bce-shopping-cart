package com.p.bce.shopping.cart.rpc.bc;

import java.util.List;

import com.p.bce.shopping.cart.rpc.dao.CategoryDetailsDAO;
import com.p.bce.shopping.cart.rpc.pojo.CategoryDetailsDTO;
import com.p.bce.shopping.cart.rpc.pojo.SearchedBookCategories;

public class CategoryDetailsBC {
	private CategoryDetailsDAO objCategoryDetailsDAO;
	
	public CategoryDetailsBC() {
		objCategoryDetailsDAO=new CategoryDetailsDAO();
	}

	public List<CategoryDetailsDTO> getAllCategoryDetails() {
		
		return objCategoryDetailsDAO.getAllCategoryDetails();
	}

	public List<SearchedBookCategories> getSearchedBookCategories(String str_searchvar, String str_colvar, int chk_ctr,
			String tab_var) {

		return objCategoryDetailsDAO.getSearchedBookCategories(str_searchvar, str_colvar, chk_ctr, tab_var);
	}

	public boolean deleteOldTempDetails() {

		return objCategoryDetailsDAO.deleteOldTempDetails();
	}

	public int insertIntoTempDetails(SearchedBookCategories objSearchedBookCategories) {
		// TODO Auto-generated method stub
		return objCategoryDetailsDAO.insertIntoTempDetails(objSearchedBookCategories);
	}

}
