package com.p.bce.shopping.cart.rpc.bc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.p.bce.shopping.cart.rpc.dao.CartDAO;
import com.p.bce.shopping.cart.rpc.pojo.CartItemDTO;

@Service
public class CartBC {

    @Autowired
    private CartDAO cartDAO;

    /**
     * Save cart item
     */
    public boolean saveCartItem(String userId, int bookId, int quantity) {
        if (userId == null || userId.trim().isEmpty() || bookId <= 0 || quantity <= 0) {
            return false;
        }
        try {
            return cartDAO.saveCartItem(userId, bookId, quantity);
        } catch (Exception e) {
            System.err.println("ERROR in saveCartItem: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get cart items from database
     */
    public List<CartItemDTO> getCartItems(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            return new java.util.ArrayList<>();
        }
        try {
            return cartDAO.getCartItems(userId);
        } catch (Exception e) {
            System.err.println("ERROR in getCartItems: " + e.getMessage());
            e.printStackTrace();
            return new java.util.ArrayList<>();
        }
    }

    /**
     * Remove cart item
     */
    public boolean removeCartItem(String userId, int bookId) {
        if (userId == null || userId.trim().isEmpty() || bookId <= 0) {
            return false;
        }
        try {
            return cartDAO.removeCartItem(userId, bookId);
        } catch (Exception e) {
            System.err.println("ERROR in removeCartItem: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Clear cart
     */
    public boolean clearCart(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            return false;
        }
        try {
            return cartDAO.clearCart(userId);
        } catch (Exception e) {
            System.err.println("ERROR in clearCart: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Save entire cart
     */
    public boolean saveCart(String userId, List<CartItemDTO> cartItems) {
        if (userId == null || userId.trim().isEmpty()) {
            return false;
        }
        if (cartItems == null || cartItems.isEmpty()) {
            return clearCart(userId);
        }
        try {
            return cartDAO.saveCart(userId, cartItems);
        } catch (Exception e) {
            System.err.println("ERROR in saveCart: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get cart item count
     */
    public int getCartItemCount(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            return 0;
        }
        try {
            return cartDAO.getCartItemCount(userId);
        } catch (Exception e) {
            System.err.println("ERROR in getCartItemCount: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }
}

