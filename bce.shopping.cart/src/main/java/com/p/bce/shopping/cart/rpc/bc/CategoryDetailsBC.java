package com.p.bce.shopping.cart.rpc.bc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.p.bce.shopping.cart.rpc.dao.CategoryDetailsDAO;
import com.p.bce.shopping.cart.rpc.pojo.CategoryDetailsDTO;
import com.p.bce.shopping.cart.rpc.pojo.SearchedBookCategories;

@Service
public class CategoryDetailsBC {
	
	@Autowired
	private CategoryDetailsDAO objCategoryDetailsDAO;

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
		return objCategoryDetailsDAO.insertIntoTempDetails(objSearchedBookCategories);
	}

}
