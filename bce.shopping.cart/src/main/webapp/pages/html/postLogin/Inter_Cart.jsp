<%@ page language="java" %>

<%
    int i;
    Sring bookid=null;
    String user_src=null;
    user_src=(String)session.getValue("user");
    if(user_src!=null){
        int counter=0;
        counter=Integer.parseInt((String) session.getValue("ctr_val"));
        int ctr=0;
        for(i=1;i<=counter;i++){
            if(request.getParameter("chk"+i)!=null){
                bookid=request.getParameter("chk"+i);
                ctr=ctr+1;
                session.putValue("chk_var"+ctr,bookid);
            }
        }
        session.putValue("TotalSel",String.valueOf(ctr));
        response.sendRedirect("Cart.jsp");
    }else{
        response.sendRedirect("../preLogin/Unauthorised.html");
    }
%>