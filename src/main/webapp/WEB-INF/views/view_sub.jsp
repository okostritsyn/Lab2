<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %><!DOCTYPE html>
<html>
<head>
  <title>Subjects</title>

  <style>
    table {
      font-family: arial, sans-serif;
      border-collapse: collapse;
      width: 100%;
    }

    td, th {
      border: 1px solid #dddddd;
      text-align: left;
      padding: 8px;
    }

    tr:nth-child(even) {
      background-color: #dddddd;
    }
  </style>
</head>
<body onload="loadList();">

<h2>Subjects table</h2>

<div>
  <p></p>
  <input id="search_field">
  <button onclick="searchByName()">Search by name</button>
  <p></p>
</div>

<table id="objectsList">

</table>

<div>
  <p><a href="new">Add subject</a></p>
</div>

<script>
  function searchByName() {
    var name = document.getElementById("search_field").value;
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
      if (this.readyState != 4) {
        return;
      }

      if (this.status == 200) {
        showListFromJSON(this.responseText);
      } else {
        alert(this.responseText);
        console.log('err', this.responseText)
      }
    };
    xhttp.open("GET", "http://localhost:8080/api/subjects/findByName?name=" + name, true);
    xhttp.send();
  }

  function showListFromJSON(responseText){
    var objects = JSON.parse(responseText);
    var html = '<tr>\n' +
            '        <th>id</th>\n' +
            '        <th>Name</th>\n' +
            '        <th> </th>\n' +
            '    </tr>';
    for (var i = 0; i < objects.length; i++) {
      var obj = objects[i];
      html = html + '<tr><td><a href="edit/'+obj.id+'">' + obj.id + '</a></td>\n' +
              '        <td><a href="edit/'+obj.id+'">' + obj.name + '</a></td>\n' +
              '        <td><button onclick="deleteObject(' + obj.id + ')">Delete</button></td></tr>';

    }
    document.getElementById("objectsList").innerHTML = html;
  }

  function deleteObject(objId) {
    if (confirm('Do you really want to delete?') === false) {
      return;
    }

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
      if (this.readyState != 4) {
        return;
      }

      if (this.status == 200) {
        loadList();
      } else {
        alert(this.responseText);
        console.log('err', this.responseText)
      }
    };
    xhttp.open("DELETE", "http://localhost:8080/api/subjects/delete/" + objId, true);
    xhttp.send();
  }

  function loadList() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
      if (this.readyState != 4) {
        return;
      }

      if (this.status == 200) {
        showListFromJSON(this.responseText);
      } else {
        alert(this.responseText);
        console.log('err', this.responseText)
      }
    };
    xhttp.open("GET", "http://localhost:8080/api/subjects/findAll", true);
    xhttp.send();
  }
</script>
</body>
</html>