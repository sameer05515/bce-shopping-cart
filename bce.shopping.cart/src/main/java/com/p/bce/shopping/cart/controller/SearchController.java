package com.p.bce.shopping.cart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.p.bce.shopping.cart.rpc.bc.CategoryDetailsBC;
import com.p.bce.shopping.cart.rpc.bc.WishlistBC;
import com.p.bce.shopping.cart.rpc.pojo.CategoryDetailsDTO;
import com.p.bce.shopping.cart.rpc.pojo.SearchedBookCategories;

@Controller
public class SearchController {

	@Autowired
	private CategoryDetailsBC categoryDetailsBC;
	
	@Autowired
	private WishlistBC wishlistBC;

	@GetMapping({"/pages/html/postLogin/SearchCriteria.jsp", "/pages/html/postLogin/SearchCriteria"})
	public String searchCriteria(HttpSession session, Model model) {
		System.out.println("SearchController.searchCriteria() called");
		String user = (String) session.getAttribute("user");
		if (user == null) {
			System.out.println("User not logged in, redirecting to Unauthorised");
			return "redirect:/pages/html/preLogin/Unauthorised.html";
		}
		
		System.out.println("User logged in: " + user);
		try {
			System.out.println("Calling categoryDetailsBC.getAllCategoryDetails()");
			List<CategoryDetailsDTO> listCategDet = categoryDetailsBC.getAllCategoryDetails();
			System.out.println("Categories loaded: " + (listCategDet != null ? listCategDet.size() : "null"));
			
			// Ensure categories list is never null
			if (listCategDet == null) {
				listCategDet = new ArrayList<>();
			}
			
			model.addAttribute("categories", listCategDet);
			System.out.println("Returning view: pages/postLogin/SearchCriteria");
			return "pages/postLogin/SearchCriteria"; // Thymeleaf template
		} catch (Exception e) {
			System.err.println("ERROR in searchCriteria: " + e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
			// On error, still provide empty categories list so template can render
			model.addAttribute("categories", new ArrayList<CategoryDetailsDTO>());
			model.addAttribute("error", "Error loading categories: " + e.getMessage());
			// Even on error, return the template so we can see the error message
			return "pages/postLogin/SearchCriteria"; // Thymeleaf template
		}
	}

	@GetMapping({"/pages/html/postLogin/Search.jsp", "/pages/html/postLogin/Search"})
	public String viewSearchResults(HttpSession session, Model model) {
		String user = (String) session.getAttribute("user");
		if (user == null) {
			return "redirect:/pages/html/preLogin/Unauthorised.html";
		}
		
		// If there are search results in session, display them
		// Otherwise redirect to search criteria
		return "redirect:/pages/html/postLogin/SearchCriteria";
	}

	@PostMapping({"/pages/html/postLogin/Search.jsp", "/pages/html/postLogin/Search"})
	public String search(
			@RequestParam(value = "R1", required = false) String searchType,
			@RequestParam(value = "R2", required = false, defaultValue = "N") String searchMode,
			@RequestParam(value = "BookCategory", required = false) String bookCategory,
			@RequestParam(value = "BookTitle", required = false) String bookTitle,
			@RequestParam(value = "BookAuthor", required = false) String bookAuthor,
			@RequestParam(value = "BookPublisher", required = false) String bookPublisher,
			HttpSession session,
			Model model) {
		
		String user = (String) session.getAttribute("user");
		if (user == null) {
			return "redirect:/pages/html/preLogin/Unauthorised.html";
		}
		
		if (searchType == null) {
			searchType = "Category";
		}
		
		String str_searchvar = null;
		int chk_ctr = 0;
		String str_colvar = null;
		String tab_var = "";
		
		if ("Title".equals(searchType)) {
			str_searchvar = bookTitle;
			str_colvar = "Title";
		} else if ("Category".equals(searchType)) {
			chk_ctr = 1;
			str_searchvar = bookCategory;
			str_colvar = "CategoryName";
		} else if ("Publisher".equals(searchType)) {
			str_searchvar = bookPublisher;
			str_colvar = "Publisher";
		} else if ("Author".equals(searchType)) {
			str_searchvar = bookAuthor;
			str_colvar = "Author";
		}
		
		if ("A".equals(searchMode)) {
			tab_var = "temp_detail";
		} else {
			tab_var = "book_details";
		}
		
		// Ensure table name is lowercase for MySQL
		tab_var = tab_var.toLowerCase();
		
		// Delete old temp details before new search
		if ("N".equals(searchMode)) {
			categoryDetailsBC.deleteOldTempDetails();
		}
		
		List<SearchedBookCategories> listSearchedBookCategories = 
				categoryDetailsBC.getSearchedBookCategories(str_searchvar, str_colvar, chk_ctr, tab_var);
		
		// Insert into temp details
		int counter = 0;
		for (SearchedBookCategories s : listSearchedBookCategories) {
			categoryDetailsBC.insertIntoTempDetails(s);
			counter++;
		}
		
		session.setAttribute("ctr_val", String.valueOf(counter));
		
		List<CategoryDetailsDTO> listCategDet = categoryDetailsBC.getAllCategoryDetails();
		
		model.addAttribute("searchResults", listSearchedBookCategories);
		model.addAttribute("categories", listCategDet);
		model.addAttribute("counter", counter);
		model.addAttribute("userName", user); // Pass userName for wishlist checks
		
		return "pages/postLogin/Search"; // Thymeleaf template
	}
}

