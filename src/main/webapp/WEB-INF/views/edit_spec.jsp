<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title><c:if test="${spec.id > 0}">
        Edit specialization
    </c:if>
        <c:if test="${spec.id == null}">
            New specialization
        </c:if></title>
</head>
<body>
<div align="left">
    <h2>
        <c:if test="${spec.id > 0}">
        <p>Edit specialization<p>
        </c:if>
        <c:if test="${spec.id == null}">
        <p>New specialization<p>
        </c:if></h2>
    <%--@elvariable id="spec" type=""--%>
    <form:form>
        <table border="0" cellpadding="5">
            <tr>
                <td><input type="hidden" readonly id="spec_id" value="<c:out value="${spec.id}"/>"></td>
            </tr>
            <tr>
                <td>Name: </td>
                <td><input type="text" id="spec_name" placeholder="Name" value="<c:out value="${spec.name}"/>"></td>
            </tr>
            <tr>
                <td>Parent: </td>
                <td>
                    <select id="specList" size="1">
                    </select>
                </td>
            </tr>
            <tr>
                <td colspan="2"><input type="button" onclick="saveSpec()" value="Save"></td>
            </tr>
        </table>
    </form:form>
</div>

<script>
    loadSpec();
    function saveSpec() {
        var specName = document.getElementById("spec_name").value;
        var specId = document.getElementById("spec_id").value;
        var specParent = document.getElementById("specList").value;
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
        xmlhttp.open("POST", "http://localhost:8080/api/spec/save",true);
        xmlhttp.setRequestHeader("Content-Type", "application/json");
        xmlhttp.send(JSON.stringify({id:specId, name: specName, parentId: specParent}));
        window.history.back();

    }
    function loadSpec() {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                var specs = JSON.parse(this.responseText);
                var html = '<option disabled>Select a parent</option>';
                var parentSpec = <c:out value="${spec.parentId}"/>;

                if (parentSpec !== 0 ) {
                    html = html + '<option value="<c:out value="${spec.parentId}"/>">'+"<c:out value="${spec.parent}"/>"+'</option>';
                } else {
                    html = html + '<option value=null>No parent</option>';
                }
                for (var i = 0; i < specs.length; i++) {
                    var spec = specs[i];

                    if (spec.id === parentSpec ) {
                        continue;
                    }
                    html = html + '<option value='+spec.id+'>'+spec.name+'</option>';
                }
                if (parentSpec !== 0 ) {
                    html = html + '<option value=null>No parent</option>';
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