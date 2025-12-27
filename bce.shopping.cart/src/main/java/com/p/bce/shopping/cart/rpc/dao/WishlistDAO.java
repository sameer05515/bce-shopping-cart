package com.p.bce.shopping.cart.rpc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.p.bce.shopping.cart.rpc.pojo.WishlistDTO;

@Repository
public class WishlistDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean addToWishlist(String userId, int bookId) {
        try {
            // Check if already in wishlist
            if (isInWishlist(userId, bookId)) {
                return false; // Already exists
            }

            String sql = "INSERT INTO wishlist (userid, bookid, added_date) VALUES (?, ?, ?)";
            int rows = jdbcTemplate.update(sql, userId, bookId, new Date());
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeFromWishlist(String userId, int bookId) {
        try {
            String sql = "DELETE FROM wishlist WHERE userid = ? AND bookid = ?";
            int rows = jdbcTemplate.update(sql, userId, bookId);
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeFromWishlistById(int wishlistId, String userId) {
        try {
            String sql = "DELETE FROM wishlist WHERE wishlist_id = ? AND userid = ?";
            int rows = jdbcTemplate.update(sql, wishlistId, userId);
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isInWishlist(String userId, int bookId) {
        try {
            String sql = "SELECT COUNT(*) FROM wishlist WHERE userid = ? AND bookid = ?";
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId, bookId);
            return count != null && count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<WishlistDTO> getWishlistByUser(String userId) {
        try {
            String sql = "SELECT w.wishlist_id, w.userid, w.bookid, w.added_date, " +
                        "b.title, b.author, b.publisher, b.price, b.quantity " +
                        "FROM wishlist w " +
                        "LEFT JOIN book_details b ON w.bookid = b.bookid " +
                        "WHERE w.userid = ? " +
                        "ORDER BY w.added_date DESC";
            
            return jdbcTemplate.query(sql, new RowMapper<WishlistDTO>() {
                @Override
                public WishlistDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                    WishlistDTO dto = new WishlistDTO();
                    dto.setWishlistId(rs.getInt("wishlist_id"));
                    dto.setUserId(rs.getString("userid"));
                    dto.setBookId(rs.getInt("bookid"));
                    dto.setAddedDate(rs.getTimestamp("added_date"));
                    dto.setBookTitle(rs.getString("title"));
                    dto.setBookAuthor(rs.getString("author"));
                    dto.setBookPublisher(rs.getString("publisher"));
                    dto.setBookPrice(rs.getDouble("price"));
                    dto.setAvailableQuantity(rs.getInt("quantity"));
                    return dto;
                }
            }, userId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public int getWishlistCount(String userId) {
        try {
            String sql = "SELECT COUNT(*) FROM wishlist WHERE userid = ?";
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId);
            return count != null ? count : 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}

