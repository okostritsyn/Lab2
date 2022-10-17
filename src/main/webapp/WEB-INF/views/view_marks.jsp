<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Student`s marks</title>

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
<body>

<h2>Student`s marks</h2>

<div>
  <p>Student: <a href="/web/students/edit/<c:out value="${student.id}"/>"><c:out value="${student.name}"/></a></p>
</div>

<table id="objectsList">

</table>

<div>
  <p><a href="/web/marks/new/<c:out value="${student.id}"/>">Add mark</a></p>
</div>

<script>
    loadList();

  function showListFromJSON(responseText){
    var objects = JSON.parse(responseText);
    var html = '<tr>\n' +
            '        <th>id</th>\n' +
            '        <th>Subject</th>\n' +
            '        <th>Mark</th>\n' +
            '        <th> </th>\n' +
            '    </tr>';
    for (var i = 0; i < objects.length; i++) {
      var obj = objects[i];
      html = html + '<tr><td><a href="/web/marks/edit/'+obj.id+'">' + obj.id + '</a></td>\n' +
              '        <td><a href="/web/subjects/edit/'+obj.subjectId+'">' + obj.subject + '</a></td>\n' +
              '        <td><a href="/web/marks/edit/'+obj.id+'">' + obj.mark + '</a></td>\n' +
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
    xhttp.open("DELETE", "http://localhost:8080/api/marks/delete/" + objId, true);
    xhttp.send();
  }

  function loadList() {
    var stId = <c:out value="${student.id}"/>;
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
    xhttp.open("GET", "http://localhost:8080/api/marks/findByStudent/"+stId, true);
    xhttp.send();
  }
</script>

</body>
</html>