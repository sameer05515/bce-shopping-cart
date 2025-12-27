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

import com.p.bce.shopping.cart.rpc.bc.BookReviewBC;
import com.p.bce.shopping.cart.rpc.pojo.BookReviewDTO;

@Controller
public class ReviewController {

    @Autowired
    private BookReviewBC bookReviewBC;

    @PostMapping("/pages/html/postLogin/Review/add")
    public String addReview(
            @RequestParam("bookId") int bookId,
            @RequestParam("rating") int rating,
            @RequestParam(value = "reviewText", required = false) String reviewText,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        System.out.println("ReviewController.addReview() called for bookId: " + bookId);
        String userName = (String) session.getAttribute("user");
        if (userName == null) {
            System.out.println("User not logged in, redirecting to Unauthorised");
            return "redirect:/pages/html/preLogin/Unauthorised.html";
        }

        if (rating < 1 || rating > 5) {
            redirectAttributes.addFlashAttribute("error", "Rating must be between 1 and 5 stars.");
            return "redirect:/pages/html/postLogin/Search";
        }

        BookReviewDTO review = new BookReviewDTO(bookId, userName, rating, reviewText != null ? reviewText : "");
        boolean success = bookReviewBC.addReview(review);
        
        if (success) {
            redirectAttributes.addFlashAttribute("message", "Review submitted successfully! It will be visible after admin approval.");
            System.out.println("Review added for book " + bookId + " by user " + userName);
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to submit review. Please try again.");
            System.err.println("ERROR: Failed to add review for book " + bookId);
        }

        return "redirect:/pages/html/postLogin/Search";
    }

    @PostMapping("/pages/html/postLogin/Review/voteHelpful")
    public String voteHelpful(
            @RequestParam("reviewId") int reviewId,
            @RequestParam("isHelpful") boolean isHelpful,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        System.out.println("ReviewController.voteHelpful() called for reviewId: " + reviewId);
        String userName = (String) session.getAttribute("user");
        if (userName == null) {
            System.out.println("User not logged in, redirecting to Unauthorised");
            return "redirect:/pages/html/preLogin/Unauthorised.html";
        }

        boolean success = bookReviewBC.voteHelpful(reviewId, userName, isHelpful);
        if (success) {
            redirectAttributes.addFlashAttribute("message", "Thank you for your feedback!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to record your vote.");
        }

        return "redirect:/pages/html/postLogin/Search";
    }

    // Admin endpoints
    @GetMapping("/admin/reviews/pending")
    public String pendingReviews(HttpSession session, Model model) {
        System.out.println("ReviewController.pendingReviews() called");
        String userName = (String) session.getAttribute("user");
        if (userName == null) {
            return "redirect:/pages/html/preLogin/Unauthorised.html";
        }

        try {
            List<BookReviewDTO> reviews = bookReviewBC.getPendingReviews();
            model.addAttribute("reviews", reviews);
            return "admin/PendingReviews";
        } catch (Exception e) {
            System.err.println("ERROR in pendingReviews: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Error loading pending reviews: " + e.getMessage());
            return "admin/PendingReviews";
        }
    }

    @PostMapping("/admin/reviews/approve")
    public String approveReview(
            @RequestParam("reviewId") int reviewId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        System.out.println("ReviewController.approveReview() called for reviewId: " + reviewId);
        String userName = (String) session.getAttribute("user");
        if (userName == null) {
            return "redirect:/pages/html/preLogin/Unauthorised.html";
        }

        boolean success = bookReviewBC.approveReview(reviewId);
        if (success) {
            redirectAttributes.addFlashAttribute("message", "Review approved successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to approve review.");
        }

        return "redirect:/admin/reviews/pending";
    }

    @PostMapping("/admin/reviews/delete")
    public String deleteReview(
            @RequestParam("reviewId") int reviewId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        System.out.println("ReviewController.deleteReview() called for reviewId: " + reviewId);
        String userName = (String) session.getAttribute("user");
        if (userName == null) {
            return "redirect:/pages/html/preLogin/Unauthorised.html";
        }

        boolean success = bookReviewBC.deleteReview(reviewId);
        if (success) {
            redirectAttributes.addFlashAttribute("message", "Review deleted successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to delete review.");
        }

        return "redirect:/admin/reviews/pending";
    }
}

