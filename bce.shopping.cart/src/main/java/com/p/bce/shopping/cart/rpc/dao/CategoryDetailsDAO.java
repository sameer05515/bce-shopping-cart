package com.p.bce.shopping.cart.rpc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.p.bce.shopping.cart.rpc.pojo.CategoryDetailsDTO;
import com.p.bce.shopping.cart.rpc.pojo.SearchedBookCategories;

@Repository
public class CategoryDetailsDAO extends AbstractDAO {

	public List<CategoryDetailsDTO> getAllCategoryDetails() {
		try {
			return jdbcTemplate.query(
					"SELECT CategoryId, CategoryName FROM category_details",
					new RowMapper<CategoryDetailsDTO>() {
						@Override
						public CategoryDetailsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
							return new CategoryDetailsDTO(rs.getInt("CategoryId"), rs.getString("CategoryName"));
						}
					});
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ArrayList<CategoryDetailsDTO>();
		}
	}

	public List<SearchedBookCategories> getSearchedBookCategories(String str_searchvar, String str_colvar, int chk_ctr,
			String tab_var) {
		String query = "";
		// SELECT `BookId`, `CategoryId`, `Title`, `Author`, `Publisher`, `Edition`,
		// `Price`, `Quantity`, `Description` FROM `book_details` WHERE 1
		if (chk_ctr == 0) {
			query = "SELECT a.*, b.categoryName FROM " + tab_var + " a, category_details b WHERE UPPER(a." + str_colvar
					+ ") = UPPER(?) AND a.categoryid = b.categoryid";
		} else {
			query = "SELECT a.*, b.categoryName FROM " + tab_var + " a, category_details b WHERE UPPER(b." + str_colvar
					+ ") = UPPER(?) AND a.categoryid = b.categoryid";
		}

		try {
			return jdbcTemplate.query(
					query,
					new RowMapper<SearchedBookCategories>() {
						@Override
						public SearchedBookCategories mapRow(ResultSet rs, int rowNum) throws SQLException {
							return new SearchedBookCategories(
									rs.getInt("BookId"),
									rs.getInt("CategoryId"),
									rs.getString("Title"),
									rs.getString("author"),
									rs.getString("publisher"),
									rs.getString("edition"),
									rs.getDouble("price"),
									rs.getInt("quantity"),
									rs.getString("description"),
									rs.getString("categoryName"));
						}
					},
					str_searchvar);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ArrayList<SearchedBookCategories>();
		}
	}

	public boolean deleteOldTempDetails() {
		try {
			int rowsAffected = jdbcTemplate.update("DELETE FROM temp_detail");
			return rowsAffected >= 0;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public int insertIntoTempDetails(SearchedBookCategories objSearchedBookCategories) {
		try {
			return jdbcTemplate.update(
					"INSERT INTO temp_detail(BookId, CategoryId, Title, Author, "
							+ "Publisher, Edition, Price, Quantity, Description) VALUES (?,?,?,?,?,?,?,?,?)",
					objSearchedBookCategories.getBookId(),
					objSearchedBookCategories.getCategoryId(),
					objSearchedBookCategories.getTitle(),
					objSearchedBookCategories.getAuthor(),
					objSearchedBookCategories.getPublisher(),
					objSearchedBookCategories.getEdition(),
					objSearchedBookCategories.getPrice(),
					objSearchedBookCategories.getQuantity(),
					objSearchedBookCategories.getDescription());
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}

	public CategoryDetailsDTO getCategoryById(int categoryId) {
		try {
			return jdbcTemplate.queryForObject(
					"SELECT CategoryId, CategoryName FROM category_details WHERE CategoryId = ?",
					new RowMapper<CategoryDetailsDTO>() {
						@Override
						public CategoryDetailsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
							return new CategoryDetailsDTO(rs.getInt("CategoryId"), rs.getString("CategoryName"));
						}
					},
					categoryId);
		} catch (EmptyResultDataAccessException ex) {
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public boolean categoryExists(String categoryName) {
		try {
			Integer count = jdbcTemplate.queryForObject(
					"SELECT COUNT(*) FROM category_details WHERE UPPER(CategoryName) = UPPER(?)",
					Integer.class,
					categoryName);
			return count != null && count > 0;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public int createCategory(CategoryDetailsDTO category) {
		try {
			// Get next category ID
			Integer nextId = jdbcTemplate.queryForObject(
					"SELECT COALESCE(MAX(CategoryId), 0) + 1 FROM category_details",
					Integer.class);
			
			return jdbcTemplate.update(
					"INSERT INTO category_details (CategoryId, CategoryName) VALUES (?, ?)",
					nextId,
					category.getCategoryName());
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}

	public boolean updateCategory(CategoryDetailsDTO category) {
		try {
			int rowsAffected = jdbcTemplate.update(
					"UPDATE category_details SET CategoryName = ? WHERE CategoryId = ?",
					category.getCategoryName(),
					category.getCategoryId());
			return rowsAffected > 0;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public boolean deleteCategory(int categoryId) {
		try {
			// Check if category is used by any books
			Integer bookCount = jdbcTemplate.queryForObject(
					"SELECT COUNT(*) FROM book_details WHERE CategoryId = ?",
					Integer.class,
					categoryId);
			
			if (bookCount != null && bookCount > 0) {
				return false; // Cannot delete category that has books
			}
			
			int rowsAffected = jdbcTemplate.update("DELETE FROM category_details WHERE CategoryId = ?", categoryId);
			return rowsAffected > 0;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

}
