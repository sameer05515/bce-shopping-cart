package com.p.bce.shopping.cart.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.p.bce.shopping.cart.rpc.bc.BookDetailsBC;
import com.p.bce.shopping.cart.rpc.bc.CategoryDetailsBC;
import com.p.bce.shopping.cart.rpc.pojo.BookDetailDTO;
import com.p.bce.shopping.cart.rpc.pojo.CategoryDetailsDTO;

@Controller
public class AdminController {

	@Autowired
	private BookDetailsBC bookDetailsBC;

	@Autowired
	private CategoryDetailsBC categoryDetailsBC;

	// ========== Book Management ==========

	@GetMapping("/admin/books")
	public String listBooks(HttpSession session, Model model) {
		if (!isAdmin(session)) {
			return "redirect:/pages/html/preLogin/Unauthorised.html";
		}

		model.addAttribute("books", bookDetailsBC.getAllBooks());
		model.addAttribute("categories", categoryDetailsBC.getAllCategoryDetails());
		return "admin/BookList";
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

		model.addAttribute("totalBooks", bookDetailsBC.getAllBooks().size());
		model.addAttribute("totalCategories", categoryDetailsBC.getAllCategoryDetails().size());
		return "admin/Dashboard";
	}

	// ========== Helper Methods ==========

	private boolean isAdmin(HttpSession session) {
		// For now, any logged-in user can access admin features
		// TODO: Implement proper role-based access control
		String userName = (String) session.getAttribute("user");
		return userName != null && !userName.isEmpty();
	}

}

