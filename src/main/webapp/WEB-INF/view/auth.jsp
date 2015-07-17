<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<meta charset="utf-8">
<title>Auth</title>
</head>
<body>
	<c:choose>
		<c:when test="${userError == ''}">
			<c:choose>
				<c:when test="${empty passwordError }">
				<c:choose>
					<c:when test="${empty roleError }">
						<h2>Welcome ${username } !</h2>
						<h2>Your roles :</h2>
						<ul>
							<c:forEach items="${roles}" var="role">
								<li>${role}</li>
							</c:forEach>
						</ul>
					</c:when>
					<c:otherwise>
						<h1>${roleError}</h1>
					</c:otherwise>
				</c:choose>
			</c:when>
				<c:otherwise>
					<h1>${passwordError}</h1>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			<h1>${userError}</h1>
		</c:otherwise>
	</c:choose>
</body>
</html>
