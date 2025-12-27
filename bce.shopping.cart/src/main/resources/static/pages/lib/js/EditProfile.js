function validate(){
    if(document.editProfileFrm.FirstName.value==""){
        alert("First Name can not be blank!!");
        return false;
    }else if(document.editProfileFrm.LastName.value==""){
        alert("Last Name can not be blank!!");
        return false;
    }else if(document.editProfileFrm.Address1.value==""){
        alert("Address can not be blank!!");
        return false;
    }else if(document.editProfileFrm.City.value==""){
        alert("City can not be blank!!");
        return false;
    }else if(document.editProfileFrm.State.value=="Select State"){
        alert("You must select a state!!");
        return false;
    }else if(document.editProfileFrm.PinCode.value==""){
        alert("PinCode can not be blank!!");
        return false;
    }else if(document.editProfileFrm.Email.value==""){
        alert("Email ID can not be blank!!");
        return false;
    }else if(document.editProfileFrm.Phone.value==""){
        alert("Phone Number can not be blank!!");
        return false;
    }else{
        // Email validation
        var emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if(!emailPattern.test(document.editProfileFrm.Email.value)){
            alert("Please enter a valid email address!!");
            return false;
        }
        return true;
    }
}

