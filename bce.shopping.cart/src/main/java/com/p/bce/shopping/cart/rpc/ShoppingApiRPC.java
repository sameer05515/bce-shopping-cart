package com.p.bce.shopping.cart.rpc;

import com.p.bce.shopping.cart.rpc.bc.UserProfileBC;
import com.p.bce.shopping.cart.rpc.pojo.UserAuthDTO;
import com.p.bce.shopping.cart.rpc.pojo.UserProfileDTO;

public final class ShoppingApiRPC {
	private static UserProfileBC objUserProfileBC;

	private ShoppingApiRPC() {
		
	}
	static {
		objUserProfileBC = new UserProfileBC();
	}

	public static boolean userNameExists(UserProfileDTO objUserProfileDTO) {
		return objUserProfileBC.keyExists(objUserProfileDTO);
	}

	public static boolean saveUserProfile(UserProfileDTO objUserProfileDTO) {
		
		return objUserProfileBC.save(objUserProfileDTO);
	}
	
	public static UserAuthDTO validateUserAuth(UserAuthDTO objUserAuthDTO) {
		return objUserProfileBC.validate(objUserAuthDTO);
	}
}
