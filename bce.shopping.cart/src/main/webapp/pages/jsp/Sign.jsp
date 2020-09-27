<html>
    <head>        
        <link rel="stylesheet" href="../lib/css/main.css">
    </head>
    <body class="body-style">
        <%@ page language="java" import="com.p.bce.shopping.cart.rpc.ShoppingApiRPC"%>
        <%@ page language="java" import="com.p.bce.shopping.cart.util.ShoppingCartUtil"%>
        <%@ page language="java" import="com.p.bce.shopping.cart.rpc.pojo.UserProfileDTO"%>

        <%
            UserProfileDTO dto=ShoppingCartUtil.getUserProfileDTOFromRequest(request);
            boolean exists=ShoppingApiRPC.userNameExists(dto);
            if(exists){%>
        <script language="javascript">
        alert("User Name already exists");
        location.href="NewUser.html";
        </script>
            <% } else{
                ShoppingApiRPC.saveUserProfile(dto);
            }          
        %>
        
    </body>
</html>