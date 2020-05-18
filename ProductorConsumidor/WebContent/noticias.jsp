<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import ="java.util.ArrayList"%>
    <%@ page import ="ual.dss.core.Noticia"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Noticias</title>
</head>
<body>

<form action='http://localhost:8080/ProductorConsumidor/servlet' method='get'>
                        <div class="form-row">
                            <div class="name">Descripción corta</div>
                            <div class="value">
                                <input class="input--style-6" type="text" name="shortDescription">
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="name">Descripción larga</div>
                            <div class="value">
                                <div class="input-group">
                                    <textarea class="textarea--style-6" name="largeDescription" placeholder="Descripción larga de la noticia"></textarea>
                                </div>
                            </div>
                        </div>
                    
	                </div>
	                <div class="card-footer">
	                    <input class="btn btn--radius-2 btn--blue-2" type="submit" value='Crear noticia' name='action'/>
	                </div>
	                <div class="errorMessage">${resultError}</div>
                    <div class="okMessage">${resultOK}</div>
                </form>
    <div>AA = ${message}</div>


</body>
</html>