<%-- ExtJS Scripts --%>
<script type="text/javascript" src="js/ext/ext-all-debug-w-comments.js"></script>
<script type="text/javascript">
<%-- Path to the blank image should point to a valid location on your server --%>
Ext.BLANK_IMAGE_URL = 'images/s.gif';
</script>

<%-- Added Components --%>
<script type="text/javascript" src="js/component/SpecProxy.js"></script>
<script type="text/javascript" src="js/component/SpecReader.js"></script>
<script type="text/javascript" src="js/component/KvpWriter.js"></script>

<%-- Global ncETL script.  Used for logging/notifying --%>
<script type="text/javascript" src="js/ui/ncetl.js"></script>

<%-- Models --%>
<script type="text/javascript" src="js/model/Ingestor.js"></script>
<script type="text/javascript" src="js/model/Catalog.js"></script>

<%-- Ext.onReady UI Script --%>
<script type="text/javascript" src='${param["UIScriptFile"]}'></script>
