<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Find a User</title>
</head>
<body>
	<form action="findusers" method="post">
		<h1>Search for a User by Neighborhood</h1>
		<p>
			<label for="neighborhood">Neighborhood</label>
			<input id="neighborhood" name="neighborhood" value="${fn:escapeXml(param.neighborhood)}">
		</p>
		<p>
			<input type="submit">
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
		<a href="createusers"> Create a User </a>

	</form>
	<h1>Matching Users</h1>
        <table border="1">
            <tr>
                <th>UserName</th>
                <th>FirstName</th>
                <th>LastName</th>
                <th>Neighborhood</th>
                <th>Posts</th>
            </tr>
            <c:forEach items="${users}" var="user" >
                <tr>
                    <td><c:out value="${user.getUsername()}" /></td>
                    <td><c:out value="${user.getFirstName()}" /></td>
                    <td><c:out value="${user.getLastName()}" /></td>
                    <td><c:out value="${user.getNeighborhood()}" /></td>
                    <td><a href="userposts?userId=<c:out value="${user.getUserId()}"/>">Posts</a></td>
                </tr>
            </c:forEach>
       </table>
</body>
</html>
