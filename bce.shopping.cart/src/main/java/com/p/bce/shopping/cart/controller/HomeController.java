package com.p.bce.shopping.cart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String index() {
		// Redirect to index.html which will be served as static resource
		return "redirect:/index.html";
	}
}

