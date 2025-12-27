package com.p.bce.shopping.cart.rpc.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.p.bce.shopping.cart.rpc.pojo.OrderDTO;
import com.p.bce.shopping.cart.rpc.pojo.OrderDetailDTO;

@Repository
public class OrderDAO extends AbstractDAO {

    public int createOrder(OrderDTO order) {
        try {
            // Get next order ID
            Integer nextId = jdbcTemplate.queryForObject(
                    "SELECT COALESCE(MAX(orderid), 0) + 1 FROM order_table",
                    Integer.class);

            // Format date as YYYYMMDD
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            String orderDateStr = dateFormat.format(order.getOrderDate() != null ? order.getOrderDate() : new Date());

            // Insert order header
            int rowsAffected = jdbcTemplate.update(
                    "INSERT INTO order_table (orderid, userid, totalamount, orderdate) VALUES (?, ?, ?, ?)",
                    nextId,
                    order.getUserId(),
                    order.getTotalAmount(),
                    orderDateStr);

            if (rowsAffected > 0) {
                return nextId;
            }
            return 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public boolean createOrderDetail(OrderDetailDTO orderDetail) {
        try {
            int rowsAffected = jdbcTemplate.update(
                    "INSERT INTO order_details (orderid, bookid, quantity) VALUES (?, ?, ?)",
                    orderDetail.getOrderId(),
                    orderDetail.getBookId(),
                    orderDetail.getQuantity());
            return rowsAffected > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<OrderDTO> getOrdersByUser(String userId) {
        try {
            return jdbcTemplate.query(
                    "SELECT orderid, userid, totalamount, orderdate FROM order_table WHERE userid = ? ORDER BY orderid DESC",
                    new RowMapper<OrderDTO>() {
                        @Override
                        public OrderDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                            OrderDTO order = new OrderDTO();
                            order.setOrderId(rs.getInt("orderid"));
                            order.setUserId(rs.getString("userid"));
                            order.setTotalAmount(rs.getBigDecimal("totalamount"));
                            
                            // Parse date from YYYYMMDD format
                            try {
                                String dateStr = rs.getString("orderdate");
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                                order.setOrderDate(dateFormat.parse(dateStr));
                            } catch (Exception e) {
                                order.setOrderDate(new Date());
                            }
                            
                            order.setStatus("CONFIRMED"); // Default status
                            return order;
                        }
                    },
                    userId);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<OrderDTO>();
        }
    }

    public OrderDTO getOrderById(int orderId) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT orderid, userid, totalamount, orderdate FROM order_table WHERE orderid = ?",
                    new RowMapper<OrderDTO>() {
                        @Override
                        public OrderDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                            OrderDTO order = new OrderDTO();
                            order.setOrderId(rs.getInt("orderid"));
                            order.setUserId(rs.getString("userid"));
                            order.setTotalAmount(rs.getBigDecimal("totalamount"));
                            
                            // Parse date from YYYYMMDD format
                            try {
                                String dateStr = rs.getString("orderdate");
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                                order.setOrderDate(dateFormat.parse(dateStr));
                            } catch (Exception e) {
                                order.setOrderDate(new Date());
                            }
                            
                            order.setStatus("CONFIRMED"); // Default status
                            return order;
                        }
                    },
                    orderId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<OrderDetailDTO> getOrderDetails(int orderId) {
        try {
            return jdbcTemplate.query(
                    "SELECT od.orderid, od.bookid, od.quantity, b.price, b.title, b.author " +
                    "FROM order_details od " +
                    "LEFT JOIN book_details b ON od.bookid = b.bookid " +
                    "WHERE od.orderid = ?",
                    new RowMapper<OrderDetailDTO>() {
                        @Override
                        public OrderDetailDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                            OrderDetailDTO detail = new OrderDetailDTO();
                            detail.setOrderId(rs.getInt("orderid"));
                            detail.setBookId(rs.getInt("bookid"));
                            detail.setQuantity(rs.getInt("quantity"));
                            detail.setPrice(rs.getBigDecimal("price"));
                            detail.setBookTitle(rs.getString("title"));
                            detail.setBookAuthor(rs.getString("author"));
                            return detail;
                        }
                    },
                    orderId);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<OrderDetailDTO>();
        }
    }

    public List<OrderDTO> getAllOrders() {
        try {
            return jdbcTemplate.query(
                    "SELECT orderid, userid, totalamount, orderdate FROM order_table ORDER BY orderid DESC",
                    new RowMapper<OrderDTO>() {
                        @Override
                        public OrderDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                            OrderDTO order = new OrderDTO();
                            order.setOrderId(rs.getInt("orderid"));
                            order.setUserId(rs.getString("userid"));
                            order.setTotalAmount(rs.getBigDecimal("totalamount"));
                            
                            // Parse date from YYYYMMDD format
                            try {
                                String dateStr = rs.getString("orderdate");
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                                order.setOrderDate(dateFormat.parse(dateStr));
                            } catch (Exception e) {
                                order.setOrderDate(new Date());
                            }
                            
                            order.setStatus("CONFIRMED"); // Default status
                            return order;
                        }
                    });
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<OrderDTO>();
        }
    }

    public boolean updateOrderStatus(int orderId, String status) {
        try {
            // Note: This assumes we'll add a status column to order_table
            // For now, we'll just return true as the table doesn't have status yet
            // TODO: Add status column to order_table: ALTER TABLE order_table ADD COLUMN status VARCHAR(20) DEFAULT 'PENDING';
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean cancelOrder(int orderId) {
        return updateOrderStatus(orderId, "CANCELLED");
    }

}

