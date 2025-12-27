package com.p.bce.shopping.cart.rpc.bc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.p.bce.shopping.cart.rpc.dao.BookDetailsDAO;
import com.p.bce.shopping.cart.rpc.pojo.BookDetailDTO;

@Service
public class BookDetailsBC {
	
	@Autowired
	private BookDetailsDAO objBookDetailsDAO;

	public BookDetailDTO getBookDetail(int bookId) {
		return objBookDetailsDAO.getBookDetail(bookId);
	}

	public List<BookDetailDTO> getAllBooks() {
		return objBookDetailsDAO.getAllBooks();
	}

	public boolean createBook(BookDetailDTO book) {
		// Validate required fields
		if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
			return false;
		}
		if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
			return false;
		}
		if (book.getCategoryId() <= 0) {
			return false;
		}
		if (book.getPrice() < 0) {
			return false;
		}
		if (book.getQuantity() < 0) {
			return false;
		}

		return objBookDetailsDAO.createBook(book) > 0;
	}

	public boolean updateBook(BookDetailDTO book) {
		// Validate required fields
		if (book.getBookId() <= 0) {
			return false;
		}
		if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
			return false;
		}
		if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
			return false;
		}
		if (book.getCategoryId() <= 0) {
			return false;
		}
		if (book.getPrice() < 0) {
			return false;
		}
		if (book.getQuantity() < 0) {
			return false;
		}

		return objBookDetailsDAO.updateBook(book);
	}

	public boolean deleteBook(int bookId) {
		if (bookId <= 0) {
			return false;
		}
		return objBookDetailsDAO.deleteBook(bookId);
	}

	public List<BookDetailDTO> getBooksByCategory(int categoryId) {
		return objBookDetailsDAO.getBooksByCategory(categoryId);
	}

}
