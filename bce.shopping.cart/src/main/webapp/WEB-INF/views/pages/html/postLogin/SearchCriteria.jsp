<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Search Books - BCE Shopping Cart</title>
        <link rel="stylesheet" href="/pages/lib/css/main.css">
        <script src="/pages/lib/js/SearchItems.js"></script>
    </head>

    <body class="body-style">
        <div class="page-container">
            <div class="page-header">
                <h1>🔍 Search Books</h1>
                <h2>Find Your Favorite Books</h2>
            </div>

            <div class="nav-bar">
                <div class="nav-links">
                    <a href="/pages/html/postLogin/Profile.jsp">👤 My Profile</a>
                    <a href="/pages/html/postLogin/Logout.jsp">🚪 Logout</a>
                </div>
            </div>

            <%@ page language="java" import="com.p.bce.shopping.cart.rpc.pojo.CategoryDetailsDTO"%>
            <%@ page language="java" import="java.util.*"%>

            <%
            @SuppressWarnings("unchecked")
            List<CategoryDetailsDTO> listCategDet = (List<CategoryDetailsDTO>) request.getAttribute("categories");
            if (listCategDet == null) {
                listCategDet = new ArrayList<>();
            }
            %>

            <form name="searchItemsFrm" method="POST" action="/pages/html/postLogin/Search.jsp" class="search-form">
                <div class="card-header">Select Search Criteria</div>

                <div class="radio-group">
                    <input type="radio" id="searchCategory" value="Category" checked name="R1">
                    <label for="searchCategory"><b>Search By Category</b></label>
                    <select name="BookCategory" class="form-control" style="max-width: 300px; margin-left: 20px;">
                        <option value="Select" selected>Select Category</option>
                        <%
                        for(CategoryDetailsDTO d:listCategDet){
                            %>
                            <option value="<%=d.getCategoryName()%>">
                                <%=d.getCategoryName()%>
                            </option>
                            <%
                        }
                        %>
                    </select>
                </div>

                <div class="radio-group">
                    <input type="radio" id="searchTitle" value="Title" name="R1">
                    <label for="searchTitle"><b>Search By Book Title</b></label>
                    <input type="text" name="BookTitle" class="form-control" 
                           placeholder="Enter book title" style="max-width: 300px; margin-left: 20px;">
                </div>

                <div class="radio-group">
                    <input type="radio" id="searchAuthor" value="Author" name="R1">
                    <label for="searchAuthor"><b>Search By Book Author</b></label>
                    <input type="text" name="BookAuthor" class="form-control" 
                           placeholder="Enter author name" style="max-width: 300px; margin-left: 20px;">
                </div>

                <div class="radio-group">
                    <input type="radio" id="searchPublisher" value="Publisher" name="R1">
                    <label for="searchPublisher"><b>Search By Book Publisher</b></label>
                    <input type="text" name="BookPublisher" class="form-control" 
                           placeholder="Enter publisher name" style="max-width: 300px; margin-left: 20px;">
                </div>

                <div class="btn-group">
                    <button type="button" class="btn btn-primary" onclick="startSearch();">
                        🔍 Search
                    </button>
                </div>
            </form>
        </div>
    </body>
</html>
