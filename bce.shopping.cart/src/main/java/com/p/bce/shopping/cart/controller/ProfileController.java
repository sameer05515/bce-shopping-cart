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
import com.p.bce.shopping.cart.rpc.pojo.PasswordChangeDTO;
import com.p.bce.shopping.cart.rpc.pojo.UserProfileDTO;

@Controller
public class ProfileController {

	@Autowired
	private UserProfileBC userProfileBC;

	/**
	 * Display user profile page
	 */
	@GetMapping("/pages/html/postLogin/Profile.jsp")
	public String viewProfile(HttpSession session, Model model) {
		String userName = (String) session.getAttribute("user");
		if (userName == null) {
			return "redirect:/pages/html/preLogin/Unauthorised.html";
		}

		UserProfileDTO profile = userProfileBC.getUserProfile(userName);
		if (profile == null) {
			model.addAttribute("error", "Profile not found");
			return "redirect:/pages/html/postLogin/SearchCriteria";
		}

		model.addAttribute("profile", profile);
		return "pages/postLogin/Profile";
	}

	/**
	 * Display profile edit page
	 */
	@GetMapping("/pages/html/postLogin/EditProfile.jsp")
	public String editProfilePage(HttpSession session, Model model) {
		String userName = (String) session.getAttribute("user");
		if (userName == null) {
			return "redirect:/pages/html/preLogin/Unauthorised.html";
		}

		UserProfileDTO profile = userProfileBC.getUserProfile(userName);
		if (profile == null) {
			model.addAttribute("error", "Profile not found");
			return "redirect:/pages/html/postLogin/Profile";
		}

		model.addAttribute("profile", profile);
		return "pages/postLogin/EditProfile";
	}

	/**
	 * Update user profile
	 */
	@PostMapping("/pages/html/postLogin/UpdateProfile.jsp")
	public String updateProfile(
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
			HttpSession session,
			Model model,
			RedirectAttributes redirectAttributes) {

		String userName = (String) session.getAttribute("user");
		if (userName == null) {
			return "redirect:/pages/html/preLogin/Unauthorised.html";
		}

		UserProfileDTO profile = new UserProfileDTO();
		profile.setUserName(userName);
		profile.setFirstName(firstName);
		profile.setMiddleName(middleName);
		profile.setLastName(lastName);
		profile.setAddress1(address1);
		profile.setAddress2(address2);
		profile.setCity(city);
		profile.setState(state);
		profile.setPincode(pinCode);
		profile.setEmail(email);
		profile.setPhone(phone);

		boolean success = userProfileBC.updateProfile(profile);
		if (success) {
			redirectAttributes.addFlashAttribute("success", "Profile updated successfully");
			return "redirect:/pages/html/postLogin/Profile";
		} else {
			model.addAttribute("error", "Failed to update profile. Please try again.");
			model.addAttribute("profile", profile);
			return "pages/postLogin/EditProfile";
		}
	}

	/**
	 * Display change password page
	 */
	@GetMapping("/pages/html/postLogin/ChangePassword.jsp")
	public String changePasswordPage(HttpSession session) {
		String userName = (String) session.getAttribute("user");
		if (userName == null) {
			return "redirect:/pages/html/preLogin/Unauthorised.html";
		}
		return "pages/postLogin/ChangePassword";
	}

	/**
	 * Process password change
	 */
	@PostMapping("/pages/html/postLogin/ChangePassword.jsp")
	public String changePassword(
			@RequestParam("CurrentPassword") String currentPassword,
			@RequestParam("NewPassword") String newPassword,
			@RequestParam("ConfirmPassword") String confirmPassword,
			HttpSession session,
			Model model) {

		String userName = (String) session.getAttribute("user");
		if (userName == null) {
			return "redirect:/pages/html/preLogin/Unauthorised.html";
		}

		PasswordChangeDTO passwordChangeDTO = new PasswordChangeDTO(
				userName, currentPassword, newPassword, confirmPassword);

		String result = userProfileBC.changePassword(passwordChangeDTO);
		
		if ("SUCCESS".equals(result)) {
			model.addAttribute("success", "Password changed successfully");
		} else {
			model.addAttribute("error", result);
		}

		return "pages/postLogin/ChangePassword";
	}
}

