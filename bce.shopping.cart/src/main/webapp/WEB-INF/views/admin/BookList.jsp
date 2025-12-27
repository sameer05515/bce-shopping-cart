<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Book Management - BCE Shopping Cart</title>
        <link rel="stylesheet" href="/pages/lib/css/main.css">
    </head>
    <body class="body-style">
        <jsp:include page="/WEB-INF/views/common/Header.jsp" />
        
        <div class="page-container">
            <div class="page-header">
                <h1>📚 Book Management</h1>
            </div>

            <%@ page language="java" import="java.util.*"%>
            <%@ page language="java" import="com.p.bce.shopping.cart.rpc.pojo.BookDetailDTO"%>
            <%@ page language="java" import="com.p.bce.shopping.cart.rpc.pojo.CategoryDetailsDTO"%>

            <%
                String message = (String) request.getAttribute("message");
                String error = (String) request.getAttribute("error");
                @SuppressWarnings("unchecked")
                List<BookDetailDTO> books = (List<BookDetailDTO>) request.getAttribute("books");
                @SuppressWarnings("unchecked")
                List<CategoryDetailsDTO> categories = (List<CategoryDetailsDTO>) request.getAttribute("categories");
                
                if (books == null) {
                    books = new ArrayList<>();
                }
                if (categories == null) {
                    categories = new ArrayList<>();
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

            <div class="card">
                <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
                    <div class="card-header" style="margin: 0;">All Books (<%= books.size() %>)</div>
                    <a href="/admin/books/add" class="btn btn-primary">➕ Add New Book</a>
                </div>

                <% if (books.isEmpty()) { %>
                    <div class="alert alert-info">
                        No books found. <a href="/admin/books/add">Add your first book</a>
                    </div>
                <% } else { %>
                    <div class="table-container">
                        <table class="data-table">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Title</th>
                                    <th>Author</th>
                                    <th>Publisher</th>
                                    <th>Category</th>
                                    <th>Price (₹)</th>
                                    <th>Stock</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    Map<Integer, String> categoryMap = new HashMap<>();
                                    for (CategoryDetailsDTO cat : categories) {
                                        categoryMap.put(cat.getCategoryId(), cat.getCategoryName());
                                    }
                                    
                                    for (BookDetailDTO book : books) {
                                        String categoryName = categoryMap.get(book.getCategoryId());
                                        if (categoryName == null) categoryName = "N/A";
                                %>
                                    <tr>
                                        <td><%= book.getBookId() %></td>
                                        <td><strong><%= book.getTitle() %></strong></td>
                                        <td><%= book.getAuthor() %></td>
                                        <td><%= book.getPublisher() %></td>
                                        <td><%= categoryName %></td>
                                        <td><strong style="color: #28a745;">₹<%= String.format("%.2f", book.getPrice()) %></strong></td>
                                        <td>
                                            <% if (book.getQuantity() <= 5) { %>
                                                <span style="color: #dc3545; font-weight: bold;"><%= book.getQuantity() %> (Low Stock)</span>
                                            <% } else { %>
                                                <%= book.getQuantity() %>
                                            <% } %>
                                        </td>
                                        <td>
                                            <div style="display: flex; gap: 10px;">
                                                <a href="/admin/books/edit?id=<%= book.getBookId() %>" 
                                                   class="btn btn-secondary" style="padding: 5px 15px; font-size: 0.9em;">
                                                    ✏️ Edit
                                                </a>
                                                <form method="POST" action="/admin/books/delete" 
                                                      style="display: inline;"
                                                      onsubmit="return confirm('Are you sure you want to delete this book?');">
                                                    <input type="hidden" name="id" value="<%= book.getBookId() %>">
                                                    <button type="submit" class="btn btn-danger" 
                                                            style="padding: 5px 15px; font-size: 0.9em;">
                                                        🗑️ Delete
                                                    </button>
                                                </form>
                                            </div>
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

