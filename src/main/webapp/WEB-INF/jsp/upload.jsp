<%@page pageEncoding="UTF-8" isErrorPage="true" contentType="text/html" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
  	<meta charset="UTF-8">
    <title>Java EE</title>
  <link type="text/css" rel="stylesheet" href="<c:url value="/form.css"/>" /> 
  <link href="https://fonts.googleapis.com/css?family=Poppins" rel="stylesheet">
  </head>
  <body>
<header id="bandeau">Enregistrement des factures</header>

	
		<form enctype="multipart/form-data" method="post" id="formulaire">
		<label for="">Veuillez charger votre facture : </label>
		 <input type="file" name="file"><br/>
		 <label for="">Veuillez entrer la destination </label>
		 <input type="text" value="/tmp" name="destination"/>
		
		 <input type="submit" value="Upload">
		 </form>
		


	<div id="retourblock">
	  	<a href="<c:url value="/accueil"/>">Retour à l'accueil
	  	</a>  
	</div>
  
  </body>
</html>