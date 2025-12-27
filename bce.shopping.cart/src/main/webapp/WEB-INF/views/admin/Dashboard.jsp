<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin Dashboard - BCE Shopping Cart</title>
        <link rel="stylesheet" href="/pages/lib/css/main.css">
    </head>
    <body class="body-style">
        <jsp:include page="/WEB-INF/views/common/Header.jsp" />
        
        <div class="page-container">
            <div class="page-header">
                <h1>⚙️ Admin Dashboard</h1>
            </div>

            <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 20px; margin-top: 30px;">
                <div class="card">
                    <div class="card-header">📚 Total Books</div>
                    <div style="font-size: 3em; color: #667eea; text-align: center; padding: 20px;">
                        ${totalBooks}
                    </div>
                    <div style="text-align: center;">
                        <a href="/admin/books" class="btn btn-primary">Manage Books</a>
                    </div>
                </div>

                <div class="card">
                    <div class="card-header">📁 Total Categories</div>
                    <div style="font-size: 3em; color: #667eea; text-align: center; padding: 20px;">
                        ${totalCategories}
                    </div>
                    <div style="text-align: center;">
                        <a href="/admin/categories" class="btn btn-primary">Manage Categories</a>
                    </div>
                </div>
            </div>

            <div class="card" style="margin-top: 30px;">
                <div class="card-header">Quick Actions</div>
                <div class="btn-group" style="flex-wrap: wrap;">
                    <a href="/admin/books/add" class="btn btn-success">➕ Add New Book</a>
                    <a href="/admin/categories/add" class="btn btn-success">➕ Add New Category</a>
                    <a href="/admin/books" class="btn btn-secondary">📋 View All Books</a>
                    <a href="/admin/categories" class="btn btn-secondary">📋 View All Categories</a>
                </div>
            </div>
        </div>
        
        <jsp:include page="/WEB-INF/views/common/Footer.jsp" />
    </body>
</html>

