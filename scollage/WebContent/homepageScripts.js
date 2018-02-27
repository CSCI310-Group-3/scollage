function editingStopped(){
    if(document.getElementById('inputBox').value.trim().length > 0) { 
        document.getElementById('submitButton').disabled = false; 
    } else { 
        document.getElementById('submitButton').disabled = true;
    }
}