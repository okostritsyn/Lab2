<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title><c:if test="${group.id > 0}">
        Edit group
    </c:if>
        <c:if test="${group.id == null}">
            New group
        </c:if></title>
</head>
<body>
<div align="left">
    <h2>
        <c:if test="${group.id > 0}">
        <p>Edit group<p>
        </c:if>
        <c:if test="${group.id == null}">
        <p>New group<p>
        </c:if></h2>
    <%--@elvariable id="group" type=""--%>
    <form:form>
        <table border="0" cellpadding="5">
            <tr>
                <td><input type="hidden" readonly id="group_id" value="<c:out value="${group.id}"/>"></td>
            </tr>
            <tr>
                <td>Name: </td>
                <td><input type="text" id="group_name" placeholder="Name" value="<c:out value="${group.name}"/>"></td>
            </tr>
            <tr>
                <td>Specialization: </td>
                <td>
                    <select id="specList" size="1">
                    </select>
                </td>
            </tr>
            <tr>
                <td colspan="2"><input type="button" onclick="saveGroup()" value="Save"></td>
            </tr>
        </table>
    </form:form>
</div>

<script>
    loadSpec();

    function saveGroup() {
        var groupId = document.getElementById("group_id").value;
        var groupName = document.getElementById("group_name").value;
        var specid = document.getElementById("specList").value;
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
        xmlhttp.open("POST", "http://localhost:8080/api/groups/save",true);
        xmlhttp.setRequestHeader("Content-Type", "application/json");
        xmlhttp.send(JSON.stringify({id:groupId, name: groupName, specId: specid}));

        window.history.back();
    }

    function loadSpec() {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                var specs = JSON.parse(this.responseText);
                var html = '<option disabled>Select a specialization</option>';
                var groupSpec = <c:out value="${group.specId}"/>;

                if (groupSpec !== 0 ) {
                    html = html + '<option value="<c:out value="${group.specId}"/>">'+"<c:out value="${group.specName}"/>"+'</option>';
                }
                for (var i = 0; i < specs.length; i++) {
                    var spec = specs[i];

                    if (spec.id === groupSpec ) {
                        continue;
                    }
                    html = html + '<option value='+spec.id+'>'+spec.name+'</option>';
                }
                document.getElementById("specList").innerHTML = html;
            }
        };
        xhttp.open("GET", "http://localhost:8080/api/spec/findAll", true);
        xhttp.send();
    }
</script>
</body>
</html>