<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <%@ page language="java" import="com.p.bce.shopping.cart.rpc.pojo.BookDetailDTO"%>
        <%
            BookDetailDTO bookForTitle = (BookDetailDTO) request.getAttribute("book");
            String pageTitle = (bookForTitle != null && bookForTitle.getBookId() > 0) ? "Edit Book" : "Add Book";
        %>
        <title><%= pageTitle %> - BCE Shopping Cart</title>
        <link rel="stylesheet" href="/pages/lib/css/main.css">
    </head>
    <body class="body-style">
        <div class="page-container">
            <div class="page-header">
                <h1><%= (bookForTitle != null && bookForTitle.getBookId() > 0) ? "✏️ Edit Book" : "➕ Add New Book" %></h1>
            </div>

            <div class="nav-bar">
                <div class="nav-links">
                    <a href="/admin/books">← Back to Books</a>
                    <a href="/admin">🏠 Dashboard</a>
                    <a href="/pages/html/postLogin/Logout.jsp">🚪 Logout</a>
                </div>
            </div>

            <%@ page language="java" import="com.p.bce.shopping.cart.rpc.pojo.CategoryDetailsDTO"%>
            <%@ page language="java" import="java.util.*"%>

            <%
                BookDetailDTO book = bookForTitle;
                if (book == null) {
                    book = new BookDetailDTO();
                }
                @SuppressWarnings("unchecked")
                List<CategoryDetailsDTO> categories = (List<CategoryDetailsDTO>) request.getAttribute("categories");
                if (categories == null) {
                    categories = new ArrayList<>();
                }
            %>

            <form method="POST" action="/admin/books/save" class="card">
                <% if (book.getBookId() > 0) { %>
                    <input type="hidden" name="bookId" value="<%= book.getBookId() %>">
                <% } %>

                <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px;">
                    <div class="form-group" style="grid-column: 1 / -1;">
                        <label for="title" class="required">Book Title</label>
                        <input type="text" id="title" name="title" class="form-control" 
                               value="<%= book.getTitle() != null ? book.getTitle() : "" %>" 
                               placeholder="Enter book title" required>
                    </div>

                    <div class="form-group">
                        <label for="author" class="required">Author</label>
                        <input type="text" id="author" name="author" class="form-control" 
                               value="<%= book.getAuthor() != null ? book.getAuthor() : "" %>" 
                               placeholder="Enter author name" required>
                    </div>

                    <div class="form-group">
                        <label for="publisher" class="required">Publisher</label>
                        <input type="text" id="publisher" name="publisher" class="form-control" 
                               value="<%= book.getPublisher() != null ? book.getPublisher() : "" %>" 
                               placeholder="Enter publisher name" required>
                    </div>

                    <div class="form-group">
                        <label for="edition" class="required">Edition</label>
                        <input type="text" id="edition" name="edition" class="form-control" 
                               value="<%= book.getEdition() != null ? book.getEdition() : "" %>" 
                               placeholder="e.g., 1st Edition, 2nd Edition" required>
                    </div>

                    <div class="form-group">
                        <label for="categoryId" class="required">Category</label>
                        <select id="categoryId" name="categoryId" class="form-control" required>
                            <option value="">Select Category</option>
                            <% for (CategoryDetailsDTO cat : categories) { %>
                                <option value="<%= cat.getCategoryId() %>" 
                                        <%= book.getCategoryId() == cat.getCategoryId() ? "selected" : "" %>>
                                    <%= cat.getCategoryName() %>
                                </option>
                            <% } %>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="price" class="required">Price (₹)</label>
                        <input type="number" id="price" name="price" class="form-control" 
                               value="<%= book.getPrice() > 0 ? book.getPrice() : "" %>" 
                               placeholder="0.00" step="0.01" min="0" required>
                    </div>

                    <div class="form-group">
                        <label for="quantity" class="required">Quantity (Stock)</label>
                        <input type="number" id="quantity" name="quantity" class="form-control" 
                               value="<%= book.getQuantity() > 0 ? book.getQuantity() : "" %>" 
                               placeholder="0" min="0" required>
                    </div>

                    <div class="form-group" style="grid-column: 1 / -1;">
                        <label for="description">Description</label>
                        <textarea id="description" name="description" class="form-control" 
                                  rows="5" placeholder="Enter book description"><%= book.getDescription() != null ? book.getDescription() : "" %></textarea>
                    </div>
                </div>

                <div class="btn-group">
                    <button type="submit" class="btn btn-primary">
                        💾 <%= book.getBookId() > 0 ? "Update Book" : "Create Book" %>
                    </button>
                    <a href="/admin/books" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        </div>
    </body>
</html>

