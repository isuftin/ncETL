<%-- 
    Document   : index
    Created on : Feb 9, 2011, 4:53:57 PM
    Author     : jwalker
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
	"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <jsp:include page="template/head.jsp"></jsp:include>
		<jsp:include page="template/css.jsp"></jsp:include>
		<jsp:include page="template/scripts.jsp">
			<jsp:param value="js/ui/decorate.js" name="UIScriptFile"/>
		</jsp:include>

		<script type="text/javascript" language="JavaScript" src="js/ext/XmlTreeLoader.js"></script>
		<script type="text/javascript" language="JavaScript" src="js/ui/decorate.js"></script>
		<script type="text/javascript" language="JavaScript" src="js/ui/wrapper.js"></script>
    </head>
    <body>
		<div id="decorate" class="x-hidden"></div>
		
		<div id="decorateContent" class="x-hidden"></div>
		<div id="tempDiv" class="x-hidden"></div>
    </body>
</html>
