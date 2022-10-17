<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title><c:if test="${subject.id > 0}">
        Edit subject
    </c:if>
        <c:if test="${subject.id == null}">
            New subject
        </c:if></title>
</head>
<body>
<div align="left">
    <h2>
        <c:if test="${subject.id > 0}">
        <p>Edit subject<p>
        </c:if>
        <c:if test="${subject.id == null}">
        <p>New subject<p>
        </c:if></h2>
    <%--@elvariable id="subject" type=""--%>
    <form:form>
        <table border="0" cellpadding="5">
            <tr>
                <td><input type="hidden" id="subject_id" value="<c:out value="${subject.id}"/>"></td>
            </tr>
            <tr>
                <td>Name: </td>
                <td><input type="text" id="subject_name" placeholder="Name" value="<c:out value="${subject.name}"/>"></td>
            </tr>
            <tr>
                <td colspan="2"><input type="button" onclick="saveSubject()" value="Save"></td>
            </tr>
        </table>
    </form:form>
</div>

<script>
    function saveSubject() {
        var subjectName = document.getElementById("subject_name").value;
        var subjectId = document.getElementById("subject_id").value;

        var xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange = function () {
            if (this.readyState != 4) {
                return;
            }

            if (this.status == 200) {

            } else {
                alert(this.responseText);
                console.log('err', this.responseText)
            }
        };
        xmlhttp.open("POST", "http://localhost:8080/api/subjects/save",true);
        xmlhttp.setRequestHeader("Content-Type", "application/json");
        xmlhttp.send(JSON.stringify({id: subjectId,name: subjectName}));
        window.history.back();
    }
</script>
</body>
</html>