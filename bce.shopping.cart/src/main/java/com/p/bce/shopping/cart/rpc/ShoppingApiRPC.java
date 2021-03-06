package com.p.bce.shopping.cart.rpc;

import java.util.List;

import com.p.bce.shopping.cart.rpc.bc.BookDetailsBC;
import com.p.bce.shopping.cart.rpc.bc.CategoryDetailsBC;
import com.p.bce.shopping.cart.rpc.bc.UserProfileBC;
import com.p.bce.shopping.cart.rpc.pojo.BookDetailDTO;
import com.p.bce.shopping.cart.rpc.pojo.CategoryDetailsDTO;
import com.p.bce.shopping.cart.rpc.pojo.SearchedBookCategories;
import com.p.bce.shopping.cart.rpc.pojo.UserAuthDTO;
import com.p.bce.shopping.cart.rpc.pojo.UserProfileDTO;

public final class ShoppingApiRPC {
	private static UserProfileBC objUserProfileBC;
	private static CategoryDetailsBC objCategoryDetailsBC;
	private static BookDetailsBC objBookDetailsBC;

	private ShoppingApiRPC() {

	}

	static {
		objUserProfileBC = new UserProfileBC();
		objCategoryDetailsBC = new CategoryDetailsBC();
		objBookDetailsBC=new BookDetailsBC();
	}

	/** User_Profile */
	public static boolean userNameExists(UserProfileDTO objUserProfileDTO) {
		return objUserProfileBC.keyExists(objUserProfileDTO);
	}

	public static boolean saveUserProfile(UserProfileDTO objUserProfileDTO) {

		return objUserProfileBC.save(objUserProfileDTO);
	}

	/** User_Auth */
	public static UserAuthDTO validateUserAuth(UserAuthDTO objUserAuthDTO) {
		return objUserProfileBC.validate(objUserAuthDTO);
	}

	/** Category_details */
	public static List<CategoryDetailsDTO> getAllCategoryDetails() {
		return objCategoryDetailsBC.getAllCategoryDetails();
	}

	/** Searched Book Categories */
	public static List<SearchedBookCategories> getSearchedBookCategories(String str_searchvar, String str_colvar,
			int chk_ctr, String tab_var) {
//		List<SearchedBookCategories> 
		return objCategoryDetailsBC.getSearchedBookCategories(str_searchvar, str_colvar, chk_ctr, tab_var);
	}
	
	public static boolean deleteOldTempDetails() {
		return objCategoryDetailsBC.deleteOldTempDetails();
	}
	
	public static int insertIntoTempDetails(SearchedBookCategories objSearchedBookCategories) {
		return objCategoryDetailsBC.insertIntoTempDetails(objSearchedBookCategories);
	}
	
	
	
	/** Book Details*/
	public static BookDetailDTO getBookDetail(int bookId) {
		return objBookDetailsBC.getBookDetail(bookId);
	}
}
