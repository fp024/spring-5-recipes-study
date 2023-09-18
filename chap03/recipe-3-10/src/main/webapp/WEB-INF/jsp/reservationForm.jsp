<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Reservation Form</title>
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
      <form:errors element="div" path="*" cssClass="alert alert-danger"/>

      <div class="input-group w-75">

        <div class="input-group mb-3">
          <div class="input-group mb-1">
            <span class="input-group-text">Court Name</span>
            <form:input path="courtName" cssClass="form-control"/>
          </div>
          <form:errors element="div" path="courtName" cssClass="error"/>
        </div>

        <div class="mb-3">
          <div class="input-group">
            <span class="input-group-text">Date</span>
            <form:input path="date" type="date" cssClass="form-control"/>
          </div>
          <form:errors element="div" path="date" cssClass="error"/>
        </div>


        <div class="input-group mb-3">
          <div class="input-group">
            <span class="input-group-text">Hour</span>
            <form:select path="hour" cssClass="form-select-sm">
              <c:forEach var="i" begin="0" end="23">
                <form:option value="${i}" label="${i}"/>
              </c:forEach>
            </form:select>
          </div>
          <form:errors element="div" path="hour" cssClass="error"/>
        </div>

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

        <div class="input-group mb-3 w-50">
          <div class="input-group">
            <span class="input-group-text">Sport Type</span>
            <form:select path="sportType" items="${sportTypes}"
                         itemValue="id" itemLabel="name"
                         cssClass="form-select"
            />
          </div>
          <form:errors element="div" path="sportType" cssClass="error"/>
        </div>

        <div class="input-group mb-3">
          <input class="btn btn-secondary" type="submit"/>
        </div>
      </div>
    </form:form>
  </div>
</div>
<hr/>
<h5>handlingTime: ${handlingTime} ms</h5>
</body>
</html>

