<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import ="java.util.ArrayList"%>
    <%@ page import ="ual.dss.core.Noticia"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
												  ArrayList<Noticia> noticias = (ArrayList<Noticia>) request.getAttribute("noticias"); 
													
												  if(noticias.isEmpty())
												  {}
%>
</body>
</html>