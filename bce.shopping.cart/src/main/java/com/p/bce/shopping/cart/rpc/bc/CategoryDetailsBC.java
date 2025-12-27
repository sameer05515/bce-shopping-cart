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

	public CategoryDetailsDTO getCategoryById(int categoryId) {
		return objCategoryDetailsDAO.getCategoryById(categoryId);
	}

	public boolean createCategory(CategoryDetailsDTO category) {
		// Validate required fields
		if (category.getCategoryName() == null || category.getCategoryName().trim().isEmpty()) {
			return false;
		}

		// Check if category name already exists
		if (objCategoryDetailsDAO.categoryExists(category.getCategoryName())) {
			return false;
		}

		return objCategoryDetailsDAO.createCategory(category) > 0;
	}

	public boolean updateCategory(CategoryDetailsDTO category) {
		// Validate required fields
		if (category.getCategoryId() <= 0) {
			return false;
		}
		if (category.getCategoryName() == null || category.getCategoryName().trim().isEmpty()) {
			return false;
		}

		// Check if another category with same name exists
		CategoryDetailsDTO existing = objCategoryDetailsDAO.getCategoryById(category.getCategoryId());
		if (existing == null) {
			return false;
		}

		// If name changed, check for duplicates
		if (!existing.getCategoryName().equalsIgnoreCase(category.getCategoryName())) {
			if (objCategoryDetailsDAO.categoryExists(category.getCategoryName())) {
				return false;
			}
		}

		return objCategoryDetailsDAO.updateCategory(category);
	}

	public boolean deleteCategory(int categoryId) {
		if (categoryId <= 0) {
			return false;
		}
		return objCategoryDetailsDAO.deleteCategory(categoryId);
	}

	public boolean categoryExists(String categoryName) {
		return objCategoryDetailsDAO.categoryExists(categoryName);
	}

}
