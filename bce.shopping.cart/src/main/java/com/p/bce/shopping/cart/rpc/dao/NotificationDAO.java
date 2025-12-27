package com.p.bce.shopping.cart.rpc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.p.bce.shopping.cart.rpc.pojo.NotificationDTO;

@Repository
public class NotificationDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean createNotification(NotificationDTO notification) {
        try {
            String sql = "INSERT INTO notifications (userid, title, message, type, is_read, created_date, link_url) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";
            int rows = jdbcTemplate.update(sql,
                notification.getUserId(),
                notification.getTitle(),
                notification.getMessage(),
                notification.getType(),
                notification.isRead() ? 1 : 0,
                notification.getCreatedDate() != null ? notification.getCreatedDate() : new Date(),
                notification.getLinkUrl());
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<NotificationDTO> getNotificationsByUser(String userId, boolean unreadOnly) {
        try {
            String sql = "SELECT notification_id, userid, title, message, type, is_read, created_date, link_url " +
                        "FROM notifications " +
                        "WHERE userid = ? " +
                        (unreadOnly ? "AND is_read = 0 " : "") +
                        "ORDER BY created_date DESC " +
                        "LIMIT 50";
            
            return jdbcTemplate.query(sql, new RowMapper<NotificationDTO>() {
                @Override
                public NotificationDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                    NotificationDTO dto = new NotificationDTO();
                    dto.setNotificationId(rs.getInt("notification_id"));
                    dto.setUserId(rs.getString("userid"));
                    dto.setTitle(rs.getString("title"));
                    dto.setMessage(rs.getString("message"));
                    dto.setType(rs.getString("type"));
                    dto.setRead(rs.getInt("is_read") == 1);
                    dto.setCreatedDate(rs.getTimestamp("created_date"));
                    dto.setLinkUrl(rs.getString("link_url"));
                    return dto;
                }
            }, userId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public int getUnreadCount(String userId) {
        try {
            String sql = "SELECT COUNT(*) FROM notifications WHERE userid = ? AND is_read = 0";
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId);
            return count != null ? count : 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean markAsRead(int notificationId, String userId) {
        try {
            String sql = "UPDATE notifications SET is_read = 1 WHERE notification_id = ? AND userid = ?";
            int rows = jdbcTemplate.update(sql, notificationId, userId);
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean markAllAsRead(String userId) {
        try {
            String sql = "UPDATE notifications SET is_read = 1 WHERE userid = ? AND is_read = 0";
            int rows = jdbcTemplate.update(sql, userId);
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteNotification(int notificationId, String userId) {
        try {
            String sql = "DELETE FROM notifications WHERE notification_id = ? AND userid = ?";
            int rows = jdbcTemplate.update(sql, notificationId, userId);
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

