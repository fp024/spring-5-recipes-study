<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Reservation Not Available</title>
  <link href="webjars/bootstrap/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="card">
  <div class="card-body">
    Your reservation for ${exception.courtName} is not available on
    <fmt:formatDate value="${exception.date}" pattern="yyyy-MM-dd"/> at
    ${exception.hour}:00.
  </div>
</div>
</body>
</html>
