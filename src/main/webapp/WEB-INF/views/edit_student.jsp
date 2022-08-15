<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title><c:if test="${student.id > 0}">
        Edit student
    </c:if>
        <c:if test="${student.id == null}">
            New student
        </c:if></title>
</head>
<body>
<div align="left">
    <h2>
        <c:if test="${student.id > 0}">
            <p>Edit student<p>
        </c:if>
        <c:if test="${student.id == null}">
            <p>New student<p>
        </c:if></h2>
    <%--@elvariable id="student" type=""--%>
    <form:form>
    <table border="0" cellpadding="5">
        <tr>
            <td><input type="hidden" readonly id="student_id" value="<c:out value="${student.id}"/>"></td>
        </tr>
        <tr>
            <td>Name: </td>
            <td><input type="text" id="student_name" placeholder="Name" value="<c:out value="${student.name}"/>"></td>
        </tr>
        <tr>
            <td>Age: </td>
            <td><input type="number" id="student_age" placeholder="Age" value="<c:out value="${student.age}"/>"></td>
        </tr>
        <tr>
            <td>Group: </td>
            <td>
                <select id="groupList" size="1">
                </select>
            </td>
        </tr>
        <tr>
            <td colspan="2"><input type="button" onclick="saveStudent()" value="Save"></td>
        </tr>
    </table>
    </form:form>
</div>

<script>
    loadGroups();

    function saveStudent() {
        var studentId = document.getElementById("student_id").value;
        var studentName = document.getElementById("student_name").value;
        var studentAge = document.getElementById("student_age").value;
        var studentGroup = document.getElementById("groupList").value;

        var xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange = function () {
            if (this.readyState != 4) {
                return;
            }

            if (this.status == 200) {

            } else {
                console.log('err', this.responseText)
            }
        };
        xmlhttp.open("POST", "http://localhost:8080/api/students/save",true);
        xmlhttp.setRequestHeader("Content-Type", "application/json");
        xmlhttp.send(JSON.stringify({id: studentId, name: studentName, age: studentAge, groupid: studentGroup}));
        window.history.back();
    }

    function loadGroups() {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                var groups = JSON.parse(this.responseText);
                var html = '<option disabled>Select a group</option>';
                var studentGroup = <c:out value="${student.groupid}"/>;
                if (studentGroup !== 0) {
                    html = html + '<option value="<c:out value="${student.groupid}"/>">'+"<c:out value="${student.group}"/>"+'</option>';
                }
                for (var i = 0; i < groups.length; i++) {
                    var group = groups[i];

                    if (group.id === studentGroup ) {
                        continue;
                    }
                    html = html + '<option value='+group.id+'>'+group.name+'</option>';
                }

                document.getElementById("groupList").innerHTML = html;
            }
        };
        xhttp.open("GET", "http://localhost:8080/api/groups/findAll", true);
        xhttp.send();
    }
</script>
</body>
</html>