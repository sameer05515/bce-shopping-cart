package com.p.bce.shopping.cart.rpc.pojo;

import java.util.Date;

public class WishlistDTO {
    private int wishlistId;
    private String userId;
    private int bookId;
    private Date addedDate;
    
    // Additional fields for display
    private String bookTitle;
    private String bookAuthor;
    private String bookPublisher;
    private double bookPrice;
    private int availableQuantity;

    public WishlistDTO() {
        super();
    }

    public WishlistDTO(String userId, int bookId) {
        super();
        this.userId = userId;
        this.bookId = bookId;
        this.addedDate = new Date();
    }

    public int getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(int wishlistId) {
        this.wishlistId = wishlistId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public void setBookPublisher(String bookPublisher) {
        this.bookPublisher = bookPublisher;
    }

    public double getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(double bookPrice) {
        this.bookPrice = bookPrice;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    @Override
    public String toString() {
        return "WishlistDTO [wishlistId=" + wishlistId + ", userId=" + userId + ", bookId=" + bookId
                + ", addedDate=" + addedDate + "]";
    }
}

