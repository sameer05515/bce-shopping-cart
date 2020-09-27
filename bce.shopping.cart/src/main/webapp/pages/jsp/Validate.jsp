<html>
    <body>
        <%@ page language="java" import="com.p.bce.shopping.cart.rpc.ShoppingApiRPC"%>
        <%@ page language="java" import="com.p.bce.shopping.cart.util.ShoppingCartUtil"%>
        <%@ page language="java" import="com.p.bce.shopping.cart.rpc.pojo.UserAuthDTO"%>
    </body>
    <%
        UserAuthDTO userAuth=ShoppingCartUtil.getUserAuthDTOFromRequest(request);
        userAuth=ShoppingApiRPC.validateUserAuth(userAuth);
        if(userAuth==null){
            response.sendRedirect("../html/preLogin/InvalidUser.html");
        }else{
            session.putValue("user",userAuth.getUserName());
            if("Administrator".equalsIgnoreCase(userAuth.getUserName())){
                response.sendRedirect("../html/postLogin/Admin.html");
            }else{
                response.sendRedirect("../html/postLogin/SearchCriteria.jsp");
            }
        }
    %>
</html>