<%@ page import="by.bnd.je.jdbc.service.TicketService" %>
<%@ page import="by.bnd.je.jdbc.dto.TicketDto" %><%--
  Created by IntelliJ IDEA.
  User: bnd
  Date: 26.03.2025
  Time: 15:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello JSP</title>
</head>
<body>
<h1>Купленные билеты:</h1>
<ul>
    <%
        TicketService ticketService = TicketService.getInstance();
        Long flightId = Long.valueOf(request.getParameter("flightId"));
        for (TicketDto ticketDto : ticketService.findAllByFlightId(flightId)){
            out.write(String.format("<li>%s</li>", ticketDto.seatNo()));
        }
    %>
</ul>
<ul>
    JSP
</ul>
</body>
</html>
