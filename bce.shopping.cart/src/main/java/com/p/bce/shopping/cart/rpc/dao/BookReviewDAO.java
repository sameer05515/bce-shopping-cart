package com.p.bce.shopping.cart.rpc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.p.bce.shopping.cart.rpc.pojo.BookReviewDTO;

@Repository
public class BookReviewDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean addReview(BookReviewDTO review) {
        try {
            String sql = "INSERT INTO book_reviews (bookid, userid, rating, review_text, review_date, is_approved) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";
            int rows = jdbcTemplate.update(sql, 
                review.getBookId(), 
                review.getUserId(), 
                review.getRating(),
                review.getReviewText(),
                review.getReviewDate() != null ? review.getReviewDate() : new Date(),
                review.isApproved() ? 1 : 0);
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<BookReviewDTO> getReviewsByBook(int bookId, boolean approvedOnly) {
        try {
            String sql = "SELECT r.review_id, r.bookid, r.userid, r.rating, r.review_text, " +
                        "r.review_date, r.is_approved, r.helpful_count, " +
                        "u.firstname, u.lastname, b.title " +
                        "FROM book_reviews r " +
                        "LEFT JOIN user_profile u ON r.userid = u.username " +
                        "LEFT JOIN book_details b ON r.bookid = b.bookid " +
                        "WHERE r.bookid = ? " +
                        (approvedOnly ? "AND r.is_approved = 1 " : "") +
                        "ORDER BY r.review_date DESC";
            
            return jdbcTemplate.query(sql, new RowMapper<BookReviewDTO>() {
                @Override
                public BookReviewDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                    BookReviewDTO dto = new BookReviewDTO();
                    dto.setReviewId(rs.getInt("review_id"));
                    dto.setBookId(rs.getInt("bookid"));
                    dto.setUserId(rs.getString("userid"));
                    dto.setRating(rs.getInt("rating"));
                    dto.setReviewText(rs.getString("review_text"));
                    dto.setReviewDate(rs.getTimestamp("review_date"));
                    dto.setApproved(rs.getInt("is_approved") == 1);
                    dto.setHelpfulCount(rs.getInt("helpful_count"));
                    
                    String firstName = rs.getString("firstname");
                    String lastName = rs.getString("lastname");
                    if (firstName != null && lastName != null) {
                        dto.setUserName(firstName + " " + lastName);
                    } else {
                        dto.setUserName(rs.getString("userid"));
                    }
                    
                    dto.setBookTitle(rs.getString("title"));
                    return dto;
                }
            }, bookId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public double getAverageRating(int bookId) {
        try {
            String sql = "SELECT AVG(rating) FROM book_reviews WHERE bookid = ? AND is_approved = 1";
            Double avg = jdbcTemplate.queryForObject(sql, Double.class, bookId);
            return avg != null ? avg : 0.0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    public int getReviewCount(int bookId) {
        try {
            String sql = "SELECT COUNT(*) FROM book_reviews WHERE bookid = ? AND is_approved = 1";
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, bookId);
            return count != null ? count : 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean voteHelpful(int reviewId, String userId, boolean isHelpful) {
        try {
            // Check if user already voted
            String checkSql = "SELECT COUNT(*) FROM review_votes WHERE review_id = ? AND userid = ?";
            Integer count = jdbcTemplate.queryForObject(checkSql, Integer.class, reviewId, userId);
            
            if (count != null && count > 0) {
                // Update existing vote
                String updateSql = "UPDATE review_votes SET is_helpful = ? WHERE review_id = ? AND userid = ?";
                jdbcTemplate.update(updateSql, isHelpful ? 1 : 0, reviewId, userId);
            } else {
                // Insert new vote
                String insertSql = "INSERT INTO review_votes (review_id, userid, is_helpful, vote_date) VALUES (?, ?, ?, ?)";
                jdbcTemplate.update(insertSql, reviewId, userId, isHelpful ? 1 : 0, new Date());
            }
            
            // Update helpful count
            updateHelpfulCount(reviewId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void updateHelpfulCount(int reviewId) {
        try {
            String sql = "UPDATE book_reviews SET helpful_count = " +
                        "(SELECT COUNT(*) FROM review_votes WHERE review_id = ? AND is_helpful = 1) " +
                        "WHERE review_id = ?";
            jdbcTemplate.update(sql, reviewId, reviewId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<BookReviewDTO> getPendingReviews() {
        try {
            String sql = "SELECT r.review_id, r.bookid, r.userid, r.rating, r.review_text, " +
                        "r.review_date, r.is_approved, r.helpful_count, " +
                        "u.firstname, u.lastname, b.title " +
                        "FROM book_reviews r " +
                        "LEFT JOIN user_profile u ON r.userid = u.username " +
                        "LEFT JOIN book_details b ON r.bookid = b.bookid " +
                        "WHERE r.is_approved = 0 " +
                        "ORDER BY r.review_date DESC";
            
            return jdbcTemplate.query(sql, new RowMapper<BookReviewDTO>() {
                @Override
                public BookReviewDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                    BookReviewDTO dto = new BookReviewDTO();
                    dto.setReviewId(rs.getInt("review_id"));
                    dto.setBookId(rs.getInt("bookid"));
                    dto.setUserId(rs.getString("userid"));
                    dto.setRating(rs.getInt("rating"));
                    dto.setReviewText(rs.getString("review_text"));
                    dto.setReviewDate(rs.getTimestamp("review_date"));
                    dto.setApproved(rs.getInt("is_approved") == 1);
                    dto.setHelpfulCount(rs.getInt("helpful_count"));
                    
                    String firstName = rs.getString("firstname");
                    String lastName = rs.getString("lastname");
                    if (firstName != null && lastName != null) {
                        dto.setUserName(firstName + " " + lastName);
                    } else {
                        dto.setUserName(rs.getString("userid"));
                    }
                    
                    dto.setBookTitle(rs.getString("title"));
                    return dto;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean approveReview(int reviewId) {
        try {
            String sql = "UPDATE book_reviews SET is_approved = 1 WHERE review_id = ?";
            int rows = jdbcTemplate.update(sql, reviewId);
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteReview(int reviewId) {
        try {
            String sql = "DELETE FROM book_reviews WHERE review_id = ?";
            int rows = jdbcTemplate.update(sql, reviewId);
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

