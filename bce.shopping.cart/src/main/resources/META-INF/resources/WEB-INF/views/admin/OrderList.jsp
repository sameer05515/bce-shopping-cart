<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Order Management - BCE Shopping Cart</title>
        <link rel="stylesheet" href="/pages/lib/css/main.css">
    </head>
    <body class="body-style">
        <jsp:include page="/WEB-INF/views/common/Header.jsp" />
        
        <div class="page-container">
            <div class="page-header">
                <h1>📋 Order Management</h1>
            </div>

            <%@ page language="java" import="java.util.*"%>
            <%@ page language="java" import="com.p.bce.shopping.cart.rpc.pojo.OrderDTO"%>
            <%@ page language="java" import="java.text.SimpleDateFormat"%>

            <%
                String message = (String) request.getAttribute("message");
                String error = (String) request.getAttribute("error");
                @SuppressWarnings("unchecked")
                List<OrderDTO> orders = (List<OrderDTO>) request.getAttribute("orders");
                
                if (orders == null) {
                    orders = new ArrayList<>();
                }
                
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm");
            %>

            <% if (message != null) { %>
                <div class="alert alert-success">
                    <strong>Success:</strong> <%= message %>
                </div>
            <% } %>

            <% if (error != null) { %>
                <div class="alert alert-error">
                    <strong>Error:</strong> <%= error %>
                </div>
            <% } %>

            <div class="card">
                <div class="card-header">All Orders (<%= orders.size() %>)</div>

                <% if (orders.isEmpty()) { %>
                    <div class="alert alert-info">
                        No orders found.
                    </div>
                <% } else { %>
                    <div class="table-container">
                        <table class="data-table">
                            <thead>
                                <tr>
                                    <th>Order ID</th>
                                    <th>Customer</th>
                                    <th>Date</th>
                                    <th>Total Amount</th>
                                    <th>Status</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% for (OrderDTO order : orders) { %>
                                    <tr>
                                        <td><strong>#<%= order.getOrderId() %></strong></td>
                                        <td><%= order.getUserId() %></td>
                                        <td>
                                            <%= order.getOrderDate() != null ? dateFormat.format(order.getOrderDate()) : "N/A" %>
                                        </td>
                                        <td><strong style="color: #28a745;">₹<%= String.format("%.2f", order.getTotalAmount()) %></strong></td>
                                        <td>
                                            <span style="padding: 5px 10px; border-radius: 5px; background: #667eea; color: white; font-size: 0.9em;">
                                                <%= order.getStatus() != null ? order.getStatus() : "CONFIRMED" %>
                                            </span>
                                        </td>
                                        <td>
                                            <a href="/admin/orders/details?id=<%= order.getOrderId() %>" 
                                               class="btn btn-secondary" style="padding: 5px 15px; font-size: 0.9em;">
                                                👁️ View Details
                                            </a>
                                        </td>
                                    </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>
                <% } %>
            </div>
        </div>
        
        <jsp:include page="/WEB-INF/views/common/Footer.jsp" />
    </body>
</html>

