package com.p.bce.shopping.cart.rpc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.p.bce.shopping.cart.rpc.pojo.BookDetailDTO;

@Repository
public class BookDetailsDAO extends AbstractDAO {

	public BookDetailDTO getBookDetail(int bookId) {
		try {
			return jdbcTemplate.queryForObject(
					"SELECT BookId, CategoryId, Title, author, publisher, edition, price, quantity, description FROM book_details WHERE BookId = ?",
					new RowMapper<BookDetailDTO>() {
						@Override
						public BookDetailDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
							return new BookDetailDTO(
									rs.getInt("BookId"),
									rs.getInt("CategoryId"),
									rs.getString("Title"),
									rs.getString("author"),
									rs.getString("publisher"),
									rs.getString("edition"),
									rs.getDouble("price"),
									rs.getInt("quantity"),
									rs.getString("description"));
						}
					},
					bookId);
		} catch (EmptyResultDataAccessException ex) {
			return new BookDetailDTO();
		} catch (Exception ex) {
			ex.printStackTrace();
			return new BookDetailDTO();
		}
	}

	public List<BookDetailDTO> getAllBooks() {
		try {
			return jdbcTemplate.query(
					"SELECT b.BookId, b.CategoryId, b.Title, b.author, b.publisher, b.edition, b.price, b.quantity, b.description, " +
					"c.CategoryName FROM book_details b LEFT JOIN category_details c ON b.CategoryId = c.CategoryId ORDER BY b.BookId DESC",
					new RowMapper<BookDetailDTO>() {
						@Override
						public BookDetailDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
							BookDetailDTO book = new BookDetailDTO(
									rs.getInt("BookId"),
									rs.getInt("CategoryId"),
									rs.getString("Title"),
									rs.getString("author"),
									rs.getString("publisher"),
									rs.getString("edition"),
									rs.getDouble("price"),
									rs.getInt("quantity"),
									rs.getString("description"));
							book.setCategoryName(rs.getString("CategoryName"));
							return book;
						}
					});
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ArrayList<BookDetailDTO>();
		}
	}

	public int createBook(BookDetailDTO book) {
		try {
			// Get next book ID
			Integer nextId = jdbcTemplate.queryForObject(
					"SELECT COALESCE(MAX(BookId), 0) + 1 FROM book_details",
					Integer.class);
			
			return jdbcTemplate.update(
					"INSERT INTO book_details (BookId, CategoryId, Title, author, publisher, edition, price, quantity, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
					nextId,
					book.getCategoryId(),
					book.getTitle(),
					book.getAuthor(),
					book.getPublisher(),
					book.getEdition(),
					book.getPrice(),
					book.getQuantity(),
					book.getDescription());
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}

	public boolean updateBook(BookDetailDTO book) {
		try {
			int rowsAffected = jdbcTemplate.update(
					"UPDATE book_details SET CategoryId = ?, Title = ?, author = ?, publisher = ?, edition = ?, price = ?, quantity = ?, description = ? WHERE BookId = ?",
					book.getCategoryId(),
					book.getTitle(),
					book.getAuthor(),
					book.getPublisher(),
					book.getEdition(),
					book.getPrice(),
					book.getQuantity(),
					book.getDescription(),
					book.getBookId());
			return rowsAffected > 0;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public boolean deleteBook(int bookId) {
		try {
			int rowsAffected = jdbcTemplate.update("DELETE FROM book_details WHERE BookId = ?", bookId);
			return rowsAffected > 0;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public List<BookDetailDTO> getBooksByCategory(int categoryId) {
		try {
			return jdbcTemplate.query(
					"SELECT BookId, CategoryId, Title, author, publisher, edition, price, quantity, description FROM book_details WHERE CategoryId = ? ORDER BY Title",
					new RowMapper<BookDetailDTO>() {
						@Override
						public BookDetailDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
							return new BookDetailDTO(
									rs.getInt("BookId"),
									rs.getInt("CategoryId"),
									rs.getString("Title"),
									rs.getString("author"),
									rs.getString("publisher"),
									rs.getString("edition"),
									rs.getDouble("price"),
									rs.getInt("quantity"),
									rs.getString("description"));
						}
					},
					categoryId);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ArrayList<BookDetailDTO>();
		}
	}

}
