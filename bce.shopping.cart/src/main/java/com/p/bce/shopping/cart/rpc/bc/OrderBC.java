package com.p.bce.shopping.cart.rpc.bc;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.p.bce.shopping.cart.rpc.dao.BookDetailsDAO;
import com.p.bce.shopping.cart.rpc.dao.OrderDAO;
import com.p.bce.shopping.cart.rpc.pojo.BookDetailDTO;
import com.p.bce.shopping.cart.rpc.pojo.OrderDTO;
import com.p.bce.shopping.cart.rpc.pojo.OrderDetailDTO;

@Service
public class OrderBC {

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private BookDetailsDAO bookDetailsDAO;

    @Transactional
    public int createOrder(OrderDTO order, List<OrderDetailDTO> orderDetails) {
        // Validate order
        if (order.getUserId() == null || order.getUserId().trim().isEmpty()) {
            return 0;
        }
        if (order.getTotalAmount() == null || order.getTotalAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return 0;
        }
        if (orderDetails == null || orderDetails.isEmpty()) {
            return 0;
        }

        // Validate and update inventory
        for (OrderDetailDTO detail : orderDetails) {
            BookDetailDTO book = bookDetailsDAO.getBookDetail(detail.getBookId());
            if (book == null || book.getBookId() == 0) {
                return 0; // Book not found
            }
            if (book.getQuantity() < detail.getQuantity()) {
                return 0; // Insufficient stock
            }
        }

        // Set order date if not set
        if (order.getOrderDate() == null) {
            order.setOrderDate(new Date());
        }

        // Create order
        int orderId = orderDAO.createOrder(order);
        if (orderId == 0) {
            return 0;
        }

        // Create order details and update inventory
        for (OrderDetailDTO detail : orderDetails) {
            detail.setOrderId(orderId);
            
            // Get current book price
            BookDetailDTO book = bookDetailsDAO.getBookDetail(detail.getBookId());
            detail.setPrice(BigDecimal.valueOf(book.getPrice()));

            // Create order detail
            if (!orderDAO.createOrderDetail(detail)) {
                return 0; // Rollback will happen
            }

            // Update inventory
            book.setQuantity(book.getQuantity() - detail.getQuantity());
            bookDetailsDAO.updateBook(book);
        }

        return orderId;
    }

    public List<OrderDTO> getOrdersByUser(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            return new java.util.ArrayList<>();
        }
        List<OrderDTO> orders = orderDAO.getOrdersByUser(userId);
        // Load order details for each order
        for (OrderDTO order : orders) {
            order.setOrderDetails(orderDAO.getOrderDetails(order.getOrderId()));
        }
        return orders;
    }

    public OrderDTO getOrderById(int orderId) {
        if (orderId <= 0) {
            return null;
        }
        OrderDTO order = orderDAO.getOrderById(orderId);
        if (order != null) {
            order.setOrderDetails(orderDAO.getOrderDetails(orderId));
        }
        return order;
    }

    public List<OrderDTO> getAllOrders() {
        List<OrderDTO> orders = orderDAO.getAllOrders();
        // Load order details for each order
        for (OrderDTO order : orders) {
            order.setOrderDetails(orderDAO.getOrderDetails(order.getOrderId()));
        }
        return orders;
    }

    public boolean cancelOrder(int orderId, String userId) {
        if (orderId <= 0 || userId == null) {
            return false;
        }

        OrderDTO order = orderDAO.getOrderById(orderId);
        if (order == null) {
            return false;
        }

        // Verify user owns the order
        if (!order.getUserId().equals(userId)) {
            return false;
        }

        // Restore inventory
        List<OrderDetailDTO> details = orderDAO.getOrderDetails(orderId);
        for (OrderDetailDTO detail : details) {
            BookDetailDTO book = bookDetailsDAO.getBookDetail(detail.getBookId());
            if (book != null) {
                book.setQuantity(book.getQuantity() + detail.getQuantity());
                bookDetailsDAO.updateBook(book);
            }
        }

        return orderDAO.cancelOrder(orderId);
    }

    public boolean updateOrderStatus(int orderId, String status) {
        if (orderId <= 0 || status == null || status.trim().isEmpty()) {
            return false;
        }
        return orderDAO.updateOrderStatus(orderId, status);
    }

}

