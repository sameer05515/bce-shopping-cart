<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Shopping Cart - BCE Shopping Cart</title>
        <link rel="stylesheet" href="/pages/lib/css/main.css">
    </head>
    <body class="body-style">
        <jsp:include page="/WEB-INF/views/common/Header.jsp" />
        
        <div class="page-container">
            <div class="page-header">
                <h1>🛒 Shopping Cart</h1>
            </div>

            <%@ page language="java" import="java.util.*"%>
            <%@ page language="java" import="com.p.bce.shopping.cart.rpc.pojo.CartItemDTO"%>
            <%@ page language="java" import="java.math.BigDecimal"%>

            <%
                String message = (String) request.getAttribute("message");
                String error = (String) request.getAttribute("error");
                @SuppressWarnings("unchecked")
                List<CartItemDTO> cartItems = (List<CartItemDTO>) request.getAttribute("cartItems");
                BigDecimal subtotal = (BigDecimal) request.getAttribute("subtotal");
                BigDecimal total = (BigDecimal) request.getAttribute("total");
                
                if (cartItems == null) {
                    cartItems = new ArrayList<>();
                }
                if (subtotal == null) {
                    subtotal = BigDecimal.ZERO;
                }
                if (total == null) {
                    total = BigDecimal.ZERO;
                }
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

            <% if (cartItems.isEmpty()) { %>
                <div class="card">
                    <div class="alert alert-info">
                        <strong>Your cart is empty!</strong>
                        <p>Start shopping to add items to your cart.</p>
                        <a href="/pages/html/postLogin/SearchCriteria.jsp" class="btn btn-primary">Browse Books</a>
                    </div>
                </div>
            <% } else { %>
                <div class="card">
                    <div class="table-container">
                        <table class="data-table">
                            <thead>
                                <tr>
                                    <th>Book</th>
                                    <th>Price</th>
                                    <th>Quantity</th>
                                    <th>Subtotal</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% for (CartItemDTO item : cartItems) { %>
                                    <tr>
                                        <td>
                                            <strong><%= item.getTitle() %></strong><br>
                                            <small>by <%= item.getAuthor() %></small><br>
                                            <small style="color: #6c757d;"><%= item.getPublisher() %> - <%= item.getEdition() %></small>
                                        </td>
                                        <td>₹<%= String.format("%.2f", item.getPrice()) %></td>
                                        <td>
                                            <form method="POST" action="/pages/html/postLogin/Cart/update" style="display: inline;">
                                                <input type="hidden" name="bookId" value="<%= item.getBookId() %>">
                                                <input type="number" name="quantity" value="<%= item.getQuantity() %>" 
                                                       min="1" max="<%= item.getAvailableQuantity() %>" 
                                                       style="width: 60px; padding: 5px;"
                                                       onchange="this.form.submit()">
                                                <small style="display: block; color: #6c757d; margin-top: 5px;">
                                                    Available: <%= item.getAvailableQuantity() %>
                                                </small>
                                            </form>
                                        </td>
                                        <td><strong>₹<%= String.format("%.2f", item.getSubtotal()) %></strong></td>
                                        <td>
                                            <form method="POST" action="/pages/html/postLogin/Cart/remove" 
                                                  style="display: inline;"
                                                  onsubmit="return confirm('Remove this item from cart?');">
                                                <input type="hidden" name="bookId" value="<%= item.getBookId() %>">
                                                <button type="submit" class="btn btn-danger" 
                                                        style="padding: 5px 15px; font-size: 0.9em;">
                                                    🗑️ Remove
                                                </button>
                                            </form>
                                        </td>
                                    </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>

                    <div style="margin-top: 30px; padding: 20px; background: #f8f9fa; border-radius: 8px;">
                        <div style="display: flex; justify-content: space-between; margin-bottom: 10px;">
                            <strong>Subtotal:</strong>
                            <strong>₹<%= String.format("%.2f", subtotal) %></strong>
                        </div>
                        <div style="display: flex; justify-content: space-between; margin-bottom: 20px;">
                            <strong>Total:</strong>
                            <strong style="font-size: 1.2em; color: #667eea;">₹<%= String.format("%.2f", total) %></strong>
                        </div>
                        <div class="btn-group">
                            <a href="/pages/html/postLogin/SearchCriteria.jsp" class="btn btn-secondary">
                                ← Continue Shopping
                            </a>
                            <form method="POST" action="/pages/html/postLogin/Cart/clear" 
                                  style="display: inline;"
                                  onsubmit="return confirm('Clear entire cart?');">
                                <button type="submit" class="btn btn-danger">Clear Cart</button>
                            </form>
                            <a href="/pages/html/postLogin/Checkout.jsp" class="btn btn-primary">
                                Proceed to Checkout →
                            </a>
                        </div>
                    </div>
                </div>
            <% } %>
        </div>
        
        <jsp:include page="/WEB-INF/views/common/Footer.jsp" />
    </body>
</html>

