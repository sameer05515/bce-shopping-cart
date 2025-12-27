package com.p.bce.shopping.cart.rpc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.p.bce.shopping.cart.rpc.pojo.CartItemDTO;

@Repository
public class CartDAO extends AbstractDAO {

    /**
     * Save cart item to database
     */
    public boolean saveCartItem(String userId, int bookId, int quantity) {
        try {
            // Check if item already exists
            Integer existingId = null;
            try {
                existingId = jdbcTemplate.queryForObject(
                        "SELECT cart_id FROM shopping_cart WHERE user_id = ? AND book_id = ?",
                        Integer.class,
                        userId, bookId);
            } catch (EmptyResultDataAccessException e) {
                // Item doesn't exist, will insert
            }

            if (existingId != null) {
                // Update existing item
                int rowsAffected = jdbcTemplate.update(
                        "UPDATE shopping_cart SET quantity = ?, updated_at = CURRENT_TIMESTAMP WHERE cart_id = ?",
                        quantity, existingId);
                return rowsAffected > 0;
            } else {
                // Insert new item
                int rowsAffected = jdbcTemplate.update(
                        "INSERT INTO shopping_cart (user_id, book_id, quantity) VALUES (?, ?, ?)",
                        userId, bookId, quantity);
                return rowsAffected > 0;
            }
        } catch (Exception ex) {
            System.err.println("Error saving cart item: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Get all cart items for a user
     */
    public List<CartItemDTO> getCartItems(String userId) {
        try {
            return jdbcTemplate.query(
                    "SELECT sc.book_id, sc.quantity, b.BookId, b.Title, b.author, b.publisher, b.edition, b.price, b.quantity as available_quantity, b.description " +
                    "FROM shopping_cart sc " +
                    "LEFT JOIN book_details b ON sc.book_id = b.BookId " +
                    "WHERE sc.user_id = ? " +
                    "ORDER BY sc.updated_at DESC",
                    new RowMapper<CartItemDTO>() {
                        @Override
                        public CartItemDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                            CartItemDTO item = new CartItemDTO();
                            item.setBookId(rs.getInt("book_id"));
                            item.setQuantity(rs.getInt("quantity"));
                            item.setTitle(rs.getString("Title") != null ? rs.getString("Title") : "Unknown Book");
                            item.setAuthor(rs.getString("author") != null ? rs.getString("author") : "");
                            item.setPublisher(rs.getString("publisher") != null ? rs.getString("publisher") : "");
                            item.setEdition(rs.getString("edition") != null ? rs.getString("edition") : "");
                            item.setPrice(rs.getBigDecimal("price") != null ? rs.getBigDecimal("price") : java.math.BigDecimal.ZERO);
                            item.setAvailableQuantity(rs.getInt("available_quantity"));
                            item.setDescription(rs.getString("description") != null ? rs.getString("description") : "");
                            return item;
                        }
                    },
                    userId);
        } catch (Exception ex) {
            System.err.println("Error fetching cart items: " + ex.getMessage());
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Remove cart item
     */
    public boolean removeCartItem(String userId, int bookId) {
        try {
            int rowsAffected = jdbcTemplate.update(
                    "DELETE FROM shopping_cart WHERE user_id = ? AND book_id = ?",
                    userId, bookId);
            return rowsAffected > 0;
        } catch (Exception ex) {
            System.err.println("Error removing cart item: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Clear all cart items for a user
     */
    public boolean clearCart(String userId) {
        try {
            int rowsAffected = jdbcTemplate.update(
                    "DELETE FROM shopping_cart WHERE user_id = ?",
                    userId);
            return rowsAffected >= 0; // Return true even if no items to delete
        } catch (Exception ex) {
            System.err.println("Error clearing cart: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Save entire cart (sync session cart to database)
     */
    public boolean saveCart(String userId, List<CartItemDTO> cartItems) {
        try {
            // Clear existing cart
            clearCart(userId);
            
            // Save all items
            for (CartItemDTO item : cartItems) {
                saveCartItem(userId, item.getBookId(), item.getQuantity());
            }
            return true;
        } catch (Exception ex) {
            System.err.println("Error saving cart: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Get cart item count for a user
     */
    public int getCartItemCount(String userId) {
        try {
            Integer count = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM shopping_cart WHERE user_id = ?",
                    Integer.class,
                    userId);
            return count != null ? count : 0;
        } catch (Exception ex) {
            System.err.println("Error getting cart count: " + ex.getMessage());
            ex.printStackTrace();
            return 0;
        }
    }
}

