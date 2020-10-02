<html>
    <head>
        <title>Search Results</title>
        <link rel="stylesheet" href="../../lib/css/main.css">
        <script src="../../lib/js/SearchItems.js"></script>
    </head>
    <body class="body-style">
        <h1 style="text-align: center;"><u>ONLINE SHOPPING CART</u></h1>
        <br>
        <a href="Logout.jsp">
            <font size="4">Click here to Logout.</font>
        </a>

        <h1 style="text-align: center;"><u>ONLINE SHOPPING CART</u></h1>
        <%@ page language="java" import="com.p.bce.shopping.cart.rpc.ShoppingApiRPC"%>
        <%@ page language="java" import="com.p.bce.shopping.cart.util.ShoppingCartUtil"%>
        <%@ page language="java" import="java.util.*"%>
        <%@ page language="java" import="com.p.bce.shopping.cart.rpc.pojo.SearchedBookCategories"%>
        <%@ page language="java" import="com.p.bce.shopping.cart.rpc.pojo.CategoryDetailsDTO"%>
        <%@ page language="java" import="java.sql.*"%>


        <%
            String str_searchvar=null;
            int chk_ctr=0;
            String str_colvar=null;
            String tab_var="";
            String user_src=(String) session.getValue("user");
            String R1=request.getParameter("R1");
            String R2=request.getParameter("R2");
            if(user_src!=null){
                

                if(request.getParameter("R1").equals("Title")){
                    str_searchvar=request.getParameter("BookTitle");
                    str_colvar="Title";
                }

                if(request.getParameter("R1").equals("Category")){
                    chk_ctr=1;
                    str_searchvar=request.getParameter("BookCategory");
                    str_colvar="CategoryName";
                }

                if(request.getParameter("R1").equals("Publisher")){
                    str_searchvar=request.getParameter("BookPublisher");
                    str_colvar="Publisher";
                }

                if(request.getParameter("R1").equals("Author")){
                    str_searchvar=request.getParameter("BookAuthor");
                    str_colvar="Author";
                }

                if(request.getParameter("R1")==null){
                    tab_var="BOOK_DETAILS";
                }else{
                    // to get the name of the table in which the search to be done ---
                    if(request.getParameter("R2").equals("A")){
                        tab_var="TEMP_DETAIL";
                    }else{
                        tab_var="BOOK_DETAILS";
                    }
                }
                List<SearchedBookCategories> listSearchedBookCategories
                    =ShoppingApiRPC.getSearchedBookCategories(str_searchvar, str_colvar, chk_ctr, tab_var);
                    %>
                    <form action="Inter_Cart.jsp" name="cart">
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
                                int counter=0;
                                
                                for(SearchedBookCategories s:listSearchedBookCategories ){
                                	counter++;
                                    out.println("<tr>");
                                    out.println("<td>"+(counter)+"</td>");                                    
                                    out.println("<td>"+"<input type=\"checkbox\" name=\"chk"+counter+"\" value=\""+s.getBookId()+"\">"+" </td>");
                                    out.println("<td>"+(s.getTitle())+"</td>");
                                    out.println("<td>"+(s.getAuthor())+"</td>");
                                    out.println("<td>"+(s.getPublisher())+"</td>");
                                    out.println("<td>"+(s.getEdition())+"</td>");
                                    out.println("<td>"+(s.getPrice())+"</td>");
                                    out.println("<td>"+(s.getQuantity())+"</td>");
                                    out.println("<td>"+(s.getDescription())+"</td>");
                                    out.println("<td>"+(s.getCategoryName())+"</td>");
                                    out.println("</tr>");
                                    int rs_ins=ShoppingApiRPC.insertIntoTempDetails(s);
                                }
                                session.putValue("ctr_val", String.valueOf(counter));
                                
                                List<CategoryDetailsDTO> listCategDet=ShoppingApiRPC.getAllCategoryDetails();
                                
                            %>
                        </table>
                        <br>
                        <input type="submit" value=" Add to Cart " name="Add">
                    </form>

                    <hr>

                    <p><font size="5">Search More</font></p>
                    <form action="Search.jsp" name="newsrc" method="POST">
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
                response.sendRedirect("../preLogin/Unauthorised.html");
            }

        %>
    </body>
    
</html>