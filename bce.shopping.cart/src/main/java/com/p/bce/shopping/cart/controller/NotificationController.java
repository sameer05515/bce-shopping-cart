package com.p.bce.shopping.cart.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.p.bce.shopping.cart.rpc.bc.NotificationBC;
import com.p.bce.shopping.cart.rpc.pojo.NotificationDTO;

@Controller
public class NotificationController {

    @Autowired
    private NotificationBC notificationBC;

    @GetMapping({"/pages/html/postLogin/Notifications", "/pages/html/postLogin/Notifications.jsp"})
    public String viewNotifications(HttpSession session, Model model) {
        System.out.println("NotificationController.viewNotifications() called");
        String userName = (String) session.getAttribute("user");
        if (userName == null) {
            System.out.println("User not logged in, redirecting to Unauthorised");
            return "redirect:/pages/html/preLogin/Unauthorised.html";
        }

        System.out.println("User logged in: " + userName);
        try {
            List<NotificationDTO> notifications = notificationBC.getNotificationsByUser(userName, false);
            int unreadCount = notificationBC.getUnreadCount(userName);
            System.out.println("Notifications: " + (notifications != null ? notifications.size() : "null") + ", Unread: " + unreadCount);
            model.addAttribute("notifications", notifications);
            model.addAttribute("unreadCount", unreadCount);
            return "pages/postLogin/Notifications";
        } catch (Exception e) {
            System.err.println("ERROR in viewNotifications: " + e.getClass().getName() + ": " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Error loading notifications: " + e.getMessage());
            model.addAttribute("notifications", new java.util.ArrayList<>());
            return "pages/postLogin/Notifications";
        }
    }

    @GetMapping("/api/notifications/unread-count")
    @ResponseBody
    public int getUnreadCount(HttpSession session) {
        String userName = (String) session.getAttribute("user");
        if (userName == null) {
            return 0;
        }
        return notificationBC.getUnreadCount(userName);
    }

    @PostMapping("/pages/html/postLogin/Notifications/markRead")
    public String markAsRead(
            @RequestParam("notificationId") int notificationId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        System.out.println("NotificationController.markAsRead() called for notificationId: " + notificationId);
        String userName = (String) session.getAttribute("user");
        if (userName == null) {
            return "redirect:/pages/html/preLogin/Unauthorised.html";
        }

        boolean success = notificationBC.markAsRead(notificationId, userName);
        if (success) {
            redirectAttributes.addFlashAttribute("message", "Notification marked as read.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to mark notification as read.");
        }

        return "redirect:/pages/html/postLogin/Notifications";
    }

    @PostMapping("/pages/html/postLogin/Notifications/markAllRead")
    public String markAllAsRead(HttpSession session, RedirectAttributes redirectAttributes) {
        System.out.println("NotificationController.markAllAsRead() called");
        String userName = (String) session.getAttribute("user");
        if (userName == null) {
            return "redirect:/pages/html/preLogin/Unauthorised.html";
        }

        boolean success = notificationBC.markAllAsRead(userName);
        if (success) {
            redirectAttributes.addFlashAttribute("message", "All notifications marked as read.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to mark notifications as read.");
        }

        return "redirect:/pages/html/postLogin/Notifications";
    }

    @PostMapping("/pages/html/postLogin/Notifications/delete")
    public String deleteNotification(
            @RequestParam("notificationId") int notificationId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        System.out.println("NotificationController.deleteNotification() called for notificationId: " + notificationId);
        String userName = (String) session.getAttribute("user");
        if (userName == null) {
            return "redirect:/pages/html/preLogin/Unauthorised.html";
        }

        boolean success = notificationBC.deleteNotification(notificationId, userName);
        if (success) {
            redirectAttributes.addFlashAttribute("message", "Notification deleted successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to delete notification.");
        }

        return "redirect:/pages/html/postLogin/Notifications";
    }
}

