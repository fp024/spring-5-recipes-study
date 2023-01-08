<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>

<html>
  <head>
    <title>Welcome</title>
  </head>

  <body>
    <h2>Welcome to Court Reservation System</h2>
    Today is <javatime:format value="${today}" pattern="yyyy-MM-dd" />.
    <hr />
    <h2>handlingTime: ${handlingTime} ms</h2>
  </body>
</html>
