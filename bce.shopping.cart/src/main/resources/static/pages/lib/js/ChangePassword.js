function validate(){
    if(document.changePasswordFrm.CurrentPassword.value==""){
        alert("Current Password can not be blank!!");
        return false;
    }else if(document.changePasswordFrm.NewPassword.value==""){
        alert("New Password can not be blank!!");
        return false;
    }else if(document.changePasswordFrm.ConfirmPassword.value==""){
        alert("Confirm Password can not be blank!!");
        return false;
    }else if(document.changePasswordFrm.NewPassword.value != document.changePasswordFrm.ConfirmPassword.value){
        alert("New Password and Confirm Password do not match!!");
        return false;
    }else if(document.changePasswordFrm.NewPassword.value == document.changePasswordFrm.CurrentPassword.value){
        alert("New Password must be different from Current Password!!");
        return false;
    }else{
        // Password strength validation
        var password = document.changePasswordFrm.NewPassword.value;
        
        if(password.length < 8){
            alert("Password must be at least 8 characters long!!");
            return false;
        }
        
        var hasUpperCase = /[A-Z]/.test(password);
        var hasLowerCase = /[a-z]/.test(password);
        var hasDigit = /\d/.test(password);
        var hasSpecialChar = /[@$!%*?&]/.test(password);
        
        if(!hasUpperCase){
            alert("Password must contain at least one uppercase letter!!");
            return false;
        }
        
        if(!hasLowerCase){
            alert("Password must contain at least one lowercase letter!!");
            return false;
        }
        
        if(!hasDigit){
            alert("Password must contain at least one digit!!");
            return false;
        }
        
        if(!hasSpecialChar){
            alert("Password must contain at least one special character (@$!%*?&)!!");
            return false;
        }
        
        return true;
    }
}

