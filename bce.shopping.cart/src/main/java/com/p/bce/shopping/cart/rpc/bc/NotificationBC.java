package com.p.bce.shopping.cart.rpc.bc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.p.bce.shopping.cart.rpc.dao.NotificationDAO;
import com.p.bce.shopping.cart.rpc.pojo.NotificationDTO;

@Service
public class NotificationBC {

    @Autowired
    private NotificationDAO notificationDAO;

    public boolean createNotification(NotificationDTO notification) {
        if (notification == null || notification.getUserId() == null || 
            notification.getTitle() == null || notification.getMessage() == null) {
            return false;
        }
        return notificationDAO.createNotification(notification);
    }

    public List<NotificationDTO> getNotificationsByUser(String userId, boolean unreadOnly) {
        if (userId == null || userId.trim().isEmpty()) {
            return new java.util.ArrayList<>();
        }
        return notificationDAO.getNotificationsByUser(userId, unreadOnly);
    }

    public int getUnreadCount(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            return 0;
        }
        return notificationDAO.getUnreadCount(userId);
    }

    public boolean markAsRead(int notificationId, String userId) {
        if (notificationId <= 0 || userId == null || userId.trim().isEmpty()) {
            return false;
        }
        return notificationDAO.markAsRead(notificationId, userId);
    }

    public boolean markAllAsRead(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            return false;
        }
        return notificationDAO.markAllAsRead(userId);
    }

    public boolean deleteNotification(int notificationId, String userId) {
        if (notificationId <= 0 || userId == null || userId.trim().isEmpty()) {
            return false;
        }
        return notificationDAO.deleteNotification(notificationId, userId);
    }

    // Helper method to create common notifications
    public void notifyOrderPlaced(String userId, int orderId) {
        NotificationDTO notification = new NotificationDTO(
            userId,
            "Order Placed Successfully",
            "Your order #" + orderId + " has been placed successfully.",
            "success"
        );
        notification.setLinkUrl("/pages/html/postLogin/OrderDetails?id=" + orderId);
        createNotification(notification);
    }

    public void notifyOrderStatusChanged(String userId, int orderId, String status) {
        NotificationDTO notification = new NotificationDTO(
            userId,
            "Order Status Updated",
            "Your order #" + orderId + " status has been updated to " + status + ".",
            "info"
        );
        notification.setLinkUrl("/pages/html/postLogin/OrderDetails?id=" + orderId);
        createNotification(notification);
    }
}

