<html>
    <head>
        <title>Search Results</title>
        <link rel="stylesheet" href="/pages/lib/css/main.css">
        <script src="/pages/lib/js/SearchItems.js"></script>
    </head>
    <body class="body-style">
        <h1 style="text-align: center;"><u>ONLINE SHOPPING CART</u></h1>
        <br>
        <a href="/pages/html/postLogin/Logout.jsp">
            <font size="4">Click here to Logout.</font>
        </a>

        <h1 style="text-align: center;"><u>ONLINE SHOPPING CART</u></h1>
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
                    %>
                    <form action="/pages/html/postLogin/Inter_Cart.jsp" name="cart">
                        <table border="1">
                            <tr>
                                <td>S.No</td>
                                <td>Select</td>
                                <td>Book Name</td>
                                <td>Author Name</td>
                                <td>Publisher</td>
                                <td>Edition</td>
                                <td>Price (in Rs.)</td>
                                <td>Quantity Available (Nos.)</td>
                                <td>Description</td>
                                <td>Category</td>
                            </tr>
                            <%
                                int count = 0;
                                
                                for(SearchedBookCategories s : listSearchedBookCategories){
                                	count++;
                                    out.println("<tr>");
                                    out.println("<td>" + count + "</td>");                                    
                                    out.println("<td>" + "<input type=\"checkbox\" name=\"chk" + count + "\" value=\"" + s.getBookId() + "\">" + " </td>");
                                    out.println("<td>" + (s.getTitle()) + "</td>");
                                    out.println("<td>" + (s.getAuthor()) + "</td>");
                                    out.println("<td>" + (s.getPublisher()) + "</td>");
                                    out.println("<td>" + (s.getEdition()) + "</td>");
                                    out.println("<td>" + (s.getPrice()) + "</td>");
                                    out.println("<td>" + (s.getQuantity()) + "</td>");
                                    out.println("<td>" + (s.getDescription()) + "</td>");
                                    out.println("<td>" + (s.getCategoryName()) + "</td>");
                                    out.println("</tr>");
                                }
                                session.setAttribute("ctr_val", String.valueOf(count));
                                
                            %>
                        </table>
                        <br>
                        <input type="submit" value=" Add to Cart " name="Add">
                    </form>

                    <hr>

                    <p><font size="5">Search More</font></p>
                    <form action="/pages/html/postLogin/Search.jsp" name="newsrc" method="POST">
                        <table border="1">
                            <tr>
                                <td>
                                    <input type="radio" name="R1" value="Category" checked>
                                </td>

                                <td>
                                    <select name="BookCategory" size="1">
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
                                </td>
                            </tr>

                            <tr>
                                <td>
                                    <input type="radio" name="R1" value="Title">
                                </td>
                                <td>
                                    Search by Book Title
                                </td>
                                <td><input type="text" name="BookTitle" size="25"></td>
                            </tr>

                            <tr>
                                <td>
                                    <input type="radio" name="R1" value="Author">
                                </td>
                                <td>
                                    Search by Book Author
                                </td>
                                <td><input type="text" name="BookAuthor" size="25"></td>
                            </tr>

                            <tr>
                                <td>
                                    <input type="radio" name="R1" value="Publisher">
                                </td>
                                <td>
                                    Search by Book Publisher
                                </td>
                                <td><input type="text" name="BookPublisher" size="25"></td>
                            </tr>

                            <tr>
                                <td>
                                    <input type="radio" name="R2" value="A"> Advanced Search
                                </td>
                                <td>
                                    <input type="radio" name="R2" value="N" checked> New Search
                                </td>
                                <td>
                                    <input type="submit" value="Search">
                                </td>
                            </tr>
                        </table>

                    </form>
                    <%
            }else{
                response.sendRedirect("/pages/html/preLogin/Unauthorised.html");
            }

        %>
    </body>
    
</html>

