<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title><c:if test="${mark.id > 0}">
        Edit mark
    </c:if>
        <c:if test="${mark.id == null}">
            New mark
        </c:if></title>
</head>
<body>
<div align="left">
    <h2>
        <c:if test="${mark.id > 0}">
        <p>Edit mark<p>
        </c:if>
        <c:if test="${mark.id == null}">
        <p>New mark<p>
        </c:if></h2>
    <%--@elvariable id="mark" type=""--%>
    <form:form>
        <table border="0" cellpadding="5">
            <tr>
                <td><input type="hidden" readonly id="mark_id" value="<c:out value="${mark.id}"/>"></td>
            </tr>
            <tr>
                <td><input type="hidden" id="student_id" value="<c:out value="${mark.studentId}"/>"></td>
            </tr>
            <tr>
                <td>Student: </td>
                <td><input type="text" readonly id="student_name" placeholder="Name" value="<c:out value="${mark.student}"/>"></td>
            </tr>
            <tr>
                <td>Subject: </td>
                <td>
                    <select id="subList" size="1">
                    </select>
                </td>
            </tr>
            <tr>
                <td>Mark: </td>
                <td><input type="number" id="mark_value" placeholder="Mark" value="<c:out value="${mark.mark}"/>"></td>
            </tr>
            <tr>
                <td colspan="2"><input type="button" onclick="saveMark()" value="Save"></td>
            </tr>
        </table>
    </form:form>
</div>

<script>
    loadSubjects();
    function saveMark() {
        var markId = document.getElementById("mark_id").value;
        var mark = document.getElementById("mark_value").value;
        var subject = document.getElementById("subList").value;
        var student = document.getElementById("student_id").value;
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
        xmlhttp.open("POST", "http://localhost:8080/api/marks/save",true);
        xmlhttp.setRequestHeader("Content-Type", "application/json");
        xmlhttp.send(JSON.stringify({id:markId, subjectId: subject, studentId: student, mark: mark}));
        window.history.back();
    }

    function loadSubjects() {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                var subjects = JSON.parse(this.responseText);
                var html = '<option disabled>Select a subject</option>';
                var currSub = <c:out value="${mark.subjectId}"/>;

                if (currSub !== 0 ) {
                    html = html + '<option value="<c:out value="${mark.subjectId}"/>">'+"<c:out value="${mark.subject}"/>"+'</option>';
                }
                for (var i = 0; i < subjects.length; i++) {
                    var subject = subjects[i];

                    if (subject.id === currSub ) {
                        continue;
                    }
                    html = html + '<option value='+subject.id+'>'+subject.name+'</option>';
                }
                document.getElementById("subList").innerHTML = html;
            }
        };
        xhttp.open("GET", "http://localhost:8080/api/subjects/findAll", true);
        xhttp.send();
    }
</script>
</body>
</html>