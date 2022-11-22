<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>UserPosts</title>
</head>
<body>
	<h1>${messages.title}</h1>
        <table border="1">
            <tr>
                <th>PostId</th>
                <th>UserId</th>
                <th>Username</th>
                <th>ReportId</th>  
                <th>Title</th>              
                <th>Content</th>
                <th>Created</th>
                <th>Comments</th>
            </tr>
            <c:forEach items="${userPosts}" var="userPosts" >
                <tr>
                    <td><c:out value="${userPosts.getPostId()}" /></td>
                    <td><c:out value="${userPosts.getUser().getUserId()}" /></td>
                    <td><c:out value="${userPosts.getUser().getUsername()}s" /></td>
                    <td><c:out value="${userPosts.getReport().getReportId()}" /></td>
                    <td><c:out value="${userPosts.getTitle()}" /></td>                    
                    <td><c:out value="${userPosts.getContent()}" /></td>
                    <td><fmt:formatDate value="${userPosts.getCreated()}" pattern="MM-dd-yyyy hh:mm:sa"/></td>
                    <td><a href="postcomments?postId=<c:out value="${userPosts.getPostId()}"/>">Comments</a></td>

                </tr>
            </c:forEach>
       </table>
</body>
</html>