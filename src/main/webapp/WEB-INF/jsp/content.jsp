<%--
  Created by IntelliJ IDEA.
  User: bnd
  Date: 26.03.2025
  Time: 21:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<span>CONTENT РУССКИЙ</span>
<p>Size: ${requestScope.flights.size()}</p>
<p>description: ${requestScope.flights.get(0).description()}</p>
<p>id: ${requestScope.flights[1].id()}</p>
<p>JSESSIONID: ${cookie.get("JSESSIONID")}</p>
<p>PARAM id: ${param.id}</p>
<p>HEADER id: ${header["cookie"]}</p>
<p>NOT EMPTY: ${not empty flights}</p>
</body>
</html>
