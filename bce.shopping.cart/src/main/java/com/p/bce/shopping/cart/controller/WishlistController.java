package com.p.bce.shopping.cart.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.p.bce.shopping.cart.rpc.bc.WishlistBC;
import com.p.bce.shopping.cart.rpc.pojo.WishlistDTO;

@Controller
public class WishlistController {

    @Autowired
    private WishlistBC wishlistBC;

    @GetMapping({"/pages/html/postLogin/Wishlist", "/pages/html/postLogin/Wishlist.jsp"})
    public String viewWishlist(HttpSession session, Model model) {
        System.out.println("WishlistController.viewWishlist() called");
        String userName = (String) session.getAttribute("user");
        if (userName == null) {
            System.out.println("User not logged in, redirecting to Unauthorised");
            return "redirect:/pages/html/preLogin/Unauthorised.html";
        }

        System.out.println("User logged in: " + userName);
        try {
            List<WishlistDTO> wishlist = wishlistBC.getWishlistByUser(userName);
            System.out.println("Wishlist items: " + (wishlist != null ? wishlist.size() : "null"));
            model.addAttribute("wishlistItems", wishlist);
            return "pages/postLogin/Wishlist";
        } catch (Exception e) {
            System.err.println("ERROR in viewWishlist: " + e.getClass().getName() + ": " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Error loading wishlist: " + e.getMessage());
            model.addAttribute("wishlistItems", new java.util.ArrayList<>());
            return "pages/postLogin/Wishlist";
        }
    }

    @PostMapping("/pages/html/postLogin/Wishlist/add")
    public String addToWishlist(
            @RequestParam("bookId") int bookId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        System.out.println("WishlistController.addToWishlist() called for bookId: " + bookId);
        String userName = (String) session.getAttribute("user");
        if (userName == null) {
            System.out.println("User not logged in, redirecting to Unauthorised");
            return "redirect:/pages/html/preLogin/Unauthorised.html";
        }

        System.out.println("User logged in: " + userName);
        boolean success = wishlistBC.addToWishlist(userName, bookId);
        if (success) {
            redirectAttributes.addFlashAttribute("message", "Book added to wishlist successfully!");
            System.out.println("Book " + bookId + " added to wishlist for user " + userName);
        } else {
            redirectAttributes.addFlashAttribute("error", "Book is already in your wishlist or could not be added.");
            System.err.println("ERROR: Failed to add book " + bookId + " to wishlist");
        }

        return "redirect:/pages/html/postLogin/Search";
    }

    @PostMapping("/pages/html/postLogin/Wishlist/remove")
    public String removeFromWishlist(
            @RequestParam("bookId") int bookId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        System.out.println("WishlistController.removeFromWishlist() called for bookId: " + bookId);
        String userName = (String) session.getAttribute("user");
        if (userName == null) {
            System.out.println("User not logged in, redirecting to Unauthorised");
            return "redirect:/pages/html/preLogin/Unauthorised.html";
        }

        System.out.println("User logged in: " + userName);
        boolean success = wishlistBC.removeFromWishlist(userName, bookId);
        if (success) {
            redirectAttributes.addFlashAttribute("message", "Book removed from wishlist successfully!");
            System.out.println("Book " + bookId + " removed from wishlist for user " + userName);
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to remove book from wishlist.");
            System.err.println("ERROR: Failed to remove book " + bookId + " from wishlist");
        }

        return "redirect:/pages/html/postLogin/Wishlist";
    }

    @PostMapping("/pages/html/postLogin/Wishlist/removeById")
    public String removeFromWishlistById(
            @RequestParam("wishlistId") int wishlistId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        System.out.println("WishlistController.removeFromWishlistById() called for wishlistId: " + wishlistId);
        String userName = (String) session.getAttribute("user");
        if (userName == null) {
            System.out.println("User not logged in, redirecting to Unauthorised");
            return "redirect:/pages/html/preLogin/Unauthorised.html";
        }

        System.out.println("User logged in: " + userName);
        boolean success = wishlistBC.removeFromWishlistById(wishlistId, userName);
        if (success) {
            redirectAttributes.addFlashAttribute("message", "Book removed from wishlist successfully!");
            System.out.println("Wishlist item " + wishlistId + " removed for user " + userName);
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to remove book from wishlist.");
            System.err.println("ERROR: Failed to remove wishlist item " + wishlistId);
        }

        return "redirect:/pages/html/postLogin/Wishlist";
    }

    @PostMapping("/pages/html/postLogin/Wishlist/moveToCart")
    public String moveToCart(
            @RequestParam("bookId") int bookId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        System.out.println("WishlistController.moveToCart() called for bookId: " + bookId);
        String userName = (String) session.getAttribute("user");
        if (userName == null) {
            System.out.println("User not logged in, redirecting to Unauthorised");
            return "redirect:/pages/html/preLogin/Unauthorised.html";
        }

        // Remove from wishlist and redirect to cart add
        wishlistBC.removeFromWishlist(userName, bookId);
        redirectAttributes.addFlashAttribute("message", "Book moved to cart!");
        return "redirect:/cart/add?bookIds=" + bookId;
    }
}

