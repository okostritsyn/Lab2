<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <title>Students Home Page</title>

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
<body onload="loadStudents(); loadNews();">

<h2>Students table</h2>

<span>
  <a href="web/subjects/viewAll">View subjects</a>
  <a href="web/groups/viewAll">View groups</a>
  <a href="web/spec/viewAll">View specializations</a>
</span>

<div>
  <p></p>
  <input id="search_field">
  <button onclick="searchByName()">Search by name</button>
  <p></p>
</div>

<table id="studentsList">

</table>

<div>
  <p><a href="web/students/new">Add student</a></p>
</div>

<h2>Top news</h2>

<table id="newsList">

</table>
<script>
  function searchByName() {
    var name = document.getElementById("search_field").value;
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
      if (this.readyState != 4) {
        return;
      }

      if (this.status == 200) {
        showStudentsFromJSON(this.responseText);
      } else {
        alert(this.responseText);
        console.log('err', this.responseText)
      }
    };
    xhttp.open("GET", "http://localhost:8080/api/students/findByName?name=" + name, true);
    xhttp.send();
  }

  function showStudentsFromJSON(responseText){
    var students = JSON.parse(responseText);
    var html = '<tr>\n' +
            '        <th>id</th>\n' +
            '        <th>Name</th>\n' +
            '        <th>Age</th>\n' +
            '        <th>Group</th>\n' +
            '        <th>Avg. mark</th>\n' +
            '        <th> </th>\n' +
            '    </tr>';
    for (var i = 0; i < students.length; i++) {
      var student = students[i];
      html = html + '<tr><td><a href="web/students/edit/'+student.id+'">' + student.id + '</a></td>\n' +
              '        <td><a href="web/students/edit/'+student.id+'">' + student.name + '</a></td>\n' +
              '        <td>' + student.age + '</td>\n' +
              '        <td><a href="web/groups/edit/'+student.groupid+'">' + student.group + '</a></td>' +
              '        <td><a href="web/marks/viewmarks/'+student.id+'">' + student.avgMark + '</a></td>' +
              '        <td><button onclick="deleteStudent(' + student.id + ')">Delete</button></td></tr>';

    }
    document.getElementById("studentsList").innerHTML = html;
  }

  function deleteStudent(studentId) {
    if (confirm('Do you really want to delete?') === false) {
      return;
    }

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
      if (this.readyState != 4) {
        return;
      }

      if (this.status == 200) {
        loadStudents();
      } else {
        alert(this.responseText);
        console.log('err', this.responseText)
      }
    };
    xhttp.open("DELETE", "http://localhost:8080/api/students/delete/" + studentId, true);
    xhttp.send();
  }

  function loadStudents() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
      if (this.readyState != 4) {
        return;
      }

      if (this.status == 200) {
        showStudentsFromJSON(this.responseText);
      } else {
        alert(this.responseText);
        console.log('err', this.responseText)
      }
    };
    xhttp.open("GET", "http://localhost:8080/api/students/findAll", true);
    xhttp.send();
  }

  function loadNews() {
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange  = function () {

      if (this.readyState != 4) {
        return;
      }

      var html = '<tr>\n' +
              '        <th>Country</th>\n' +
              '        <th>Author</th>\n' +
              '        <th>Title</th>\n' +
              '        <th>Description</th>\n' +
              '        <th>Date</th>\n' +
              '        <th> </th>\n' +
              '    </tr>';
      if (this.status == 200) {
        showNewsFromJSON(this.responseText);
      }else if (this.status == 0) {
        html = html + '<tr><td></td>\n' +
                '        <td>We have an error while getting news</td>\n' +
                '        <td>Error #' + this.status + '</td>\n' +
                '        <td>' + "Connection refused. Service is not available?" + '</td>\n' +
                '        <td></td>' +
                '        <td></td></tr>';
        document.getElementById("newsList").innerHTML = html;
        console.log('err', "status = 0,Service is not available")
      } else {
        html = html + '<tr><td></td>\n' +
                '        <td>We have an error while getting news</td>\n' +
                '        <td>Error #' + this.status + '</td>\n' +
                '        <td>' +this.responseText+ '</td>\n' +
                '        <td></td>' +
                '        <td></td></tr>';
        document.getElementById("newsList").innerHTML = html;
        console.log('err', this.responseText)
        }
    };
    xhttp.open("POST", "http://localhost:8081/top",true);
    xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhttp.send(JSON.stringify([{ "country": "ua"},{ "country": "us"}]));
  }

  function showNewsFromJSON(responseText){
    var response = JSON.parse(responseText);
    var html = '<tr>\n' +
            '        <th>Country</th>\n' +
            '        <th>Author</th>\n' +
            '        <th>Title</th>\n' +
            '        <th>Description</th>\n' +
            '        <th>Date</th>\n' +
            '        <th> </th>\n' +
            '    </tr>';

    for (var j = 0; j < response.length; j++){
      var currresponse = response[j];
      responseCode = currresponse.statusCode;
      html = html + '<tr><td>'+currresponse.request.paramFields.country+'</td>\n' +
              '        <td></td>\n' +
              '        <td></td>\n' +
              '        <td></td>\n' +
              '        <td></td>' +
              '        <td></td></tr>';

      if (responseCode != 200) {
        html = html + '<tr><td></td>\n' +
                '        <td>We have an error while getting news</td>\n' +
                '        <td>Error #' + responseCode + '</td>\n' +
                '        <td>' + currresponse.data.message + '</td>\n' +
                '        <td></td>' +
                '        <td></td></tr>';
        console.log('err', currresponse.data.message);
      } else {
        for (var i = 0; i < currresponse.data.articles.length; i++) {
          var article = currresponse.data.articles[i];
          html = html + '<tr><td></td>\n' +
                  '        <td>' + article.author + '</td>\n' +
                  '        <td>' + article.title + '</td>\n' +
                  '        <td>' + article.description + '</td>\n' +
                  '        <td>' + article.publishedAt + '</td>' +
                  '        <td><button onclick="openArticle(&quot;' + article.url + '&quot;)">Open</button><button onclick="saveArticle(&quot;' + encodeURI(JSON.stringify(article)) + '&quot;)">Save</button></td></tr>';

        }
      }
    }
    document.getElementById("newsList").innerHTML = html;
  }

  function saveArticle(articleString) {
    articleJson = decodeURI(articleString);
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange  = function () {

      if (this.readyState != 4) {
        return;
      }

      if (this.status == 200) {
        window.open("http://localhost:8081/files/"+this.responseText, '_blank').focus();
      } else {
        alert(this.status);
        console.log('err', this.responseText)
      }
    };
    xhttp.open("POST", "http://localhost:8081/getfile",true);
    xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhttp.send(articleJson);
  }

  function openArticle(urlArticle) {
    window.open(urlArticle, '_blank').focus();
  }
</script>
</body>
</html>