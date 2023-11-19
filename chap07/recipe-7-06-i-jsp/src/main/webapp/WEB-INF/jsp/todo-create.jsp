<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.servletContext.contextPath}"/>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="utf-8">
  <title>Message </title>
  <link type="text/css" rel="stylesheet"
        href="${contextPath}/webjars/Semantic-UI/semantic.min.css"/>
</head>


<body>
<div class="ui container">
  <h4>New To-do</h4>
  <form:form method="POST" modelAttribute="todo" action="${contextPath}/todos" class="ui form">
    <%-- Spring Form Tag를 사용한 부분은 csrf hidden이 자동 추가됨 --%>
    <fieldset>
      <legend>To-do</legend>
      <div class="field">
        <label>To-do</label>
        <form:input path="description"/>
        <form:errors element="div" cssClass="ui pointing red basic label" path="description"/>
      </div>
      <div class="field">
        <label>Completed</label>
        <form:checkbox path="completed"/>
      </div>
      <button class="ui mini primary button">Save <i class="send icon"></i></button>
    </fieldset>
  </form:form>
</div>
</body>
</html>

</html>