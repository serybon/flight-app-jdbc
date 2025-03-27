<%@ page import="by.bnd.je.jdbc.service.TicketService" %>
<%@ page import="by.bnd.je.jdbc.dto.TicketDto" %><%--
  Created by IntelliJ IDEA.
  User: bnd
  Date: 26.03.2025
  Time: 15:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>--%>
<html>
<head>
    <title>Hello JSP</title>
</head>
<body>
<h1>Купленные билеты:</h1>
<ul>
    <c:if test="${not empty requestScope.tickets}">
    <c:forEach var="ticket" items="${requestScope.tickets}">
        <li>
                ${fn:toLowerCase(ticket.seatNo())}
        </li>
    </c:forEach>
    </c:if>
</ul>
<ul>
    JSP
</ul>
</body>
</html>
