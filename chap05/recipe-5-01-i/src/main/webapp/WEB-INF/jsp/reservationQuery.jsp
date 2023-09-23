<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>

<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Reservation Query</title>
  <link href="<c:url value="/webjars/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet">
</head>

<body>

<div class="card">
  <div class="card-body">
    <form method="post">
      <div class="input-group mb-3 w-50">
        <span class="input-group-text" id="inputGroup-sizing-sm">Court Name</span>
        <input class="form-control w-50" type="text" name="courtName" value="${courtName}"/>
        <button class="form-control btn btn-sm btn-secondary" type="submit" value="Query">
          <span role="status">Query</span>
          <span class="spinner-border spinner-border-sm d-none" aria-hidden="true"></span>
        </button>
      </div>
    </form>


    <table class="table">
      <tr>
        <th>Court Name</th>
        <th>Date</th>
        <th>Hour</th>
        <th>Player</th>
      </tr>
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
    </table>
  </div>
</div>

<script>
  document.querySelector('form').addEventListener('submit', function(event) {
    event.preventDefault();
    document.querySelector('.spinner-border').classList.remove('d-none');
    this.querySelector('button[type="submit"]').disabled = true;
    this.submit();
  });
</script>

</body>
</html>
