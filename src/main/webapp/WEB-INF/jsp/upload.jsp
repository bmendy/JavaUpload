<%@page pageEncoding="UTF-8" isErrorPage="true" contentType="text/html" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
  	<meta charset="UTF-8">
    <title>Java EE</title>
    <style type="text/css">
    	form > div {
    		padding: .5em;
    	}
    	
    	div > label:first-child {
    		display: inline-block;
			min-width: 18em;
		}
		
		.error {
			color: red;
		}
    </style>
  </head>
  <body>


	
		<form enctype="multipart/form-data" method="post">
		<label for="">Veuillez charger votre facture : </label>
		 <input type="file" name="file">
		 <label for="">Veuillez entrer la destination </label>
		 <input type="text" value="/tmp" name="destination"/>
		
		 <input type="submit" value="Upload">
		 </form>
		


	<div>
	  	<a href="<c:url value="/accueil"/>">Retour à l'accueil</a>  
	</div>
  
  </body>
</html>