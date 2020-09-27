package com.p.bce.shopping.cart.util;

import javax.servlet.http.HttpServletRequest;

import com.p.bce.shopping.cart.rpc.pojo.UserProfileDTO;

public final class ShoppingCartUtil {
	public static UserProfileDTO getUserProfileDTOFromRequest(HttpServletRequest req) {
		UserProfileDTO dto = new UserProfileDTO(req.getParameter("UserName"), req.getParameter("Password"),
				req.getParameter("Password2"), req.getParameter("FirstName"), req.getParameter("MiddleName"),
				req.getParameter("LastName"), req.getParameter("Address1"), req.getParameter("Address2"),
				req.getParameter("City"), req.getParameter("State"), req.getParameter("PinCode"),
				req.getParameter("Email"), req.getParameter("Phone"));
		
		System.out.println("UserProfileDTO : "+dto);

		return dto;
	}
}
