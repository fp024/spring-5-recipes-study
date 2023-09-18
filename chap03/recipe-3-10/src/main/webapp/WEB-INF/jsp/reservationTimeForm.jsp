<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Reservation Time Form</title>
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

        <div class="mb-3">
          <div class="input-group">
            <span class="input-group-text">From Date</span>
            <form:input path="fromDate" type="date" cssClass="form-control"/>
          </div>
          <form:errors element="div" path="fromDate" cssClass="error"/>
        </div>

        <div class="mb-3">
          <div class="input-group">
            <span class="input-group-text">To Date</span>
            <form:input path="toDate" type="date" cssClass="form-control"/>
          </div>
          <form:errors element="div" path="toDate" cssClass="error"/>
        </div>

        <div class="input-group mb-3 w-50">
          <div class="input-group">
            <span class="input-group-text">Period</span>
            <form:select path="period" items="${periods}"
                         cssClass="form-select"
            />
          </div>
          <form:errors element="div" path="period" cssClass="error"/>
        </div>

        <div class="input-group mb-3">
          <div class="input-group">
            <span class="input-group-text">Hour</span>
            <form:input path="hour" cssClass="form-control"/>
          </div>
          <form:errors element="div" path="hour" cssClass="error"/>
        </div>

        <div class="input-group mb-3">
          <input type="hidden" value="1" name="_page">
          <div class="btn-group">
            <input class="btn btn-outline-secondary" type="submit" value="Previous" name="_target0"/>
            <input class="btn btn-outline-primary" type="submit" value="Next" name="_target2"/>
            <input class="btn btn-outline-danger" type="submit" value="Cancel" name="_cancel"/>
          </div>
        </div>
      </div>
    </form:form>
  </div>
</div>
</body>
</html>

