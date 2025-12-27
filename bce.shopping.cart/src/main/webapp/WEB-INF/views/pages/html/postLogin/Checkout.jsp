<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Checkout - BCE Shopping Cart</title>
        <link rel="stylesheet" href="/pages/lib/css/main.css">
    </head>
    <body class="body-style">
        <jsp:include page="/WEB-INF/views/common/Header.jsp" />
        
        <div class="page-container">
            <div class="page-header">
                <h1>💳 Checkout</h1>
            </div>

            <%@ page language="java" import="java.util.*"%>
            <%@ page language="java" import="com.p.bce.shopping.cart.rpc.pojo.CartItemDTO"%>
            <%@ page language="java" import="java.math.BigDecimal"%>

            <%
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

            <% if (cartItems.isEmpty()) { %>
                <div class="alert alert-error">
                    Your cart is empty. <a href="/pages/html/postLogin/SearchCriteria.jsp">Continue Shopping</a>
                </div>
            <% } else { %>
                <div style="display: grid; grid-template-columns: 2fr 1fr; gap: 30px;">
                    <!-- Order Summary -->
                    <div class="card">
                        <div class="card-header">Order Summary</div>
                        <div class="table-container">
                            <table class="data-table">
                                <thead>
                                    <tr>
                                        <th>Book</th>
                                        <th>Quantity</th>
                                        <th>Price</th>
                                        <th>Subtotal</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% for (CartItemDTO item : cartItems) { %>
                                        <tr>
                                            <td>
                                                <strong><%= item.getTitle() %></strong><br>
                                                <small>by <%= item.getAuthor() %></small>
                                            </td>
                                            <td><%= item.getQuantity() %></td>
                                            <td>₹<%= String.format("%.2f", item.getPrice()) %></td>
                                            <td><strong>₹<%= String.format("%.2f", item.getSubtotal()) %></strong></td>
                                        </tr>
                                    <% } %>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <!-- Order Total & Place Order -->
                    <div class="card">
                        <div class="card-header">Order Total</div>
                        <div style="padding: 20px;">
                            <div style="display: flex; justify-content: space-between; margin-bottom: 15px;">
                                <span>Subtotal:</span>
                                <span>₹<%= String.format("%.2f", subtotal) %></span>
                            </div>
                            <div style="display: flex; justify-content: space-between; margin-bottom: 15px;">
                                <span>Shipping:</span>
                                <span>Free</span>
                            </div>
                            <hr style="margin: 20px 0;">
                            <div style="display: flex; justify-content: space-between; margin-bottom: 20px;">
                                <strong>Total:</strong>
                                <strong style="font-size: 1.3em; color: #667eea;">₹<%= String.format("%.2f", total) %></strong>
                            </div>
                            
                            <form method="POST" action="/pages/html/postLogin/PlaceOrder.jsp">
                                <button type="submit" class="btn btn-primary" style="width: 100%; padding: 15px; font-size: 1.1em;">
                                    ✅ Place Order
                                </button>
                            </form>
                            
                            <a href="/pages/html/postLogin/Cart.jsp" class="btn btn-secondary" 
                               style="width: 100%; margin-top: 10px; padding: 15px;">
                                ← Back to Cart
                            </a>
                        </div>
                    </div>
                </div>
            <% } %>
        </div>
        
        <jsp:include page="/WEB-INF/views/common/Footer.jsp" />
    </body>
</html>

