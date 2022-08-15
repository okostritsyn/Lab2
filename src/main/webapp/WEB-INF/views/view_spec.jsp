<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %><!DOCTYPE html>
<html>
<head>
  <title>Specializations</title>
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
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.2.1/themes/default/style.min.css" />
</head>
<body onload="loadList();">

<h2>Specializations table</h2>

<div>
  <p></p>
  <input id="search_field">
  <button onclick="searchByName()">Search by name</button>
  <p></p>
</div>

<table id="objectsList">

</table>

<div>
  <p><a href="new">Add specialization</a></p>
</div>

<p>Specializations tree:</p>
<div id="jstree">

</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.2.1/jstree.min.js"></script>
<script>
  loadTree();
  function searchByName() {
    var name = document.getElementById("search_field").value;
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
      if (this.readyState == 4 && this.status == 200) {
        showListFromJSON(this.responseText);
      }
    };
    xhttp.open("GET", "http://localhost:8080/api/spec/findByName?name=" + name, true);
    xhttp.send();
  }

  function showListFromJSON(responseText){
    var objects = JSON.parse(responseText);
    var html = '<tr>\n' +
            '        <th>id</th>\n' +
            '        <th>Name</th>\n' +
            '        <th>Parent</th>\n' +
            '        <th> </th>\n' +
            '    </tr>';
    for (var i = 0; i < objects.length; i++) {
      var obj = objects[i];
      var parent = obj.parent === null?"":obj.parent;
      html = html + '<tr><td><a href="edit/'+obj.id+'">' + obj.id + '</a></td>\n' +
              '        <td><a href="edit/'+obj.id+'">' + obj.name + '</a></td>\n' +
              '        <td><a href="edit/'+obj.parentId+'">' + parent  + '</a></td>\n' +
              '        <td><button onclick="deleteObject(' + obj.id + ')">Delete</button></td></tr>';

    }
    document.getElementById("objectsList").innerHTML = html;
  }

  function showTreeFromJSON(responseText){
    var tree = { 'core' : { 'data' : [] } };
    var objects = JSON.parse(responseText);
    for (var i = 0; i < objects.length; i++) {
      var obj = objects[i];
      tree.core.data.push(obj);
    }
    var jsonData = JSON.stringify(tree);
    $('#jstree').jstree(tree);
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
        console.log('err', this.responseText)
      }
    };
    xhttp.open("DELETE", "http://localhost:8080/api/spec/delete/" + objId, true);
    xhttp.send();
  }

  function loadList() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
      if (this.readyState == 4 && this.status == 200) {
        showListFromJSON(this.responseText);
      }
    };
    xhttp.open("GET", "http://localhost:8080/api/spec/findAll", true);
    xhttp.send();
  }

  function loadTree() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
      if (this.readyState == 4 && this.status == 200) {
        showTreeFromJSON(this.responseText);
      }
    };
    xhttp.open("GET", "http://localhost:8080/api/spec/findAllAsTree", true);
    xhttp.send();
  }
</script>
</body>
</html>