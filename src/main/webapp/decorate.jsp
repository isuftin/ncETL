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
        <%@include file="template/head.jspf" %>
		<%@include file="template/css.jspf" %>
		<%@include file="template/scripts.jspf" %>

		<script type="text/javascript" language="JavaScript" src="js/ext/RowEditor.js"></script>
		<script type="text/javascript" language="JavaScript" src="js/ext/CheckColumn.js"></script>
		<script type="text/javascript" language="JavaScript" src="js/ext/TableGrid.js"></script>
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
