
    
    <%@page pageEncoding="UTF-8" isErrorPage="true" contentType="text/html" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet"
    href="<c:url value="/form.css"/>" /> 
     <link href="https://fonts.googleapis.com/css?family=Poppins" rel="stylesheet">
    <title>Java EE</title>

</head>
<body>
<header id="bandeau">Liste des factures</header>
<div id="">

<table id="tablefac">
	<thead title="Numéro Facture"></thead>
 		<c:forEach items="${factures}" var="fact" >
			<tr>
				<td>
 <a href="" id="tablefacnum">n° <c:out       value="${fact.faNumero}"/></a>
  				</td>
  						<td>
 <a href="" id="tablefacdate"> -  <c:out       value="${fact.faDate}"/></a>
  				</td>
  				<td>
  <a href="<%=request.getContextPath()+"/pdf?id="%><c:out       value="${fact.faNumero}"/>" id="buttontelec"> Télécharger pdf </a>
  				</td>
  			</tr>
 		</c:forEach>
</table>

	<div id="retourblock">
	  	<a href="<c:url value="/accueil"/>">Retour à l'accueil
	  	</a>  
	</div>
 </div>
</body>
</html>