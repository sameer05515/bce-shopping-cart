package com.p.bce.shopping.cart.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

import com.p.bce.shopping.cart.rpc.bc.BookDetailsBC;
import com.p.bce.shopping.cart.rpc.bc.CategoryDetailsBC;
import com.p.bce.shopping.cart.rpc.bc.OrderBC;
import com.p.bce.shopping.cart.rpc.bc.UserProfileBC;
import com.p.bce.shopping.cart.rpc.pojo.BookDetailDTO;
import com.p.bce.shopping.cart.rpc.pojo.CategoryDetailsDTO;
import com.p.bce.shopping.cart.rpc.pojo.OrderDTO;
import com.p.bce.shopping.cart.rpc.pojo.UserProfileDTO;

@Controller
public class AdminController {

	@Autowired
	private BookDetailsBC bookDetailsBC;

	@Autowired
	private CategoryDetailsBC categoryDetailsBC;
	
	@Autowired
	private OrderBC orderBC;
	
	@Autowired
	private UserProfileBC userProfileBC;

	// ========== Book Management ==========

	@GetMapping("/admin/books")
	public String listBooks(HttpSession session, Model model) {
		if (!isAdmin(session)) {
			return "redirect:/pages/html/preLogin/Unauthorised.html";
		}

		try {
			java.util.List<BookDetailDTO> books = bookDetailsBC.getAllBooks();
			if (books == null) {
				books = new java.util.ArrayList<>();
			}
			model.addAttribute("books", books);
			
			java.util.List<CategoryDetailsDTO> categories = categoryDetailsBC.getAllCategoryDetails();
			if (categories == null) {
				categories = new java.util.ArrayList<>();
			}
			model.addAttribute("categories", categories);
			
			return "admin/BookList";
		} catch (Exception e) {
			System.err.println("ERROR in listBooks: " + e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
			model.addAttribute("error", "Error loading books: " + e.getMessage());
			model.addAttribute("books", new java.util.ArrayList<BookDetailDTO>());
			model.addAttribute("categories", new java.util.ArrayList<CategoryDetailsDTO>());
			return "admin/BookList";
		}
	}

	@GetMapping("/admin/books/add")
	public String showAddBookForm(HttpSession session, Model model) {
		if (!isAdmin(session)) {
			return "redirect:/pages/html/preLogin/Unauthorised.html";
		}

		model.addAttribute("categories", categoryDetailsBC.getAllCategoryDetails());
		model.addAttribute("book", new BookDetailDTO());
		return "admin/BookForm";
	}

	@GetMapping("/admin/books/edit")
	public String showEditBookForm(@RequestParam("id") int bookId, HttpSession session, Model model) {
		if (!isAdmin(session)) {
			return "redirect:/pages/html/preLogin/Unauthorised.html";
		}

		BookDetailDTO book = bookDetailsBC.getBookDetail(bookId);
		if (book == null || book.getBookId() == 0) {
			return "redirect:/admin/books?error=Book not found";
		}

		model.addAttribute("book", book);
		model.addAttribute("categories", categoryDetailsBC.getAllCategoryDetails());
		return "admin/BookForm";
	}

	@PostMapping("/admin/books/save")
	public String saveBook(
			@RequestParam(value = "bookId", required = false) Integer bookId,
			@RequestParam("categoryId") int categoryId,
			@RequestParam("title") String title,
			@RequestParam("author") String author,
			@RequestParam("publisher") String publisher,
			@RequestParam("edition") String edition,
			@RequestParam("price") double price,
			@RequestParam("quantity") int quantity,
			@RequestParam(value = "description", required = false) String description,
			HttpSession session,
			RedirectAttributes redirectAttributes) {
		
		if (!isAdmin(session)) {
			return "redirect:/pages/html/preLogin/Unauthorised.html";
		}

		BookDetailDTO book = new BookDetailDTO();
		if (bookId != null && bookId > 0) {
			book.setBookId(bookId);
		}
		book.setCategoryId(categoryId);
		book.setTitle(title);
		book.setAuthor(author);
		book.setPublisher(publisher);
		book.setEdition(edition);
		book.setPrice(price);
		book.setQuantity(quantity);
		book.setDescription(description != null ? description : "");

		boolean success;
		if (bookId != null && bookId > 0) {
			success = bookDetailsBC.updateBook(book);
			if (success) {
				redirectAttributes.addFlashAttribute("message", "Book updated successfully!");
			} else {
				redirectAttributes.addFlashAttribute("error", "Failed to update book. Please check all fields.");
			}
		} else {
			success = bookDetailsBC.createBook(book);
			if (success) {
				redirectAttributes.addFlashAttribute("message", "Book created successfully!");
			} else {
				redirectAttributes.addFlashAttribute("error", "Failed to create book. Please check all fields.");
			}
		}

		return "redirect:/admin/books";
	}

	@PostMapping("/admin/books/delete")
	public String deleteBook(@RequestParam("id") int bookId, HttpSession session, RedirectAttributes redirectAttributes) {
		if (!isAdmin(session)) {
			return "redirect:/pages/html/preLogin/Unauthorised.html";
		}

		boolean success = bookDetailsBC.deleteBook(bookId);
		if (success) {
			redirectAttributes.addFlashAttribute("message", "Book deleted successfully!");
		} else {
			redirectAttributes.addFlashAttribute("error", "Failed to delete book.");
		}

		return "redirect:/admin/books";
	}

	// ========== Category Management ==========

	@GetMapping("/admin/categories")
	public String listCategories(HttpSession session, Model model) {
		if (!isAdmin(session)) {
			return "redirect:/pages/html/preLogin/Unauthorised.html";
		}

		model.addAttribute("categories", categoryDetailsBC.getAllCategoryDetails());
		return "admin/CategoryList";
	}

	@GetMapping("/admin/categories/add")
	public String showAddCategoryForm(HttpSession session, Model model) {
		if (!isAdmin(session)) {
			return "redirect:/pages/html/preLogin/Unauthorised.html";
		}

		model.addAttribute("category", new CategoryDetailsDTO());
		return "admin/CategoryForm";
	}

	@GetMapping("/admin/categories/edit")
	public String showEditCategoryForm(@RequestParam("id") int categoryId, HttpSession session, Model model) {
		if (!isAdmin(session)) {
			return "redirect:/pages/html/preLogin/Unauthorised.html";
		}

		CategoryDetailsDTO category = categoryDetailsBC.getCategoryById(categoryId);
		if (category == null) {
			return "redirect:/admin/categories?error=Category not found";
		}

		model.addAttribute("category", category);
		return "admin/CategoryForm";
	}

	@PostMapping("/admin/categories/save")
	public String saveCategory(
			@RequestParam(value = "categoryId", required = false) Integer categoryId,
			@RequestParam("categoryName") String categoryName,
			HttpSession session,
			RedirectAttributes redirectAttributes) {
		
		if (!isAdmin(session)) {
			return "redirect:/pages/html/preLogin/Unauthorised.html";
		}

		CategoryDetailsDTO category = new CategoryDetailsDTO();
		if (categoryId != null && categoryId > 0) {
			category.setCategoryId(categoryId);
		}
		category.setCategoryName(categoryName);

		boolean success;
		if (categoryId != null && categoryId > 0) {
			success = categoryDetailsBC.updateCategory(category);
			if (success) {
				redirectAttributes.addFlashAttribute("message", "Category updated successfully!");
			} else {
				redirectAttributes.addFlashAttribute("error", "Failed to update category. Category name may already exist.");
			}
		} else {
			success = categoryDetailsBC.createCategory(category);
			if (success) {
				redirectAttributes.addFlashAttribute("message", "Category created successfully!");
			} else {
				redirectAttributes.addFlashAttribute("error", "Failed to create category. Category name may already exist.");
			}
		}

		return "redirect:/admin/categories";
	}

	@PostMapping("/admin/categories/delete")
	public String deleteCategory(@RequestParam("id") int categoryId, HttpSession session, RedirectAttributes redirectAttributes) {
		if (!isAdmin(session)) {
			return "redirect:/pages/html/preLogin/Unauthorised.html";
		}

		boolean success = categoryDetailsBC.deleteCategory(categoryId);
		if (success) {
			redirectAttributes.addFlashAttribute("message", "Category deleted successfully!");
		} else {
			redirectAttributes.addFlashAttribute("error", "Failed to delete category. It may be in use by books.");
		}

		return "redirect:/admin/categories";
	}

	// ========== Admin Dashboard ==========

	@GetMapping("/admin")
	public String adminDashboard(HttpSession session, Model model) {
		if (!isAdmin(session)) {
			return "redirect:/pages/html/preLogin/Unauthorised.html";
		}

		try {
			// Basic counts
			List<BookDetailDTO> allBooks = bookDetailsBC.getAllBooks();
			model.addAttribute("totalBooks", allBooks != null ? allBooks.size() : 0);
			model.addAttribute("totalCategories", categoryDetailsBC.getAllCategoryDetails().size());
			model.addAttribute("totalUsers", userProfileBC.getUserCount());
			model.addAttribute("totalOrders", orderBC.getTotalOrderCount());
			
			// Sales analytics
			BigDecimal totalRevenue = orderBC.getTotalRevenue();
			model.addAttribute("totalRevenue", totalRevenue != null ? totalRevenue : BigDecimal.ZERO);
			BigDecimal avgOrderValue = orderBC.getAverageOrderValue();
			model.addAttribute("averageOrderValue", avgOrderValue != null ? avgOrderValue : BigDecimal.ZERO);
			
			// Inventory analytics
			model.addAttribute("totalInventoryValue", bookDetailsBC.getTotalInventoryValue());
			model.addAttribute("totalStockQuantity", bookDetailsBC.getTotalStockQuantity());
			model.addAttribute("lowStockCount", bookDetailsBC.getLowStockCount(10)); // Threshold: 10
			model.addAttribute("lowStockBooks", bookDetailsBC.getLowStockBooks(10));
			
			// Recent orders
			model.addAttribute("recentOrders", orderBC.getRecentOrders(5));
			
		} catch (Exception e) {
			System.err.println("ERROR in adminDashboard: " + e.getMessage());
			e.printStackTrace();
			// Set defaults on error
			model.addAttribute("totalBooks", 0);
			model.addAttribute("totalCategories", 0);
			model.addAttribute("totalUsers", 0);
			model.addAttribute("totalOrders", 0);
			model.addAttribute("totalRevenue", BigDecimal.ZERO);
			model.addAttribute("averageOrderValue", BigDecimal.ZERO);
			model.addAttribute("totalInventoryValue", 0.0);
			model.addAttribute("totalStockQuantity", 0);
			model.addAttribute("lowStockCount", 0);
		}
		
		return "admin/Dashboard";
	}
	
	// ========== User Management ==========
	
	@GetMapping("/admin/users")
	public String listUsers(
			@RequestParam(value = "search", required = false) String searchTerm,
			HttpSession session,
			Model model) {
		if (!isAdmin(session)) {
			return "redirect:/pages/html/preLogin/Unauthorised.html";
		}
		
		try {
			List<UserProfileDTO> users;
			if (searchTerm != null && !searchTerm.trim().isEmpty()) {
				users = userProfileBC.searchUsers(searchTerm);
				model.addAttribute("searchTerm", searchTerm);
			} else {
				users = userProfileBC.getAllUsers();
			}
			
			if (users == null) {
				users = new java.util.ArrayList<>();
			}
			model.addAttribute("users", users);
			return "admin/UserList";
		} catch (Exception e) {
			System.err.println("ERROR in listUsers: " + e.getMessage());
			e.printStackTrace();
			model.addAttribute("error", "Error loading users: " + e.getMessage());
			model.addAttribute("users", new java.util.ArrayList<UserProfileDTO>());
			return "admin/UserList";
		}
	}
	
	@GetMapping("/admin/users/details")
	public String userDetails(
			@RequestParam("username") String username,
			HttpSession session,
			Model model) {
		if (!isAdmin(session)) {
			return "redirect:/pages/html/preLogin/Unauthorised.html";
		}
		
		try {
			UserProfileDTO user = userProfileBC.getUserProfile(username);
			if (user == null) {
				return "redirect:/admin/users?error=User not found";
			}
			
			// Get user's orders
			List<OrderDTO> userOrders = orderBC.getOrdersByUser(username);
			model.addAttribute("user", user);
			model.addAttribute("userOrders", userOrders != null ? userOrders : new java.util.ArrayList<>());
			return "admin/UserDetails";
		} catch (Exception e) {
			System.err.println("ERROR in userDetails: " + e.getMessage());
			e.printStackTrace();
			return "redirect:/admin/users?error=Error loading user details";
		}
	}
	
	// ========== Order Management Enhancements ==========
	
	@GetMapping("/admin/orders/search")
	public String searchOrders(
			@RequestParam(value = "q", required = false) String searchTerm,
			@RequestParam(value = "status", required = false) String status,
			HttpSession session,
			Model model) {
		if (!isAdmin(session)) {
			return "redirect:/pages/html/preLogin/Unauthorised.html";
		}
		
		try {
			List<OrderDTO> orders;
			if (searchTerm != null && !searchTerm.trim().isEmpty()) {
				orders = orderBC.searchOrders(searchTerm);
				model.addAttribute("searchTerm", searchTerm);
			} else {
				orders = orderBC.getAllOrders();
			}
			
			// Filter by status if provided
			if (status != null && !status.trim().isEmpty() && !status.equals("ALL")) {
				List<OrderDTO> filtered = new java.util.ArrayList<>();
				for (OrderDTO order : orders) {
					if (order.getStatus() != null && order.getStatus().equals(status)) {
						filtered.add(order);
					}
				}
				orders = filtered;
				model.addAttribute("filterStatus", status);
			}
			
			if (orders == null) {
				orders = new java.util.ArrayList<>();
			}
			model.addAttribute("orders", orders);
			return "admin/OrderList";
		} catch (Exception e) {
			System.err.println("ERROR in searchOrders: " + e.getMessage());
			e.printStackTrace();
			model.addAttribute("error", "Error searching orders: " + e.getMessage());
			model.addAttribute("orders", new java.util.ArrayList<OrderDTO>());
			return "admin/OrderList";
		}
	}

	// ========== Helper Methods ==========

	private boolean isAdmin(HttpSession session) {
		// For now, any logged-in user can access admin features
		// TODO: Implement proper role-based access control
		String userName = (String) session.getAttribute("user");
		return userName != null && !userName.isEmpty();
	}

}

