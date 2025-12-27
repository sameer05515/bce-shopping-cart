<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Order Details - Admin - BCE Shopping Cart</title>
        <link rel="stylesheet" href="/pages/lib/css/main.css">
    </head>
    <body class="body-style">
        <jsp:include page="/WEB-INF/views/common/Header.jsp" />
        
        <div class="page-container">
            <div class="page-header">
                <h1>📄 Order Details</h1>
            </div>

            <%@ page language="java" import="java.util.*"%>
            <%@ page language="java" import="com.p.bce.shopping.cart.rpc.pojo.OrderDTO"%>
            <%@ page language="java" import="com.p.bce.shopping.cart.rpc.pojo.OrderDetailDTO"%>
            <%@ page language="java" import="java.text.SimpleDateFormat"%>

            <%
                OrderDTO order = (OrderDTO) request.getAttribute("order");
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm");
            %>

            <% if (order != null) { %>
                <div class="card">
                    <div class="card-header">Order Information</div>
                    <div class="profile-info">
                        <div class="profile-label">Order ID:</div>
                        <div class="profile-value"><strong>#<%= order.getOrderId() %></strong></div>
                    </div>
                    <div class="profile-info">
                        <div class="profile-label">Customer:</div>
                        <div class="profile-value"><%= order.getUserId() %></div>
                    </div>
                    <div class="profile-info">
                        <div class="profile-label">Order Date:</div>
                        <div class="profile-value">
                            <%= order.getOrderDate() != null ? dateFormat.format(order.getOrderDate()) : "N/A" %>
                        </div>
                    </div>
                    <div class="profile-info">
                        <div class="profile-label">Status:</div>
                        <div class="profile-value">
                            <form method="POST" action="/admin/orders/updateStatus" style="display: inline;">
                                <input type="hidden" name="id" value="<%= order.getOrderId() %>">
                                <select name="status" class="form-control" style="display: inline-block; width: auto; margin-right: 10px;"
                                        onchange="this.form.submit()">
                                    <option value="PENDING" <%= "PENDING".equals(order.getStatus()) ? "selected" : "" %>>PENDING</option>
                                    <option value="CONFIRMED" <%= "CONFIRMED".equals(order.getStatus()) ? "selected" : "" %>>CONFIRMED</option>
                                    <option value="PROCESSING" <%= "PROCESSING".equals(order.getStatus()) ? "selected" : "" %>>PROCESSING</option>
                                    <option value="SHIPPED" <%= "SHIPPED".equals(order.getStatus()) ? "selected" : "" %>>SHIPPED</option>
                                    <option value="DELIVERED" <%= "DELIVERED".equals(order.getStatus()) ? "selected" : "" %>>DELIVERED</option>
                                    <option value="CANCELLED" <%= "CANCELLED".equals(order.getStatus()) ? "selected" : "" %>>CANCELLED</option>
                                </select>
                            </form>
                        </div>
                    </div>
                    <div class="profile-info">
                        <div class="profile-label">Total Amount:</div>
                        <div class="profile-value">
                            <strong style="font-size: 1.2em; color: #28a745;">₹<%= String.format("%.2f", order.getTotalAmount()) %></strong>
                        </div>
                    </div>
                </div>

                <div class="card">
                    <div class="card-header">Order Items</div>
                    <div class="table-container">
                        <table class="data-table">
                            <thead>
                                <tr>
                                    <th>Book</th>
                                    <th>Quantity</th>
                                    <th>Unit Price</th>
                                    <th>Subtotal</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% if (order.getOrderDetails() != null) { %>
                                    <% for (OrderDetailDTO detail : order.getOrderDetails()) { %>
                                        <tr>
                                            <td>
                                                <strong><%= detail.getBookTitle() != null ? detail.getBookTitle() : "Book ID: " + detail.getBookId() %></strong><br>
                                                <% if (detail.getBookAuthor() != null) { %>
                                                    <small>by <%= detail.getBookAuthor() %></small>
                                                <% } %>
                                            </td>
                                            <td><%= detail.getQuantity() %></td>
                                            <td>₹<%= String.format("%.2f", detail.getPrice()) %></td>
                                            <td><strong>₹<%= String.format("%.2f", detail.getSubtotal()) %></strong></td>
                                        </tr>
                                    <% } %>
                                <% } %>
                            </tbody>
                        </table>
                    </div>
                </div>

                <div class="btn-group">
                    <a href="/admin/orders" class="btn btn-secondary">← Back to Orders</a>
                </div>
            <% } else { %>
                <div class="alert alert-error">
                    Order not found.
                </div>
            <% } %>
        </div>
        
        <jsp:include page="/WEB-INF/views/common/Footer.jsp" />
    </body>
</html>

