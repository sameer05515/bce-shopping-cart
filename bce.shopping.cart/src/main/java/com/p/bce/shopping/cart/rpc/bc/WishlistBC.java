package com.p.bce.shopping.cart.rpc.bc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.p.bce.shopping.cart.rpc.dao.WishlistDAO;
import com.p.bce.shopping.cart.rpc.pojo.WishlistDTO;

@Service
public class WishlistBC {

    @Autowired
    private WishlistDAO wishlistDAO;

    public boolean addToWishlist(String userId, int bookId) {
        if (userId == null || userId.trim().isEmpty() || bookId <= 0) {
            return false;
        }
        return wishlistDAO.addToWishlist(userId, bookId);
    }

    public boolean removeFromWishlist(String userId, int bookId) {
        if (userId == null || userId.trim().isEmpty() || bookId <= 0) {
            return false;
        }
        return wishlistDAO.removeFromWishlist(userId, bookId);
    }

    public boolean removeFromWishlistById(int wishlistId, String userId) {
        if (userId == null || userId.trim().isEmpty() || wishlistId <= 0) {
            return false;
        }
        return wishlistDAO.removeFromWishlistById(wishlistId, userId);
    }

    public boolean isInWishlist(String userId, int bookId) {
        if (userId == null || userId.trim().isEmpty() || bookId <= 0) {
            return false;
        }
        return wishlistDAO.isInWishlist(userId, bookId);
    }

    public List<WishlistDTO> getWishlistByUser(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            return new java.util.ArrayList<>();
        }
        return wishlistDAO.getWishlistByUser(userId);
    }

    public int getWishlistCount(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            return 0;
        }
        return wishlistDAO.getWishlistCount(userId);
    }
}

