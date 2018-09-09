<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" rel="stylesheet" href="<c:url value="/form.css"/>" /> 

  <link href="https://fonts.googleapis.com/css?family=Poppins" rel="stylesheet">
<title>Accueil</title>
</head>
<body>
<header id="bandeau">Accueil</header>
<div id="accueil">

    <p id="retourblock"><a href="<c:url value="/chargementcsv"/>">Sauvegarder une facture en base</a></p>
    <p id="retourblock"><a href="<c:url value="/listeFacture"/>">Voir les factures existantes</a></p>

</div>
</body>
</html>