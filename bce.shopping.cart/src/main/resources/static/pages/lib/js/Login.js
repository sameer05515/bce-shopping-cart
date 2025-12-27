function validate(){
    if(document.loginFrm.UserName.value==""){
        alert("UserName can not be blank");
        return;
    }else if(document.loginFrm.Password.value==""){
        alert("Password can not be blank");
        return;
    }else{
        document.loginFrm.action="/pages/jsp/Validate.jsp";
        document.loginFrm.submit();
    }

}
