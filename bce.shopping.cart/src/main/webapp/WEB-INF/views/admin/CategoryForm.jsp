<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <%@ page language="java" import="com.p.bce.shopping.cart.rpc.pojo.CategoryDetailsDTO"%>
        <%
            CategoryDetailsDTO categoryForTitle = (CategoryDetailsDTO) request.getAttribute("category");
            String pageTitle = (categoryForTitle != null && categoryForTitle.getCategoryId() > 0) ? "Edit Category" : "Add Category";
        %>
        <title><%= pageTitle %> - BCE Shopping Cart</title>
        <link rel="stylesheet" href="/pages/lib/css/main.css">
    </head>
    <body class="body-style">
        <div class="page-container">
            <div class="page-header">
                <h1><%= (categoryForTitle != null && categoryForTitle.getCategoryId() > 0) ? "✏️ Edit Category" : "➕ Add New Category" %></h1>
            </div>

            <div class="nav-bar">
                <div class="nav-links">
                    <a href="/admin/categories">← Back to Categories</a>
                    <a href="/admin">🏠 Dashboard</a>
                    <a href="/pages/html/postLogin/Logout.jsp">🚪 Logout</a>
                </div>
            </div>

            <%
                CategoryDetailsDTO category = categoryForTitle;
                if (category == null) {
                    category = new CategoryDetailsDTO();
                }
            %>

            <form method="POST" action="/admin/categories/save" class="card">
                <% if (category.getCategoryId() > 0) { %>
                    <input type="hidden" name="categoryId" value="<%= category.getCategoryId() %>">
                <% } %>

                <div class="form-group">
                    <label for="categoryName" class="required">Category Name</label>
                    <input type="text" id="categoryName" name="categoryName" class="form-control" 
                           value="<%= category.getCategoryName() != null ? category.getCategoryName() : "" %>" 
                           placeholder="Enter category name" required>
                    <small style="color: #6c757d; margin-top: 5px; display: block;">
                        Category names must be unique.
                    </small>
                </div>

                <div class="btn-group">
                    <button type="submit" class="btn btn-primary">
                        💾 <%= category.getCategoryId() > 0 ? "Update Category" : "Create Category" %>
                    </button>
                    <a href="/admin/categories" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        </div>
    </body>
</html>

