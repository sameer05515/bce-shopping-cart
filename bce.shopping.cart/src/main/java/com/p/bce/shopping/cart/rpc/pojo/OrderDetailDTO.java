package com.p.bce.shopping.cart.rpc.pojo;

import java.math.BigDecimal;

public class OrderDetailDTO {
    private int orderId;
    private int bookId;
    private int quantity;
    private BigDecimal price; // Price at time of order
    private String bookTitle; // For display purposes
    private String bookAuthor; // For display purposes

    public OrderDetailDTO() {
        super();
    }

    public OrderDetailDTO(int orderId, int bookId, int quantity, BigDecimal price) {
        super();
        this.orderId = orderId;
        this.bookId = bookId;
        this.quantity = quantity;
        this.price = price;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public BigDecimal getSubtotal() {
        if (price != null && quantity > 0) {
            return price.multiply(BigDecimal.valueOf(quantity));
        }
        return BigDecimal.ZERO;
    }

    @Override
    public String toString() {
        return "OrderDetailDTO [orderId=" + orderId + ", bookId=" + bookId + ", quantity=" + quantity
                + ", price=" + price + "]";
    }
}

