<html>
    <head>
        <title>Search Items</title>
        <link rel="stylesheet" href="/pages/lib/css/main.css">
        <script src="/pages/lib/js/SearchItems.js"></script>
    </head>

    <body class="body-style">
        <h1 style="text-align: center;"><u>ONLINE SHOPPING CART</u></h1>
        <%@ page language="java" import="com.p.bce.shopping.cart.rpc.pojo.CategoryDetailsDTO"%>
        <%@ page language="java" import="java.util.*"%>

        <%
        @SuppressWarnings("unchecked")
        List<CategoryDetailsDTO> listCategDet = (List<CategoryDetailsDTO>) request.getAttribute("categories");
        if (listCategDet == null) {
            listCategDet = new ArrayList<>();
        }
        %>

        <form name="searchItemsFrm" method="POST" action="/pages/html/postLogin/Search.jsp">
            <span style="text-align: center;">Search Books</span>

            <table>
                <tr>
                    <td width="17%" height="25">
                        <input type="radio" value="Category" checked name="R1">
                    </td>
                    <td width="32%" height="25">Search By Category</td>
                    <td width="51%" height="25">
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
                    <td width="17%" height="25">
                        <input type="radio" value="Title" name="R1">
                    </td>
                    <td width="32%" height="25">Search By Book Title</td>
                    <td width="51%" height="25">
                        <input type="text" name="BookTitle" size="25">
                    </td>
                </tr>

                <tr>
                    <td width="17%" height="25">
                        <input type="radio" value="Author" name="R1">
                    </td>
                    <td width="32%" height="25">Search By Book Author</td>
                    <td width="51%" height="25">
                        <input type="text" name="BookAuthor" size="25">
                    </td>
                </tr>

                <tr>
                    <td width="17%" height="25">
                        <input type="radio" value="Publisher" name="R1">
                    </td>
                    <td width="32%" height="25">Search By Book Publisher</td>
                    <td width="51%" height="25">
                        <input type="text" name="BookPublisher" size="25">
                    </td>
                </tr>

                <tr>
                    <td width="17%" height="25">
                        
                    </td>
                    <td width="32%" height="25">
                        <input type="button" value="Search" name="Search" onclick="startSearch();">
                    </td>
                    <td width="51%" height="25">
                        Click <a href="/pages/html/postLogin/Logout.jsp">here</a> to logout.
                    </td>
                </tr>
            </table>

        </form>

    </body>
</html>

