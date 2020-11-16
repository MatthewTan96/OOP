
document.addEventListener("DOMContentLoaded", function(event) { 
    function signOut(){
        sessionStorage.clear();
        window.location.href = "index.html";
    }
});