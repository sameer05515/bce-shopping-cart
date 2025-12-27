package com.p.bce.shopping.cart.rpc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

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

}
