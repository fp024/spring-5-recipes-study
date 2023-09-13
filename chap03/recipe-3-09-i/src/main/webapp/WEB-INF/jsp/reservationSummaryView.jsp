<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>

<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Reservation Summary</title>
  <link href="webjars/bootstrap/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
<div class="card">
  <div class="card-body">
    <table class="table">
      <thead>
      <tr>
        <th>Court Name</th>
        <th>Date</th>
        <th>Hour</th>
        <th>Player</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach items="${reservations}" var="reservation">
        <tr>
          <td>${reservation.courtName}</td>
          <td>
            <javatime:format value="${reservation.date}" pattern="yyyy-MM-dd"/>
          </td>
          <td>${reservation.hour}</td>
          <td>${reservation.player.name}</td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
    <hr/>
    <h5>handlingTime: ${handlingTime} ms</h5>
  </div>
</div>

</body>
</html>
