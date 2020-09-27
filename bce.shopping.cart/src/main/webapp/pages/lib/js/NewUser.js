function validate(){
    if(document.newUserFrm.UserName.value==""){
        alert("UserName can not be blank!!");
        return;
    }else if(document.newUserFrm.Password.value==""){
        alert("Password can not be blank!!");
        return;
    }else if(document.newUserFrm.Password.value==""){
        alert("Password can not be blank!!");
        return;
    }else if(document.newUserFrm.Password.value!=document.newUserFrm.Password2.value){
        alert("The two passwords do not match!!");
        return;
    }else if(document.newUserFrm.FirstName.value==""){
        alert("FirstName can not be blank!!");
        return;
    }else if(document.newUserFrm.LastName.value==""){
        alert("LastName can not be blank!!");
        return;
    }else if(document.newUserFrm.Address1.value==""){
        alert("Address can not be blank!!");
        return;
    }else if(document.newUserFrm.City.value==""){
        alert("City can not be blank!!");
        return;
    }else if(document.newUserFrm.State.value=="Select State"){
        alert("You must select a state!!");
        return;
    }else if(document.newUserFrm.PinCode.value==""){
        alert("PinCode can not be blank!!");
        return;
    }else if(document.newUserFrm.Email.value==""){
        alert("Email ID can not be blank!!");
        return;
    }else if(document.newUserFrm.Phone.value==""){
        alert("Phone Number can not be blank!!");
        return;
    }else{
        document.forms[0].action="http://127.0.0.1:8080/ShoppingCart/pages/jsp/Sign.jsp";
        document.forms[0].submit();
    }

}