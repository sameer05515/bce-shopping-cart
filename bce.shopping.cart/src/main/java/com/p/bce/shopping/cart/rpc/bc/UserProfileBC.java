package com.p.bce.shopping.cart.rpc.bc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.p.bce.shopping.cart.rpc.dao.UserProfileDAO;
import com.p.bce.shopping.cart.rpc.pojo.UserAuthDTO;
import com.p.bce.shopping.cart.rpc.pojo.UserProfileDTO;

@Service
public class UserProfileBC {
	
	@Autowired
	private UserProfileDAO objUserProfileDAO;
	
	public boolean keyExists(UserProfileDTO objUserProfileDTO){
		try {
			return objUserProfileDAO.keyExists(objUserProfileDTO);
		} catch (Exception e) {			
			e.printStackTrace();
			return true;
		}
	}

	public boolean save(UserProfileDTO objUserProfileDTO)  {
	     try {
			objUserProfileDAO.save(objUserProfileDTO);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}		
	}

	public UserAuthDTO validate(UserAuthDTO objUserAuthDTO) {
		try {
			return objUserProfileDAO.validate(objUserAuthDTO);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
