<%@page pageEncoding="UTF-8" isErrorPage="true" contentType="text/html" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
  	<meta charset="UTF-8">
    <title>Java EE</title>
  </head>
  <body>
  <div id="succes">
	 <p align="center"><c:out value="${message}"></c:out></p>
 </div>
  	<a href="<c:url value="/accueil"/>">Retour à l'accueil</a>  
  </body>
</html>