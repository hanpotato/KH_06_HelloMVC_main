<%@page import="com.rclass.admin.controller.MyServletException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>

<%
	exception = (MyServletException) request.getAttribute("e");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>errorPage.jsp</title>
</head>
<body>
	<h1>에러 : <%= exception.getMessage() %></h1>
	<a href="<%= request.getContextPath() %>/index.jsp">시작페이지로이동</a>
</body>
</html>