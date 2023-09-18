<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>😈 Reservation Summary Page Error 😈</title>
  <link href="<c:url value="/webjars/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet">
</head>
<body>
<div class="card">
  <div class="card-body">
    <div class="alert alert-danger">
      ${exception.message}
    </div>
  </div>
</div>
</body>
</html>
