<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
  <head>
    <title><spring:message code="welcome.title" text="Welcome"/></title>
  </head>

  <body>
    <h2>
      <spring:message
        code="welcome.message"
        text="Welcome to Court Reservation System"
      />
    </h2>
    Today is <javatime:format value="${today}" pattern="yyyy-MM-dd" />.
    <hr />
    <h2>handlingTime: ${handlingTime} ms</h2>
    <br />
    <h2>Locale: ${pageContext.response.locale}</h2>
  </body>
</html>
