<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib
prefix="javatime" uri="http://sargue.net/jsptags/time" %>

<html>
  <head>
    <title>Reservation Summary</title>
  </head>

  <body>
    <table border="1">
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
            <javatime:format value="${reservation.date}" pattern="yyyy-MM-dd" />
          </td>
          <td>${reservation.hour}</td>
          <td>${reservation.player.name}</td>
        </tr>
      </c:forEach>
    </table>
    <hr />
    <h2>handlingTime: ${handlingTime} ms</h2>
  </body>
</html>
