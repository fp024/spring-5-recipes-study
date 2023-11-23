<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.servletContext.contextPath}"/>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="utf-8">
  <title>To-do List</title>
  <link type="text/css" rel="stylesheet"
        href="${contextPath}/webjars/semantic/dist/semantic.min.css"/>
</head>

<body>
<div class="ui container">
  <div class="ui inverted menu">
    <a class="item" href="${contextPath}/todos">To-dos</a>
    <div class="right menu" sec:authorize="isAuthenticated()">
      <div class="item">
        <form action="${contextPath}/logout" method="post">
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          <button class="ui small red basic compact button">Logout</button>
        </form>
      </div>
    </div>
  </div>
  <!--/* 목록 페이지가 항상 로그인을 요구해서 익명인 경우가 없음. */-->
  <sec:authorize access="isAuthenticated()">
    <h4>To-dos for <sec:authentication property="name"/></h4>
    <!--/* 보유한 권한 목록 표시 */-->
    <sec:authentication property="authorities" var="authorities"/>
    <ul>
      <c:forEach items="${authorities}" var="authority">
        <li>${authority.authority}</li>
      </c:forEach>
    </ul>
  </sec:authorize>

  <table class="ui celled table">
    <thead>
    <tr>
      <th class="three wide">Owner</th>
      <th class="five wide">To-do</th>
      <th class="two wide">Completed</th>
      <th class="six wide">&nbsp;</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${todos}" var="todo">
      <tr>
        <td>${todo.owner}</td>
        <td>${todo.description}</td>
        <td>${todo.completed}</td>
        <td>
          <c:if test="${!todo.completed}">
            <form action="${contextPath}/todos/${todo.id}/completed" method="post" style="float: left;">
              <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
              <input type="hidden" name="_method" value="PUT"/>
              <button class="ui mini green icon button"><i class="check circle icon"></i></button>
            </form>
          </c:if>

          <sec:authorize access="hasAuthority('ADMIN')">
            <form action="${contextPath}/todos/${todo.id}" method="post" style="float: left;">
              <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
              <input type="hidden" name="_method" value="DELETE"/>
              <button class="ui mini red icon button"><i class="remove circle icon"></i></button>
            </form>
          </sec:authorize>
        </td>

      </tr>
    </c:forEach>

    <tr>
      <td colspan="4">
        <a class="ui mini icon button" href="${contextPath}/todos/new">New To-do <i class="add circle icon"></i></a>
      </td>
    </tr>
    </tbody>
  </table>
</div>
</body>
</html>

