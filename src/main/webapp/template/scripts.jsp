<%-- ExtJS Scripts --%>
<script type="text/javascript" src="js/ext/ext-all-debug-w-comments.js"></script>
<script type="text/javascript">
Ext.BLANK_IMAGE_URL = 'images/s.gif';<%-- Path to the blank image should point to a valid location on your server --%>
</script>

<%-- Added Shared Components --%>
<script type="text/javascript" src="js/component/shared/SpecProxy.js"></script>
<script type="text/javascript" src="js/component/shared/SpecReader.js"></script>
<script type="text/javascript" src="js/component/shared/KvpWriter.js"></script>
<script type="text/javascript" src="js/component/shared/ModelForm.js"></script>

<%-- Global ncETL script --%>
<script type="text/javascript" src="js/ui/ncetl.js"></script>

<%-- Models --%>
<script type="text/javascript" src="js/model/Ingestor.js"></script>
<script type="text/javascript" src="js/model/Catalog.js"></script>

<%-- Added UI Components --%>
<script type="text/javascript" src="js/component/IngestorGrid.js"></script>

<%-- Ext.onReady UI Script --%>
<script type="text/javascript" src='${param["UIScriptFile"]}'></script>
