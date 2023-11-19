<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.servletContext.contextPath}" />
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>Login</title>
  <link type="text/css" rel="stylesheet"
        href="${contextPath}/webjars/Semantic-UI/semantic.min.css"/>
  <style>
    body {
      background-color: #DADADA;
    }

    body > .grid {
      height: 100%;
    }

    .column {
      max-width: 450px;
    }
  </style>
</head>

<body>
<div class="ui middle aligned center aligned grid">
  <div class="column">
    <h2 class="ui header">You have been successfully logged out</h2>
    <div>Goto <a href="${contextPath}/" class="link">Chapter07 Example Main Page</a>.</div>
  </div>
</div>
</body>
</html>