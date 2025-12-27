function startSearch(){
    if(document.searchItemsFrm.R1[1].checked){
        if(document.searchItemsFrm.BookTitle.value==""){
            alert("You must enter a BookTitle ");
            return;
        }
    }else if(document.searchItemsFrm.R1[2].checked){
        if(document.searchItemsFrm.BookAuthor.value==""){
            alert("You must enter a BookAuthor ");
            return;
        }
    }else if(document.searchItemsFrm.R1[3].checked){
        if(document.searchItemsFrm.BookPublisher.value==""){
            alert("You must enter a BookPublisher ");
            return;
        }
    }else{
        document.searchItemsFrm.action="/pages/html/postLogin/Search.jsp";
        document.searchItemsFrm.submit();
    }
}
