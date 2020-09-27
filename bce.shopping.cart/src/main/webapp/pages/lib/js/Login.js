function validate(){
    if(document.loginFrm.UserName.value==""){
        alert("UserName can not be blank");
        return;
    }else if(document.loginFrm.Password.value==""){
        alert("Password can not be blank");
        return;
    }else{
        //document.loginFrm.action="http://127.0.0.1:8080/ShoppingCart/pages/jsp/Validate.jsp";
        document.loginFrm.action="http://127.0.0.1:8080/ShoppingCart/pages/jsp/Validate.jsp";
        document.loginFrm.submit();
    }

}