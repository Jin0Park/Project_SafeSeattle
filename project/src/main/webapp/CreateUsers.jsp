<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create a User</title>
</head>
<body>
	<h1>Create a User</h1>
	<form action="createusers" method="post">
		<p>
			<label for="username">UserName</label>
			<input id="username" name="username" value="">
		</p>
		<p>
			<label for="password">Password</label>
			<input id="password" name="password" value="">
		</p>
		<p>
			<label for="firstName">FirstName</label>
			<input id="firstName" name="firstName" value="">
		</p>
		<p>
			<label for="lastName">LastName</label>
			<input id="lastName" name="lastName" value="">
		</p>
		<p>
			<label for="phoneNumber">PhoneNumber</label>
			<input id="phoneNumber" name="lastName" value="">
		</p>	
		<p>
			<label for="email">Email</label>
			<input id="email" name="email" value="">
		</p>
		<p>
			<label for="address">Address</label>
			<input id="address" name="address" value="">
		</p>
		<p>
			<label for="neighborhood">Neighborhood</label>
			<input id="neighborhood" name="neighborhood" value="">
		</p>
		<p>
			<input type="submit">
		</p>
	</form>
	<br/><br/>
	<p>
		<span id="successMessage"><b>${messages.success}</b></span>
	</p>
</body>
</html>
