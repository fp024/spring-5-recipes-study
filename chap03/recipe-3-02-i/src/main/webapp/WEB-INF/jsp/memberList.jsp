<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>

<html>
<head>
    <title>Member List</title>
</head>

<body>
<form method="post" action="/member/add">
    name: <input type="text" name="name"/>
    phone: <input type="text" name="phone"/>
    email: <input type="text" name="email"/>

    <input type="submit" value="Query"/>
</form>


<table border="1">
    <tr>
        <th>name</th>
        <th>phone</th>
        <th>email</th>
        <th>remove</th>
    </tr>
    <c:forEach items="${memberList}" var="member">
        <tr>
            <td>${member.name}</td>
            <td>${member.phone}</td>
            <td>${member.email}</td>
            <td>
                <form method="post" action="/member/remove">
                    <input type="hidden" name="memberName" value="${member.name}">
                    <input type="submit" value="Remove"/>
                </form>
            </td>
        </tr>
        
    </c:forEach>
</table>
</body>
</html>