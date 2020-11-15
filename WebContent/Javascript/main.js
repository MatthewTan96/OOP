  // Document.ready function 
  document.addEventListener("DOMContentLoaded", function(event) { 

    //window.sessionStorage

    if(sessionStorage.getItem("email") != null){
        var checkEmail = sessionStorage.getItem("email");
    } else {
      window.location.href = "index.html";
    }
    
    var request = new XMLHttpRequest();
    request.onreadystatechange = function(){
      if (this.readyState == 4 && this.status == 200) {
        //console.log(this.responseText);
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

    getVesseslInFavorites();
    getVesseslInSubscribe();
    //SortVesselByDate();
  });

  // remove all row in the table
  function clearTable(){
    $("#displayTable > tbody").empty()
  }

  // reload page
  function reloadPage(){
    location.reload();
  }
  
  // Get all Vessel function 
  function getShips(email) {
          var request = new XMLHttpRequest();
          request.onreadystatechange = function(){
              if (this.readyState == 4 && this.status == 200) {
                  try { 
                      //console.log(this.responseText);
                      dataObj = JSON.parse(this.responseText);
                      //sessionStorage.setItem("allVesselData", dataObj);

                      // Sort Vessel by Date
                      var vesselByDate = {};
                      var allVesselFiltered = [];
                      for(var ship of dataObj){
                        var bthDateTimeValue = ship["bthgDt"];
                        bthDateTimeValue = bthDateTimeValue.split("T");
                        var checkDate = bthDateTimeValue[0];
                        // If False, date is before today, which we do not want.
                        var isAfterToday = checkIfDateBeforeToday(checkDate); 
                        // create date as key, if the date is not in vesselByDate  
                        if(isAfterToday){
                          if(!(checkDate in vesselByDate)){
                            vesselByDate[checkDate] = [];
                            vesselByDate[checkDate].push(ship);
                          } else {
                            vesselByDate[checkDate].push(ship);
                          }
                          // Place all vessels into all Vessel key, which are also before today;
                          if(!("allVessel" in vesselByDate)){
                            vesselByDate["allVessel"] = [];
                          } else {
                            vesselByDate["allVessel"].push(ship)
                            allVesselFiltered.push(ship);
                          }
                        }
                        // end of if
                      }
                      console.log(vesselByDate);
                      sessionStorage.setItem("vesselByDate", JSON.stringify(vesselByDate));
                      sessionStorage.setItem("allVesselFiltered", JSON.stringify(allVesselFiltered));

                      // Get Current Date
                      var currentDate = "";
                      for(var key in vesselByDate){
                        if(key!="allVessel"){
                          if(checkIfTodayIsCurrentDate(key)){
                            currentDate = key;
                          }
                        }
                        if(currentDate == key){
                          $('#filterVesselBySelection').append($('<option>').val(key).text(key))
                          $('#filterVesselBySelection').val(key);
                        } else if (key == "allVessel") {
                          $('#filterVesselBySelection').append($('<option>').val(key).text("All Vessels"))
                        } else {
                          $('#filterVesselBySelection').append($('<option>').val(key).text(key))
                        }

                      }
                      //console.log(currentDate);

                      if(vesselByDate[currentDate] == []){
                        document.getElementById("displayOutputInformationMainPage").innerHTML = 'Unable to connect to DataBase';
                      } else {
                      var tableRef = document.getElementById('displayTable').getElementsByTagName('tbody')[0];
                      //for(var ship of dataObj){
                        for(var ship of vesselByDate[currentDate]){
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

                        var isInFavorites = vesselInFavorites(vessl_id)
                        if(isInFavorites){
                          var cell10  = newRow.insertCell(9);
                          var addBtn  = document.createElement("BUTTON")
                          addBtn.setAttribute("type", "button")
                          addBtn.setAttribute("id", "addBtn-"+ship["vesselId"]+"-"+ship["abbrVslM"]+"-"+ship["inVoyN"]+"-"+ship["outVoyN"])
                          addBtn.setAttribute("class", "btn btn-secondary")
                          addBtn.setAttribute("onclick", "addToFavourites('"+ email +"','"+ ship["abbrVslM"] +"','"+ ship["inVoyN"] +"','"+ship["outVoyN"]+"')")
                          addBtn.setAttribute("disabled", "true")
                          addBtn.innerHTML = "Add";
                          cell10.appendChild(addBtn);
                        } else {
                          var cell10  = newRow.insertCell(9);
                          var addBtn  = document.createElement("BUTTON")
                          addBtn.setAttribute("type", "button")
                          addBtn.setAttribute("id", "addBtn-"+ship["vesselId"]+"-"+ship["abbrVslM"]+"-"+ship["inVoyN"]+"-"+ship["outVoyN"])
                          addBtn.setAttribute("class", "btn btn-primary")
                          addBtn.setAttribute("onclick", "addToFavourites('"+ email +"','"+ ship["abbrVslM"] +"','"+ ship["inVoyN"] +"','"+ship["outVoyN"]+"')")
                          addBtn.setAttribute("data-toggle", "modal")
                          addBtn.setAttribute("data-target", "#mainModalCenterFavourites")
                          addBtn.innerHTML = "Add";
                          cell10.appendChild(addBtn);
                        }

                      var isInSubscribe = vesselInSubscribe(vessl_id)
                      if(isInSubscribe){
                        var cell11  = newRow.insertCell(10);
                        var subBtn  = document.createElement("BUTTON")
                        subBtn.setAttribute("type", "button")
                        subBtn.setAttribute("id", "subBtn-"+ship["vesselId"]+"-"+ship["abbrVslM"]+"-"+ship["inVoyN"]+"-"+ship["outVoyN"])
                        subBtn.setAttribute("class", "btn btn-secondary")
                        subBtn.setAttribute("onclick", "addToSubscribe('"+ email +"','"+ ship["abbrVslM"] +"','"+ ship["inVoyN"] +"','"+ship["outVoyN"]+"')")
                        subBtn.setAttribute("disabled", "true")
                        subBtn.innerHTML = "Sub";
                        cell11.appendChild(subBtn);
                      } else {
                        var cell11  = newRow.insertCell(10);
                        var subBtn  = document.createElement("BUTTON")
                        subBtn.setAttribute("type", "button")
                        subBtn.setAttribute("id", "subBtn-"+ship["vesselId"]+"-"+ship["abbrVslM"]+"-"+ship["inVoyN"]+"-"+ship["outVoyN"])
                        subBtn.setAttribute("class", "btn btn-info")
                        subBtn.setAttribute("onclick", "addToSubscribe('"+ email +"','"+ ship["abbrVslM"] +"','"+ ship["inVoyN"] +"','"+ship["outVoyN"]+"')")
                        subBtn.setAttribute("data-toggle", "modal")
                        subBtn.setAttribute("data-target", "#mainModalCenterSubscribe")
                        subBtn.innerHTML = "Sub";
                        cell11.appendChild(subBtn);
                      }
                        // if degree of change is between 0 & 1 == yellow else more than == red. 
                        if(ship["degreeChange"] < 1.0 && ship["degreeChange"] > 0.0){
                          newRow.setAttribute("class", "table-warning")
                        } else if(ship["degreeChange"] > 1.0){
                          newRow.setAttribute("class", "table-danger")
                        }
                      } // end of for loop
                    } // end of else
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

// Print vesssels into Table.
function getVesselByName(){
  var request = new XMLHttpRequest();
  request.onreadystatechange = function(){
    if(this.readyState == 4 && this.status == 200){
        try {                      
          console.log(this.responseText);
          dataObj = JSON.parse(this.responseText);
          if(dataObj == []){
            console.log("nothing in Database")
            } else {
              clearTable()
              var email = sessionStorage.getItem("email");
              var searchVessel = document.getElementById('vesselSearch').value;
              searchVessel = searchVessel.toLowerCase();
              var isFound = false;
              var tableRef = document.getElementById('displayTable').getElementsByTagName('tbody')[0];

              var allVesselFiltered = sessionStorage.getItem("allVesselFiltered");
              allVesselFiltered = JSON.parse(allVesselFiltered);

              //for(var ship of dataObj){
                for(var ship of allVesselFiltered){
                var newRow = tableRef.insertRow(tableRef.rows.length);

                var SelectByVesselName = ship["abbrVslM"];
                SelectByVesselName = SelectByVesselName.toLowerCase();

                if(searchVessel == SelectByVesselName)
                {
                  console.log("Found!")
                  isFound = true;
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

                  var isInFavorites = vesselInFavorites(vessl_id)
                  if(isInFavorites){
                    var cell10  = newRow.insertCell(9);
                    var addBtn  = document.createElement("BUTTON")
                    addBtn.setAttribute("type", "button")
                    addBtn.setAttribute("id", "addBtn-"+ship["vesselId"]+"-"+ship["abbrVslM"]+"-"+ship["inVoyN"]+"-"+ship["outVoyN"])
                    addBtn.setAttribute("class", "btn btn-secondary")
                    addBtn.setAttribute("onclick", "addToFavourites('"+ email +"','"+ ship["abbrVslM"] +"','"+ ship["inVoyN"] +"','"+ship["outVoyN"]+"')")
                    addBtn.setAttribute("disabled", "true")
                    addBtn.innerHTML = "Add";
                    cell10.appendChild(addBtn);
                  } else {
                    var cell10  = newRow.insertCell(9);
                    var addBtn  = document.createElement("BUTTON")
                    addBtn.setAttribute("type", "button")
                    addBtn.setAttribute("id", "addBtn-"+ship["vesselId"]+"-"+ship["abbrVslM"]+"-"+ship["inVoyN"]+"-"+ship["outVoyN"])
                    addBtn.setAttribute("class", "btn btn-primary")
                    addBtn.setAttribute("onclick", "addToFavourites('"+ email +"','"+ ship["abbrVslM"] +"','"+ ship["inVoyN"] +"','"+ship["outVoyN"]+"')")
                    addBtn.setAttribute("data-toggle", "modal")
                    addBtn.setAttribute("data-target", "#mainModalCenterFavourites")
                    addBtn.innerHTML = "Add";
                    cell10.appendChild(addBtn);
                  }

                  var isInSubscribe = vesselInSubscribe(vessl_id)
                  if(isInSubscribe){
                    var cell11  = newRow.insertCell(10);
                    var subBtn  = document.createElement("BUTTON")
                    subBtn.setAttribute("type", "button")
                    subBtn.setAttribute("id", "subBtn-"+ship["vesselId"]+"-"+ship["abbrVslM"]+"-"+ship["inVoyN"]+"-"+ship["outVoyN"])
                    subBtn.setAttribute("class", "btn btn-secondary")
                    subBtn.setAttribute("onclick", "addToSubscribe('"+ email +"','"+ ship["abbrVslM"] +"','"+ ship["inVoyN"] +"','"+ship["outVoyN"]+"')")
                    subBtn.setAttribute("disabled", "true")
                    subBtn.innerHTML = "Sub";
                    cell11.appendChild(subBtn);
                  } else {
                    var cell11  = newRow.insertCell(10);
                    var subBtn  = document.createElement("BUTTON")
                    subBtn.setAttribute("type", "button")
                    subBtn.setAttribute("id", "subBtn-"+ship["vesselId"]+"-"+ship["abbrVslM"]+"-"+ship["inVoyN"]+"-"+ship["outVoyN"])
                    subBtn.setAttribute("class", "btn btn-info")
                    subBtn.setAttribute("onclick", "addToSubscribe('"+ email +"','"+ ship["abbrVslM"] +"','"+ ship["inVoyN"] +"','"+ship["outVoyN"]+"')")
                    subBtn.setAttribute("data-toggle", "modal")
                    subBtn.setAttribute("data-target", "#mainModalCenterSubscribe")
                    subBtn.innerHTML = "Sub";
                    cell11.appendChild(subBtn);
                  }

                  // if degree of change is between 0 & 1 == yellow else more than == red. 
                  if(ship["degreeChange"] < 1.0 && ship["degreeChange"] > 0.0){
                    newRow.setAttribute("class", "table-warning")
                  } else if(ship["degreeChange"] > 1.0){
                    newRow.setAttribute("class", "table-danger")
                  } // End of color check

                } // End of If 
              } // end of for loop
              if(!(isFound)){
                //console.log("Not Found")
                document.getElementById("displayOutputInformationMainPage").innerHTML = 'Unable to search for query: ' + searchVessel;
              }
            } // end of else
        } catch(err){
          console.log("error");
        }
      }    
    }
  request.open("GET", "http://localhost:8080/getAllVessels", true);
  request.send();  
}

// -- FUNCTION -- //
// Check if vessel in favorites and return boolean to disable button
function getVesseslInFavorites(){
  var request = new XMLHttpRequest();
  request.onreadystatechange = function(){
    if (this.readyState == 4 && this.status == 200) {
      dataObj = JSON.parse(this.responseText);
      var favouritesVessels = dataObj["favouritedVessels"];
      //console.log(favouritesVessels);
      sessionStorage.setItem("vesseslInFavorites", JSON.stringify(favouritesVessels));
    } else {
      // to display error code
    }
  }
  var email = sessionStorage.getItem("email");
  request.open("GET", "http://localhost:8080/getAccountByEmail?email="+email, true);
  request.send();
}

function vesselInFavorites(check_Vessel_ID){
  var favouritesVessels = sessionStorage.getItem("vesseslInFavorites");
  favouritesVessels = JSON.parse(favouritesVessels);
  //console.log(favouritesVessels);
  for(var ship of favouritesVessels){
    var vessl_id = ship["vesselId"];
    if(check_Vessel_ID == vessl_id){
      //console.log("true")
      return true;
    } 
  }
  return false;
}
// -- END OF FUNCTION --//

// -- FUNCTION -- //
// Check if vessel in subscribe and return boolean to disable button
function getVesseslInSubscribe(){
  var request = new XMLHttpRequest();
  request.onreadystatechange = function(){
    if (this.readyState == 4 && this.status == 200) {
      dataObj = JSON.parse(this.responseText);
      var subscribedVessels = dataObj["subscribedVessels"];
      //console.log(subscribedVessels);
      sessionStorage.setItem("vesseslInSubscribe", JSON.stringify(subscribedVessels));
    } else {
      // to display error code
    }
  }
  var email = sessionStorage.getItem("email");
  request.open("GET", "http://localhost:8080/getAccountByEmail?email="+email, true);
  request.send();
}

function vesselInSubscribe(check_Vessel_ID){
  var subscribedVessels = sessionStorage.getItem("vesseslInSubscribe");
  subscribedVessels = JSON.parse(subscribedVessels);
  //console.log(subscribedVessels);
  for(var ship of subscribedVessels){
    var vessl_id = ship["vesselId"];
    if(check_Vessel_ID == vessl_id){
      //console.log("true")
      return true;
    } 
  }
  return false;
}
// -- END OF FUNCTION --//

// -- FUNCTION -- //
// display vessel by current date and create input selection. 
/*
function SortVesselByDate(){
  var allVesselData = sessionStorage.getItem("allVesselData");
  allVesselData = JSON.parse(allVesselData);
  //console.log(allVesselData);
  var vesselByDate = {};
  for(var ship of allVesselData){
    var bthDateTimeValue = ship["bthgDt"];
    bthDateTimeValue = bthDateTimeValue.split("T");
    var checkDate = bthDateTimeValue[0];
    var isAfterToday = checkIfDateBeforeToday(checkDate); // If False, date is before today, which we do not want.
    // create date as key, if the date is not in vesselByDate  
    if(isAfterToday){
      if(!(checkDate in vesselByDate)){
        vesselByDate[checkDate] = [];
        vesselByDate[checkDate].push(ship);
      } else {
        vesselByDate[checkDate].push(ship);
      }
      // Place all vessels into all Vessel key, which are also before today;
      if(!("allVessel" in vesselByDate)){
        vesselByDate["allVessel"] = [];
      } else {
        vesselByDate["allVessel"].push(ship)
      }
    }
    // end of if
  }
  console.log(vesselByDate);
}
*/

// -- FUNCTION -- //
// Function filters out date that is before current date (Today). 
function checkIfDateBeforeToday(date){
  //console.log(date);
  //console.log(time);
  // date
  var year = date.split("-")[0];
  var month = date.split("-")[1];
  var day = date.split("-")[2];
  // time
  /*
  var hour = time.split(":")[0];
  var min = time.split(":")[1];
  var sec = "00";
  */
  //var postDateTime = " " + month + " " + day + ", " + year + " " + hour + ":" + min + ":" + sec + "";
  var inputDate = " " + month + " " + day + ", " + year;
  var checkinputDate = new Date(inputDate).getTime();

  var now = new Date().getTime();
  var timeRemaining =  checkinputDate - now;

  if (timeRemaining < -86400000) {
    return false;
  } else {
    return true;
  }
  return false;
}
// -- END OF FUNCTION --//

// -- FUNCTION -- //
// Function check the objects keys if the date is current date. 
function checkIfTodayIsCurrentDate(key){
  var year = key.split("-")[0];
  var month = key.split("-")[1];
  var day = key.split("-")[2];

  var inputDate = " " + month + " " + day + ", " + year;
  var checkinputDate = new Date(inputDate).getTime();

  var now = new Date().getTime();
  var timeRemaining =  checkinputDate - now;

  if (timeRemaining < 0) {
    return true;
  } else {
    return false;
  }
}
// -- END OF FUNCTION --//

function filterVesselByInputSelection(){
  clearTable();
  var email = sessionStorage.getItem("email");
  var selectedDate = document.getElementById('filterVesselBySelection').value;
  var vesselByDate = sessionStorage.getItem("vesselByDate");
  vesselByDate = JSON.parse(vesselByDate);
  //console.log(selectedDate);
  //console.log(vesselByDate);
  //console.log(vesselByDate[selectedDate]);
  var tableRef = document.getElementById('displayTable').getElementsByTagName('tbody')[0];
  for(var ship of vesselByDate[selectedDate]){
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

    var isInFavorites = vesselInFavorites(vessl_id)
    if(isInFavorites){
      var cell10  = newRow.insertCell(9);
      var addBtn  = document.createElement("BUTTON")
      addBtn.setAttribute("type", "button")
      addBtn.setAttribute("id", "addBtn-"+ship["vesselId"]+"-"+ship["abbrVslM"]+"-"+ship["inVoyN"]+"-"+ship["outVoyN"])
      addBtn.setAttribute("class", "btn btn-secondary")
      addBtn.setAttribute("onclick", "addToFavourites('"+ email +"','"+ ship["abbrVslM"] +"','"+ ship["inVoyN"] +"','"+ship["outVoyN"]+"')")
      addBtn.setAttribute("disabled", "true")
      addBtn.innerHTML = "Add";
      cell10.appendChild(addBtn);
    } else {
      var cell10  = newRow.insertCell(9);
      var addBtn  = document.createElement("BUTTON")
      addBtn.setAttribute("type", "button")
      addBtn.setAttribute("id", "addBtn-"+ship["vesselId"]+"-"+ship["abbrVslM"]+"-"+ship["inVoyN"]+"-"+ship["outVoyN"])
      addBtn.setAttribute("class", "btn btn-primary")
      addBtn.setAttribute("onclick", "addToFavourites('"+ email +"','"+ ship["abbrVslM"] +"','"+ ship["inVoyN"] +"','"+ship["outVoyN"]+"')")
      addBtn.setAttribute("data-toggle", "modal")
      addBtn.setAttribute("data-target", "#mainModalCenterFavourites")
      addBtn.innerHTML = "Add";
      cell10.appendChild(addBtn);
    }

  var isInSubscribe = vesselInSubscribe(vessl_id)
  if(isInSubscribe){
    var cell11  = newRow.insertCell(10);
    var subBtn  = document.createElement("BUTTON")
    subBtn.setAttribute("type", "button")
    subBtn.setAttribute("id", "subBtn-"+ship["vesselId"]+"-"+ship["abbrVslM"]+"-"+ship["inVoyN"]+"-"+ship["outVoyN"])
    subBtn.setAttribute("class", "btn btn-secondary")
    subBtn.setAttribute("onclick", "addToSubscribe('"+ email +"','"+ ship["abbrVslM"] +"','"+ ship["inVoyN"] +"','"+ship["outVoyN"]+"')")
    subBtn.setAttribute("disabled", "true")
    subBtn.innerHTML = "Sub";
    cell11.appendChild(subBtn);
  } else {
    var cell11  = newRow.insertCell(10);
    var subBtn  = document.createElement("BUTTON")
    subBtn.setAttribute("type", "button")
    subBtn.setAttribute("id", "subBtn-"+ship["vesselId"]+"-"+ship["abbrVslM"]+"-"+ship["inVoyN"]+"-"+ship["outVoyN"])
    subBtn.setAttribute("class", "btn btn-info")
    subBtn.setAttribute("onclick", "addToSubscribe('"+ email +"','"+ ship["abbrVslM"] +"','"+ ship["inVoyN"] +"','"+ship["outVoyN"]+"')")
    subBtn.setAttribute("data-toggle", "modal")
    subBtn.setAttribute("data-target", "#mainModalCenterSubscribe")
    subBtn.innerHTML = "Sub";
    cell11.appendChild(subBtn);
  }
    // if degree of change is between 0 & 1 == yellow else more than == red. 
    if(ship["degreeChange"] < 1.0 && ship["degreeChange"] > 0.0){
      newRow.setAttribute("class", "table-warning")
    } else if(ship["degreeChange"] > 1.0){
      newRow.setAttribute("class", "table-danger")
    }
  }
}