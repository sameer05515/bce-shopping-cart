package com.p.bce.shopping.cart.rpc;

import java.util.List;

import com.p.bce.shopping.cart.rpc.bc.CategoryDetailsBC;
import com.p.bce.shopping.cart.rpc.bc.UserProfileBC;
import com.p.bce.shopping.cart.rpc.pojo.CategoryDetailsDTO;
import com.p.bce.shopping.cart.rpc.pojo.UserAuthDTO;
import com.p.bce.shopping.cart.rpc.pojo.UserProfileDTO;

public final class ShoppingApiRPC {
	private static UserProfileBC objUserProfileBC;
	private static CategoryDetailsBC objCategoryDetailsBC;

	private ShoppingApiRPC() {
		
	}
	static {
		objUserProfileBC = new UserProfileBC();
		objCategoryDetailsBC=new CategoryDetailsBC();
	}

	/**User_Profile*/
	public static boolean userNameExists(UserProfileDTO objUserProfileDTO) {
		return objUserProfileBC.keyExists(objUserProfileDTO);
	}

	public static boolean saveUserProfile(UserProfileDTO objUserProfileDTO) {
		
		return objUserProfileBC.save(objUserProfileDTO);
	}
	
	/**User_Auth*/
	public static UserAuthDTO validateUserAuth(UserAuthDTO objUserAuthDTO) {
		return objUserProfileBC.validate(objUserAuthDTO);
	}
	
	/**Category_details*/
	public static List<CategoryDetailsDTO> getAllCategoryDetails() {
		return objCategoryDetailsBC.getAllCategoryDetails();
	}
}
