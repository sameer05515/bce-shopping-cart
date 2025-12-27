<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Search Results - BCE Shopping Cart</title>
        <link rel="stylesheet" href="/pages/lib/css/main.css">
        <script src="/pages/lib/js/SearchItems.js"></script>
    </head>
    <body class="body-style">
        <div class="page-container">
            <div class="page-header">
                <h1>📚 Search Results</h1>
            </div>

            <div class="nav-bar">
                <div class="nav-links">
                    <a href="/pages/html/postLogin/SearchCriteria.jsp">🔍 New Search</a>
                    <a href="/pages/html/postLogin/Profile.jsp">👤 My Profile</a>
                    <a href="/pages/html/postLogin/Logout.jsp">🚪 Logout</a>
                </div>
            </div>

            <%@ page language="java" import="java.util.*"%>
            <%@ page language="java" import="com.p.bce.shopping.cart.rpc.pojo.SearchedBookCategories"%>
            <%@ page language="java" import="com.p.bce.shopping.cart.rpc.pojo.CategoryDetailsDTO"%>

            <%
                String user_src = (String) session.getAttribute("user");
                @SuppressWarnings("unchecked")
                List<SearchedBookCategories> listSearchedBookCategories = 
                    (List<SearchedBookCategories>) request.getAttribute("searchResults");
                @SuppressWarnings("unchecked")
                List<CategoryDetailsDTO> listCategDet = 
                    (List<CategoryDetailsDTO>) request.getAttribute("categories");
                Integer counter = (Integer) request.getAttribute("counter");
                
                if (listSearchedBookCategories == null) {
                    listSearchedBookCategories = new ArrayList<>();
                }
                if (listCategDet == null) {
                    listCategDet = new ArrayList<>();
                }
                if (counter == null) {
                    counter = 0;
                }
                
                if(user_src != null){
                    if(listSearchedBookCategories.isEmpty()) {
            %>
                        <div class="alert alert-info">
                            <strong>No Results Found:</strong> Please try a different search criteria.
                        </div>
            <%
                    } else {
            %>
                        <div class="card">
                            <div class="card-header">
                                Found <%= listSearchedBookCategories.size() %> Book(s)
                            </div>
                            
                            <form action="/pages/html/postLogin/Inter_Cart.jsp" name="cart">
                                <div class="table-container">
                                    <table class="data-table">
                                        <thead>
                                            <tr>
                                                <th>S.No</th>
                                                <th>Select</th>
                                                <th>Book Name</th>
                                                <th>Author Name</th>
                                                <th>Publisher</th>
                                                <th>Edition</th>
                                                <th>Price (₹)</th>
                                                <th>Quantity Available</th>
                                                <th>Description</th>
                                                <th>Category</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%
                                                int count = 0;
                                                
                                                for(SearchedBookCategories s : listSearchedBookCategories){
                                                    count++;
                                            %>
                                                    <tr>
                                                        <td><%= count %></td>
                                                        <td>
                                                            <input type="checkbox" name="chk<%= count %>" 
                                                                   value="<%= s.getBookId() %>">
                                                        </td>
                                                        <td><strong><%= s.getTitle() %></strong></td>
                                                        <td><%= s.getAuthor() %></td>
                                                        <td><%= s.getPublisher() %></td>
                                                        <td><%= s.getEdition() %></td>
                                                        <td><strong style="color: #28a745;">₹<%= s.getPrice() %></strong></td>
                                                        <td><%= s.getQuantity() %></td>
                                                        <td><%= s.getDescription() %></td>
                                                        <td><%= s.getCategoryName() %></td>
                                                    </tr>
                                            <%
                                                }
                                                session.setAttribute("ctr_val", String.valueOf(count));
                                            %>
                                        </tbody>
                                    </table>
                                </div>
                                
                                <div class="btn-group">
                                    <button type="submit" class="btn btn-success">
                                        🛒 Add Selected to Cart
                                    </button>
                                </div>
                            </form>
                        </div>

                        <hr style="margin: 30px 0; border: none; border-top: 2px solid #e0e0e0;">

                        <div class="card">
                            <div class="card-header">🔍 Search More Books</div>
                            
                            <form action="/pages/html/postLogin/Search.jsp" name="newsrc" method="POST" class="search-form">
                                <div class="radio-group">
                                    <input type="radio" id="newSearchCategory" name="R1" value="Category" checked>
                                    <label for="newSearchCategory">Search By Category</label>
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
                                    <input type="radio" id="newSearchTitle" name="R1" value="Title">
                                    <label for="newSearchTitle">Search By Book Title</label>
                                    <input type="text" name="BookTitle" class="form-control" 
                                           placeholder="Enter book title" style="max-width: 300px; margin-left: 20px;">
                                </div>

                                <div class="radio-group">
                                    <input type="radio" id="newSearchAuthor" name="R1" value="Author">
                                    <label for="newSearchAuthor">Search By Book Author</label>
                                    <input type="text" name="BookAuthor" class="form-control" 
                                           placeholder="Enter author name" style="max-width: 300px; margin-left: 20px;">
                                </div>

                                <div class="radio-group">
                                    <input type="radio" id="newSearchPublisher" name="R1" value="Publisher">
                                    <label for="newSearchPublisher">Search By Book Publisher</label>
                                    <input type="text" name="BookPublisher" class="form-control" 
                                           placeholder="Enter publisher name" style="max-width: 300px; margin-left: 20px;">
                                </div>

                                <div class="radio-group">
                                    <input type="radio" id="advancedSearch" name="R2" value="A">
                                    <label for="advancedSearch">Advanced Search</label>
                                    <input type="radio" id="newSearch" name="R2" value="N" checked style="margin-left: 20px;">
                                    <label for="newSearch">New Search</label>
                                </div>

                                <div class="btn-group">
                                    <button type="submit" class="btn btn-primary">🔍 Search</button>
                                </div>
                            </form>
                        </div>
            <%
                    }
                } else {
                    response.sendRedirect("/pages/html/preLogin/Unauthorised.html");
                }
            %>
        </div>
    </body>
</html>
