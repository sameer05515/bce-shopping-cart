package com.p.bce.shopping.cart.rpc.pojo;

import java.util.Date;

public class BookReviewDTO {
    private int reviewId;
    private int bookId;
    private String userId;
    private int rating; // 1-5 stars
    private String reviewText;
    private Date reviewDate;
    private boolean isApproved;
    private int helpfulCount;
    
    // Additional fields for display
    private String userName;
    private String bookTitle;

    public BookReviewDTO() {
        super();
    }

    public BookReviewDTO(int bookId, String userId, int rating, String reviewText) {
        super();
        this.bookId = bookId;
        this.userId = userId;
        this.rating = rating;
        this.reviewText = reviewText;
        this.reviewDate = new Date();
        this.isApproved = false;
        this.helpfulCount = 0;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean isApproved) {
        this.isApproved = isApproved;
    }

    public int getHelpfulCount() {
        return helpfulCount;
    }

    public void setHelpfulCount(int helpfulCount) {
        this.helpfulCount = helpfulCount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    @Override
    public String toString() {
        return "BookReviewDTO [reviewId=" + reviewId + ", bookId=" + bookId + ", userId=" + userId
                + ", rating=" + rating + ", reviewDate=" + reviewDate + ", isApproved=" + isApproved + "]";
    }
}

