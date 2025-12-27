package com.p.bce.shopping.cart.rpc.bc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.p.bce.shopping.cart.rpc.dao.BookReviewDAO;
import com.p.bce.shopping.cart.rpc.pojo.BookReviewDTO;

@Service
public class BookReviewBC {

    @Autowired
    private BookReviewDAO bookReviewDAO;

    public boolean addReview(BookReviewDTO review) {
        if (review == null || review.getBookId() <= 0 || review.getUserId() == null || 
            review.getRating() < 1 || review.getRating() > 5) {
            return false;
        }
        return bookReviewDAO.addReview(review);
    }

    public List<BookReviewDTO> getReviewsByBook(int bookId, boolean approvedOnly) {
        if (bookId <= 0) {
            return new java.util.ArrayList<>();
        }
        return bookReviewDAO.getReviewsByBook(bookId, approvedOnly);
    }

    public double getAverageRating(int bookId) {
        if (bookId <= 0) {
            return 0.0;
        }
        return bookReviewDAO.getAverageRating(bookId);
    }

    public int getReviewCount(int bookId) {
        if (bookId <= 0) {
            return 0;
        }
        return bookReviewDAO.getReviewCount(bookId);
    }

    public boolean voteHelpful(int reviewId, String userId, boolean isHelpful) {
        if (reviewId <= 0 || userId == null || userId.trim().isEmpty()) {
            return false;
        }
        return bookReviewDAO.voteHelpful(reviewId, userId, isHelpful);
    }

    public List<BookReviewDTO> getPendingReviews() {
        return bookReviewDAO.getPendingReviews();
    }

    public boolean approveReview(int reviewId) {
        if (reviewId <= 0) {
            return false;
        }
        return bookReviewDAO.approveReview(reviewId);
    }

    public boolean deleteReview(int reviewId) {
        if (reviewId <= 0) {
            return false;
        }
        return bookReviewDAO.deleteReview(reviewId);
    }
}

