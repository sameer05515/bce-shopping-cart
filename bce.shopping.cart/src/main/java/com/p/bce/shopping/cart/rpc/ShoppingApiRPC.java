package com.p.bce.shopping.cart.rpc;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.p.bce.shopping.cart.rpc.bc.BookDetailsBC;
import com.p.bce.shopping.cart.rpc.bc.CategoryDetailsBC;
import com.p.bce.shopping.cart.rpc.bc.UserProfileBC;
import com.p.bce.shopping.cart.rpc.pojo.BookDetailDTO;
import com.p.bce.shopping.cart.rpc.pojo.CategoryDetailsDTO;
import com.p.bce.shopping.cart.rpc.pojo.SearchedBookCategories;
import com.p.bce.shopping.cart.rpc.pojo.UserAuthDTO;
import com.p.bce.shopping.cart.rpc.pojo.UserProfileDTO;

@Component
public class ShoppingApiRPC implements ApplicationContextAware {
	private static UserProfileBC objUserProfileBC;
	private static CategoryDetailsBC objCategoryDetailsBC;
	private static BookDetailsBC objBookDetailsBC;
	private static ApplicationContext applicationContext;

	private ShoppingApiRPC() {

	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ShoppingApiRPC.applicationContext = applicationContext;
		objUserProfileBC = applicationContext.getBean(UserProfileBC.class);
		objCategoryDetailsBC = applicationContext.getBean(CategoryDetailsBC.class);
		objBookDetailsBC = applicationContext.getBean(BookDetailsBC.class);
	}

	private static void ensureBeansInitialized() {
		if (objUserProfileBC == null && applicationContext != null) {
			objUserProfileBC = applicationContext.getBean(UserProfileBC.class);
			objCategoryDetailsBC = applicationContext.getBean(CategoryDetailsBC.class);
			objBookDetailsBC = applicationContext.getBean(BookDetailsBC.class);
		}
	}

	/** User_Profile */
	public static boolean userNameExists(UserProfileDTO objUserProfileDTO) {
		ensureBeansInitialized();
		return objUserProfileBC.keyExists(objUserProfileDTO);
	}

	public static boolean saveUserProfile(UserProfileDTO objUserProfileDTO) {
		ensureBeansInitialized();
		return objUserProfileBC.save(objUserProfileDTO);
	}

	/** User_Auth */
	public static UserAuthDTO validateUserAuth(UserAuthDTO objUserAuthDTO) {
		ensureBeansInitialized();
		return objUserProfileBC.validate(objUserAuthDTO);
	}

	/** Category_details */
	public static List<CategoryDetailsDTO> getAllCategoryDetails() {
		ensureBeansInitialized();
		return objCategoryDetailsBC.getAllCategoryDetails();
	}

	/** Searched Book Categories */
	public static List<SearchedBookCategories> getSearchedBookCategories(String str_searchvar, String str_colvar,
			int chk_ctr, String tab_var) {
		ensureBeansInitialized();
		return objCategoryDetailsBC.getSearchedBookCategories(str_searchvar, str_colvar, chk_ctr, tab_var);
	}
	
	public static boolean deleteOldTempDetails() {
		ensureBeansInitialized();
		return objCategoryDetailsBC.deleteOldTempDetails();
	}
	
	public static int insertIntoTempDetails(SearchedBookCategories objSearchedBookCategories) {
		ensureBeansInitialized();
		return objCategoryDetailsBC.insertIntoTempDetails(objSearchedBookCategories);
	}
	
	/** Book Details*/
	public static BookDetailDTO getBookDetail(int bookId) {
		ensureBeansInitialized();
		return objBookDetailsBC.getBookDetail(bookId);
	}
}
