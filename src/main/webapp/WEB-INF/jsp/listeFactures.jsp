
    
    <%@page pageEncoding="UTF-8" isErrorPage="true" contentType="text/html" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet"
    href="<c:url value="/form.css"/>" /> 
    <title>Java EE</title>

</head>
<body>
<div id="">
<h3>Liste des Factures :</h3>
<table border="0.5">
	<thead title="Numéro Facture"></thead>
 		<c:forEach items="${factures}" var="fact">
			<tr>
				<td>
 <a href="">n° <c:out       value="${fact.faNumero}"/></a>
  				</td>
  						<td>
 <a href=""> -  <c:out       value="${fact.faDate}"/></a>
  				</td>
  				<td>
  <a href="<%=request.getContextPath()+"/pdf?id="%><c:out       value="${fact.faNumero}"/>"> - Télécharger pdf </a>
  				</td>
  			</tr>
 		</c:forEach>
</table>

 </div>
</body>
</html>