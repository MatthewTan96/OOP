  // Document.ready function 
  document.addEventListener("DOMContentLoaded", function(event) { 

    if(sessionStorage.getItem("email") != null){
        var checkEmail = sessionStorage.getItem("email");
        //document.getElementById("userNamePlaceholder").innerHTML = email;
    } else {
      console.log("This is for testing purpose!") // Login page will sent over email via session
      var checkEmail = "test@gmail5.com";
      sessionStorage.setItem("email", "test@gmail5.com");
    }


    var request = new XMLHttpRequest();
    request.onreadystatechange = function(){
      if (this.readyState == 4 && this.status == 200) {
        dataObj = JSON.parse(this.responseText);
        var email = dataObj["email"];
        document.getElementById("userNamePlaceholder").innerHTML = email;
        getShips(email);
      } else {
        // to display error code
      }
    }
    request.open("GET", "http://localhost:8080/getAccountByEmail?email="+checkEmail, true);
    request.send();
  
  });

  
  // Get all Ships function 
  function getShips(email) {
          var request = new XMLHttpRequest();
          request.onreadystatechange = function(){
              if (this.readyState == 4 && this.status == 200) {
                  try { 
                      console.log(this.responseText);
                      dataObj = JSON.parse(this.responseText);
                      if(dataObj == []){
                        console.log("nothing in Database")
                      } else {
                      var tableRef = document.getElementById('displayTable').getElementsByTagName('tbody')[0];
                      for(var ship of dataObj){
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

                        var cell04  = newRow.insertCell(3);
                        var input04  = document.createTextNode(ship["btrgDt"])
                        cell04.appendChild(input04);

                        var cell05  = newRow.insertCell(4);
                        var input05  = document.createTextNode(ship["unbthgDt"])
                        cell05.appendChild(input05);

                        var cell06  = newRow.insertCell(5);
                        var input06  = document.createTextNode(ship["berthN"])
                        cell06.appendChild(input06);

                        var cell07  = newRow.insertCell(6);
                        var input07  = document.createTextNode(ship["changeCount"])
                        cell07.appendChild(input07);

                        var cell08  = newRow.insertCell(7);
                        var input08  = document.createTextNode(ship["degreeChange"])
                        cell08.appendChild(input08);

                        var cell09  = newRow.insertCell(8);
                        var input09  = document.createTextNode(ship["firstBerthTime"])
                        cell09.appendChild(input09);

                        var cell10  = newRow.insertCell(9);
                        var input10  = document.createTextNode(ship["status"])
                        cell10.appendChild(input10);

                        var cell11  = newRow.insertCell(10);
                        var addBtn  = document.createElement("BUTTON")
                        addBtn.setAttribute("type", "button")
                        addBtn.setAttribute("id", "addBtn-"+ship["vesselId"]+"-"+ship["abbrVslM"]+"-"+ship["inVoyN"]+"-"+ship["outVoyN"])
                        addBtn.setAttribute("class", "btn btn-primary")
                        addBtn.setAttribute("onclick", "addToFavourites('"+ email +"','"+ ship["abbrVslM"] +"','"+ ship["inVoyN"] +"','"+ship["outVoyN"]+"')")
                        addBtn.innerHTML = "Add";
                        cell11.appendChild(addBtn);

                        var cell12  = newRow.insertCell(11);
                        var subBtn  = document.createElement("BUTTON")
                        subBtn.setAttribute("type", "button")
                        subBtn.setAttribute("id", "subBtn-"+ship["vesselId"]+"-"+ship["abbrVslM"]+"-"+ship["inVoyN"]+"-"+ship["outVoyN"])
                        subBtn.setAttribute("class", "btn btn-info")
                        subBtn.setAttribute("onclick", "addToSubscribe('"+ email +"','"+ ship["abbrVslM"] +"','"+ ship["inVoyN"] +"','"+ship["outVoyN"]+"')")
                        subBtn.innerHTML = "Sub";
                        cell12.appendChild(subBtn);

                        // if degree of change is between 0 & 1 == yellow else more than == red. 
                        if(ship["degreeChange"] < 1.0 && ship["degreeChange"] > 0.0){
                          newRow.setAttribute("class", "table-warning")
                        } else if(ship["degreeChange"] > 1.0){
                          newRow.setAttribute("class", "table-danger")
                        }
                      }
                    }
                  } catch(err){
                      console.log("error");
                  }
          }
      }
      request.open("GET", "http://localhost:8080/getAllVessels", true);
      request.send();  
  }
  // End of get all ship function 

function addToFavourites( email, vesselName, incomingVoyage, outcomingVoyage){
  console.log("Add to Favourites");

  // Sent to Favourites
  var request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if(this.readyState == 4 && this.status == 200){
      console.log("Success :)");
    } else {
      // To display error code if need.
    }
  }
  var data = "http://localhost:8080/postFavourite" + "?vesselShortName=" + vesselName + "&incoming=" + incomingVoyage + "&email=" + email
  request.open("POST", data, true);
  request.setRequestHeader("Content-type", "application/json");
  request.send();
}
// End of get add to Favourites function 

function addToSubscribe(email, vesselName, incomingVoyage, outcomingVoyage){
  console.log("SUB!!");
  console.log(email);
  console.log(vesselName);
  console.log(incomingVoyage);
  console.log(outcomingVoyage);

  console.log("Add to Subscribe");

  // Sent to Subscribe
  var request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if(this.readyState == 4 && this.status == 200){
      console.log("Success :)");
    } else {
      // To display error code if need.
    }
  }
  var data = "http://localhost:8080/postSubscribe" + "?vesselShortName=" + vesselName + "&incoming=" + incomingVoyage + "&email=" + email
  request.open("POST", data, true);
  request.setRequestHeader("Content-type", "application/json");
  request.send();
}
// End of get add to Subscribe function 
