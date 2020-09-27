function validate(){
    if(document.frm.UserName.value==""){
        alert("UserName can not be blank");
        return;
    }else if(document.frm.Password.value==""){
        alert("Password can not be blank");
        return;
    }else{
        document.frm.action="Validate.jsp";
        document.frm.submit();
    }

}