package com.p.bce.shopping.cart.rpc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.p.bce.shopping.cart.rpc.pojo.BookDetailDTO;

public class BookDetailsDAO extends AbstractDAO {

	public BookDetailDTO getBookDetail(int bookId) {
		BookDetailDTO result = new BookDetailDTO();
		try {
			ResultSet rs = null;
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement("select title, price  from book_details");

			rs = ps.executeQuery();
			if (rs.next()) {
				BookDetailDTO dtoFromDb = new BookDetailDTO(rs.getInt("BookId"),
						rs.getInt("CategoryId"), rs.getString("Title"), rs.getString("author"),
						rs.getString("publisher"), rs.getString("edition"), rs.getDouble("price"),
						rs.getInt("quantity"), rs.getString("description"));
				result = dtoFromDb;
			}
			closeConnection(con);
		} catch (Exception ex) {
			result = new BookDetailDTO();
			ex.printStackTrace();
		}
		return result;
	}

}
