// Document.ready function 
document.addEventListener("DOMContentLoaded", function(event) { 
          
    if(sessionStorage.getItem("email") != null){
        var checkEmail = sessionStorage.getItem("email");
        console.log(checkEmail);
        //document.getElementById("userNamePlaceholder").innerHTML = email;
    } else {
      console.log("no email") // Login page will sent over email via session
      //var checkEmail = "test@gmail5.com";
      window.location.href = "index.html";
    }

    var request = new XMLHttpRequest();
    request.onreadystatechange = function(){
      if (this.readyState == 4 && this.status == 200) {
        dataObj = JSON.parse(this.responseText);
        var email = dataObj["email"];
        var favouritesVessels = dataObj["favouritedVessels"];
        document.getElementById("userNamePlaceholder").innerHTML = email;
        getFavouritedVessels(favouritesVessels,email);
      } else {
        // to display error code
      }
    }
    request.open("GET", "http://localhost:8080/getAccountByEmail?email="+checkEmail, true);
    request.send();
  });


  function getFavouritedVessels(favouritesVessels,email){
    var tableRef = document.getElementById('displayTable').getElementsByTagName('tbody')[0];
    console.log(favouritesVessels);
    if(favouritesVessels.length == 0){
      document.getElementById("displayOutputInformationFavouritePage").innerHTML = 'There are no Vessel within Favourites';
    } else {
    for(var ship of favouritesVessels){
      var newRow = tableRef.insertRow(tableRef.rows.length);

    var vessl_id  = ship["vesselId"];

     var cell01  = newRow.insertCell(0);
     var input01  = document.createTextNode(ship["abbrVslM"])
     cell01.appendChild(input01);

     var cell02  = newRow.insertCell(1);
     var input02  = document.createTextNode(ship["inVoyN"])
     cell02.appendChild(input02);

     var cell03  = newRow.insertCell(2);
     var input03  = document.createTextNode(ship["outVoyN"])
     cell03.appendChild(input03);

     /*
     var cell04  = newRow.insertCell(3);
     var input04  = document.createTextNode(ship["bthgDt"])
     cell04.appendChild(input04);

     var cell05  = newRow.insertCell(4);
     var input05  = document.createTextNode(ship["unbthgDt"])
     cell05.appendChild(input05);
     */

    var cell04  = newRow.insertCell(3);
    var bthDateTimeValue = ship["bthgDt"];
    bthDateTimeValue = bthDateTimeValue.split("T");
    var bthOutput = "Date:" + bthDateTimeValue[0] + " Time:" + bthDateTimeValue[1];
    var input04  = document.createTextNode(bthOutput)
    //var input04  = document.createTextNode(ship["bthgDt"])
    cell04.appendChild(input04);

    var cell05  = newRow.insertCell(4);
    var unbthDateTimeValue = ship["unbthgDt"];
    unbthDateTimeValue = unbthDateTimeValue.split("T");
    var unbthOutput = "Date:" + unbthDateTimeValue[0] + " Time:" + unbthDateTimeValue[1];
    var input05  = document.createTextNode(unbthOutput);
    //var input05  = document.createTextNode(ship["unbthgDt"])
    cell05.appendChild(input05);

     var cell06  = newRow.insertCell(5);
     var input06  = document.createTextNode(ship["berthN"])
     cell06.appendChild(input06);

     var cell07  = newRow.insertCell(6);
     var input07  = document.createTextNode(ship["status"])
     cell07.appendChild(input07);

     var cell08  = newRow.insertCell(7);
     var input08  = document.createTextNode(ship["changeCount"])
     cell08.appendChild(input08);

     var cell09  = newRow.insertCell(8);
     var input09  = document.createTextNode(ship["degreeChange"])
     cell09.appendChild(input09);

     var cell10  = newRow.insertCell(9);
     var removeBtn  = document.createElement("BUTTON")
     removeBtn.setAttribute("type", "button")
     removeBtn.setAttribute("id", "addBtn-"+ship["vesselId"]+"-"+ship["abbrVslM"]+"-"+ship["inVoyN"]+"-"+ship["outVoyN"])
     removeBtn.setAttribute("class", "btn btn-danger")
     removeBtn.setAttribute("onclick", "removeFromFavourites('"+ email +"','"+ ship["abbrVslM"] +"','"+ ship["inVoyN"] +"','"+ship["outVoyN"]+"')")
     removeBtn.innerHTML = "Remove";
     cell10.appendChild(removeBtn);


      // if degree of change is between 0 & 1 == yellow else more than == red. 
      if(ship["degreeChange"] < 1.0 && ship["degreeChange"] > 0.0){
          newRow.setAttribute("class", "table-warning")
      } else if(ship["degreeChange"] > 1.0){
          newRow.setAttribute("class", "table-danger")
      }
    }
  }
}

function removeFromFavourites( email, vesselName, incomingVoyage, outcomingVoyage){
console.log("remove to Favourites");

// Delete from Favourites
var request = new XMLHttpRequest();
request.onreadystatechange = function() {
if(this.readyState == 4 && this.status == 200){
  console.log("Success :)");
  location.reload();
} else {
  // To display error code if need.
}
}
var data = "http://localhost:8080/deleteFavourite" + "?vesselShortName=" + vesselName + "&incoming=" + incomingVoyage + "&email=" + email
request.open("DELETE", data, true);
request.setRequestHeader("Content-type", "application/json");
request.send();
}

