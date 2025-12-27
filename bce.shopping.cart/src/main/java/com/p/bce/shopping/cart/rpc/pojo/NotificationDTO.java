package com.p.bce.shopping.cart.rpc.pojo;

import java.util.Date;

public class NotificationDTO {
    private int notificationId;
    private String userId;
    private String title;
    private String message;
    private String type; // info, success, warning, error
    private boolean isRead;
    private Date createdDate;
    private String linkUrl;

    public NotificationDTO() {
        super();
    }

    public NotificationDTO(String userId, String title, String message, String type) {
        super();
        this.userId = userId;
        this.title = title;
        this.message = message;
        this.type = type;
        this.isRead = false;
        this.createdDate = new Date();
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    @Override
    public String toString() {
        return "NotificationDTO [notificationId=" + notificationId + ", userId=" + userId + ", title=" + title
                + ", type=" + type + ", isRead=" + isRead + "]";
    }
}

