package com.p.bce.shopping.cart.rpc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.p.bce.shopping.cart.rpc.pojo.CategoryDetailsDTO;
import com.p.bce.shopping.cart.rpc.pojo.SearchedBookCategories;

public class CategoryDetailsDAO extends AbstractDAO {

	public List<CategoryDetailsDTO> getAllCategoryDetails() {
		List<CategoryDetailsDTO> result = new ArrayList<CategoryDetailsDTO>();
		try {
			ResultSet rs = null;
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement("select CategoryId, CategoryName  from category_details");

			rs = ps.executeQuery();
			while (rs.next()) {
				CategoryDetailsDTO dtoFromDb = new CategoryDetailsDTO(rs.getInt("CategoryId"),
						rs.getString("CategoryName"));
				result.add(dtoFromDb);
			}
			closeConnection(con);
		} catch (Exception ex) {
			result = new ArrayList<CategoryDetailsDTO>();
			ex.printStackTrace();
		}
		return result;
	}

	public List<SearchedBookCategories> getSearchedBookCategories(String str_searchvar, String str_colvar, int chk_ctr,
			String tab_var) {
		List<SearchedBookCategories> result = new ArrayList<SearchedBookCategories>();
		String query = "";
		// SELECT `BookId`, `CategoryId`, `Title`, `Author`, `Publisher`, `Edition`,
		// `Price`, `Quantity`, `Description` FROM `book_details` WHERE 1
		if (chk_ctr == 0) {
			query = "SELECT a.* b.categoryName from " + tab_var + " CategoryDetails b where UPPER(a." + str_colvar
					+ ") = UPPER('" + str_searchvar + "') and a.categoryid=b.categoryid";
		} else {
			query = "SELECT a.* b.categoryName from " + tab_var + " CategoryDetails b where UPPER(b." + str_colvar
					+ ") = UPPER('" + str_searchvar + "') and a.categoryid=b.categoryid";
		}

		try {
			ResultSet rs = null;
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(query);

			rs = ps.executeQuery();
			while (rs.next()) {
				SearchedBookCategories dtoFromDb = new SearchedBookCategories(rs.getInt("BookId"),
						rs.getInt("CategoryId"), rs.getString("Title"), rs.getString("author"),
						rs.getString("publisher"), rs.getString("edition"), rs.getDouble("price"),
						rs.getInt("quantity"), rs.getString("description"), rs.getString("categoryName"));
				result.add(dtoFromDb);
			}
			closeConnection(con);
		} catch (Exception ex) {
			result = new ArrayList<SearchedBookCategories>();
			ex.printStackTrace();
		}

		return result;
	}

	public boolean deleteOldTempDetails() {
		boolean result = false;
		try {
			int rs_del = -1;
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement("Delete from temp_detail");

			rs_del = ps.executeUpdate();
			result = rs_del >= 0;
			closeConnection(con);
		} catch (Exception ex) {
			result = false;
			ex.printStackTrace();
		}
		return result;
	}

	public int insertIntoTempDetails(SearchedBookCategories objSearchedBookCategories) {
		int result = 0;
		try {
			int rs_del = -1;
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement("INSERT INTO temp_detail(BookId, CategoryId, Title, Author, "
					+ "Publisher, Edition, Price, Quantity, Description) VALUES (?,?,?,?,?,?,?,?,?)");
			int j=1;
			ps.setInt(j++, objSearchedBookCategories.getBookId());
			ps.setInt(j++, objSearchedBookCategories.getCategoryId());
			ps.setString(j++, objSearchedBookCategories.getTitle());
			ps.setString(j++, objSearchedBookCategories.getAuthor());
			ps.setString(j++, objSearchedBookCategories.getPublisher());
			ps.setString(j++, objSearchedBookCategories.getEdition());
			ps.setDouble(j++, objSearchedBookCategories.getPrice());
			ps.setInt(j++, objSearchedBookCategories.getQuantity());
			ps.setString(j++, objSearchedBookCategories.getDescription());

			rs_del = ps.executeUpdate();
			result = rs_del;
			closeConnection(con);
		} catch (Exception ex) {
			result = 0;
			ex.printStackTrace();
		}
		return result;
	}

}
