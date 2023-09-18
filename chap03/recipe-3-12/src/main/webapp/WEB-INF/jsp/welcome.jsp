<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title><spring:message code="welcome.title" text="Welcome"/></title>
  <link href="<c:url value="/webjars/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet">
</head>

<body>
<div class="card">
  <div class="card-body">
    <h2>
      <spring:message
          code="welcome.message"
          text="Welcome to Court Reservation System"
      />
    </h2>
    Today is <javatime:format value="${today}" pattern="yyyy-MM-dd"/>.
    <hr/>
    <h5>handlingTime: ${handlingTime} ms</h5>
    <br/>
    <h5>Locale: ${pageContext.response.locale}</h5>
  </div>
</div>
</body>
</html>
