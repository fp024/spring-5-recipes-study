<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Reservation Player Form</title>
  <link href="<c:url value="/webjars/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet">
  <style>
    .error {
      color: #ff0000;
      font-weight: bold;
    }
  </style>
</head>

<body>
<div class="card">
  <div class="card-body">
    <form:form method="post" modelAttribute="reservation">
      <div class="input-group w-75">

        <div class="input-group mb-3">
          <div class="input-group">
            <span class="input-group-text">Player Name</span>
            <form:input path="player.name" cssClass="form-control"/>
          </div>
          <form:errors element="div" path="player.name" cssClass="error"/>
        </div>

        <div class="input-group mb-3">
          <div class="input-group">
            <span class="input-group-text">Player Phone</span>
            <form:input path="player.phone" cssClass="form-control"/>
          </div>
          <form:errors element="div" path="player.phone" cssClass="error"/>
        </div>

        <div class="input-group mb-3">
          <input type="hidden" value="2" name="_page">
          <div class="btn-group">
            <input class="btn btn-outline-secondary" type="submit" value="Previous" name="_target1"/>
            <input class="btn btn-outline-primary" type="submit" value="Next" name="_finish"/>
            <input class="btn btn-outline-danger" type="submit" value="Cancel" name="_cancel"/>
          </div>
        </div>
      </div>
    </form:form>
  </div>
</div>
</body>
</html>

