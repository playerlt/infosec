<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>InfoSec: 登录</title>
</head>
<body>

<form:form id="loginForm" method="post" action="/weChat/login" modelAttribute="loginBean">
    <form:label path="username">Enter your username</form:label>
    <form:input id="username" name="username" path="username" /><br>
    <input type="submit" value="Submit" />
</form:form>
</body>
</html>