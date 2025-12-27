package com.p.bce.shopping.cart.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.p.bce.shopping.cart.rpc.bc.UserProfileBC;
import com.p.bce.shopping.cart.rpc.pojo.UserAuthDTO;
import com.p.bce.shopping.cart.rpc.pojo.UserProfileDTO;

@Controller
public class UserController {

	@Autowired
	private UserProfileBC userProfileBC;

	@PostMapping("/pages/jsp/Validate.jsp")
	public String validateUser(
			@RequestParam("UserName") String userName,
			@RequestParam("Password") String password,
			HttpSession session,
			RedirectAttributes redirectAttributes) {
		
		UserAuthDTO userAuth = new UserAuthDTO(userName, password);
		userAuth = userProfileBC.validate(userAuth);
		
		if (userAuth == null) {
			return "redirect:/pages/html/preLogin/InvalidUser.html";
		}
		
		session.setAttribute("user", userAuth.getUserName());
		
		if ("Administrator".equalsIgnoreCase(userAuth.getUserName())) {
			return "redirect:/pages/html/postLogin/Admin.html";
		} else {
			return "redirect:/pages/html/postLogin/SearchCriteria";
		}
	}

	@PostMapping("/pages/jsp/Sign.jsp")
	public String signUp(
			@RequestParam("UserName") String userName,
			@RequestParam("Password") String password,
			@RequestParam("Password2") String password2,
			@RequestParam("FirstName") String firstName,
			@RequestParam(value = "MiddleName", required = false) String middleName,
			@RequestParam("LastName") String lastName,
			@RequestParam("Address1") String address1,
			@RequestParam(value = "Address2", required = false) String address2,
			@RequestParam("City") String city,
			@RequestParam("State") String state,
			@RequestParam("PinCode") String pinCode,
			@RequestParam("Email") String email,
			@RequestParam("Phone") String phone,
			Model model) {
		
		UserProfileDTO dto = new UserProfileDTO(userName, password, password2, firstName, middleName,
				lastName, address1, address2, city, state, pinCode, email, phone);
		
		boolean exists = userProfileBC.keyExists(dto);
		if (exists) {
			model.addAttribute("error", "User Name already exists");
			return "pages/html/preLogin/NewUser";
		}
		
		boolean success = userProfileBC.save(dto);
		if (success) {
			return "redirect:/pages/html/preLogin/Login.html";
		}
		
		return "pages/html/preLogin/NewUser";
	}

	@GetMapping("/pages/html/postLogin/Logout.jsp")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/pages/html/preLogin/Login.html";
	}
}

