<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Category Management - BCE Shopping Cart</title>
        <link rel="stylesheet" href="/pages/lib/css/main.css">
    </head>
    <body class="body-style">
        <div class="page-container">
            <div class="page-header">
                <h1>📁 Category Management</h1>
            </div>

            <div class="nav-bar">
                <div class="nav-links">
                    <a href="/admin">🏠 Dashboard</a>
                    <a href="/admin/categories/add">➕ Add Category</a>
                    <a href="/admin/books">📚 Books</a>
                    <a href="/pages/html/postLogin/SearchCriteria.jsp">🔍 Customer View</a>
                    <a href="/pages/html/postLogin/Logout.jsp">🚪 Logout</a>
                </div>
            </div>

            <%@ page language="java" import="java.util.*"%>
            <%@ page language="java" import="com.p.bce.shopping.cart.rpc.pojo.CategoryDetailsDTO"%>

            <%
                String message = (String) request.getAttribute("message");
                String error = (String) request.getAttribute("error");
                @SuppressWarnings("unchecked")
                List<CategoryDetailsDTO> categories = (List<CategoryDetailsDTO>) request.getAttribute("categories");
                
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
                    <div class="card-header" style="margin: 0;">All Categories (<%= categories.size() %>)</div>
                    <a href="/admin/categories/add" class="btn btn-primary">➕ Add New Category</a>
                </div>

                <% if (categories.isEmpty()) { %>
                    <div class="alert alert-info">
                        No categories found. <a href="/admin/categories/add">Add your first category</a>
                    </div>
                <% } else { %>
                    <div class="table-container">
                        <table class="data-table">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Category Name</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% for (CategoryDetailsDTO category : categories) { %>
                                    <tr>
                                        <td><%= category.getCategoryId() %></td>
                                        <td><strong><%= category.getCategoryName() %></strong></td>
                                        <td>
                                            <div style="display: flex; gap: 10px;">
                                                <a href="/admin/categories/edit?id=<%= category.getCategoryId() %>" 
                                                   class="btn btn-secondary" style="padding: 5px 15px; font-size: 0.9em;">
                                                    ✏️ Edit
                                                </a>
                                                <form method="POST" action="/admin/categories/delete" 
                                                      style="display: inline;"
                                                      onsubmit="return confirm('Are you sure you want to delete this category? This will fail if any books use this category.');">
                                                    <input type="hidden" name="id" value="<%= category.getCategoryId() %>">
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
    </body>
</html>

