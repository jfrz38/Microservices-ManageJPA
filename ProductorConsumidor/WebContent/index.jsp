<%@ page language='java' contentType='text/html; charset=UTF-8'
    pageEncoding='UTF-8'%>
    <%@ page import="java.text.SimpleDateFormat"%>
    <%@ page import="java.util.Date"%>
<html>
    <head>
       <meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1'>
       <meta name='Author' content='Luis Iribarne'>
       <meta name='Description' content='University of Almeria (Spain)'>
       <title>Productor noticias</title>
       <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i,800,800i" rel="stylesheet">
    	<link href="css/productor.css" rel="stylesheet" media="all">
    </head>
    <body>
    <!--  POST noticias -->
    <div class="page-wrapper bg-dark p-t-100 p-b-50">
        <div class="wrapper wrapper--w900">
            <div class="card card-6">
                <div class="card-heading">
                    <h2 class="title">Insertar noticia</h2>
                </div>
                <div class="card-body">
                    <form action='http://localhost:8080/ProductorConsumidor/servlet' method='post'>
                        <div class="form-row">
                            <div class="name">Título</div>
                            <div class="value">
                                <input class="input--style-6" type="text" name="shortDescription">
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="name">Descripción</div>
                            <div class="value">
                                <div class="input-group">
                                    <textarea class="textarea--style-6" name="largeDescription" placeholder="Descripción de la noticia"></textarea>
                                </div>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="name">URL</div>
                            <div class="value">
                                <input class="input--style-6" type="text" name="url">
                            </div>
                        </div>
                        <div class="form-row">
                            <p style="right: 0;">Se creará la noticia a fecha de hoy: <%= new SimpleDateFormat("dd/MM/yyyy").format(new Date())%></p>
                        </div>
                    
	                </div>
	                <div class="card-footer">
	                    <input class="btn btn--radius-2 btn--blue-2" type="submit" value='Crear noticia' name='action'/>
	                </div>
	                <div class="errorMessage">${resultError}</div>
                    <div class="okMessage">${resultOK}</div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>