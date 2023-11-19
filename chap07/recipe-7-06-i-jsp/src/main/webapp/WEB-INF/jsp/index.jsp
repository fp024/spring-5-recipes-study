<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.servletContext.contextPath}" />
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>💡 레시피 7-06-i 뷰에서 보안 처리하기 - JSP</title>
  <link href="${contextPath}/webjars/bootstrap/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="card">
  <div class="card-header">
    <h2>레시피 7-06-i 뷰에서 보안 처리하기 - Thymeleaf : 테스트 URL</h2>
  </div>
  <div class="card-body">
    <h5 class="card-title">레시피 7-06-i 뷰에서 보안 처리하기 - Thymeleaf</h5>
    <ul>
      <li><a href="${contextPath}/login">로그인 페이지</a></li>
      <li><a href="${contextPath}/todos">URL 접근 보안이 적용된 Todo 메인 페이지</a></li>
    </ul>
  </div>
</div>

</body>
</html>