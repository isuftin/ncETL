<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
	"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <jsp:include page="template/head.jsp"></jsp:include>
		<jsp:include page="template/css.jsp"></jsp:include>
		<jsp:include page="template/scripts.jsp">
			<jsp:param value="js/ui/ingest.js" name="UIScriptFile"/>
		</jsp:include>
    </head>
    <body>
    <div id="decorate" class="x-hidden"></div>
	<div id="dummy-delete-me" class="x-hidden"></div>
	<div id="decorateContent" class="x-hidden"></div>
	<div id="tempDiv" class="x-hidden"></div>
    </body>
</html>
