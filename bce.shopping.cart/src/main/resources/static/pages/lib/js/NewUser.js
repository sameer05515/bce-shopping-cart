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
        // Password strength validation
        var password = document.newUserFrm.Password.value;
        
        if(password.length < 8){
            alert("Password must be at least 8 characters long!!");
            return;
        }
        
        var hasUpperCase = /[A-Z]/.test(password);
        var hasLowerCase = /[a-z]/.test(password);
        var hasDigit = /\d/.test(password);
        var hasSpecialChar = /[@$!%*?&]/.test(password);
        
        if(!hasUpperCase){
            alert("Password must contain at least one uppercase letter!!");
            return;
        }
        
        if(!hasLowerCase){
            alert("Password must contain at least one lowercase letter!!");
            return;
        }
        
        if(!hasDigit){
            alert("Password must contain at least one digit!!");
            return;
        }
        
        if(!hasSpecialChar){
            alert("Password must contain at least one special character (@$!%*?&)!!");
            return;
        }
        
        document.forms[0].action="/pages/jsp/Sign.jsp";
        document.forms[0].submit();
    }

}
