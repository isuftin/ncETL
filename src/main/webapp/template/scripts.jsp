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
<script type="text/javascript" src="js/component/shared/ModelFormGroup.js"></script>
<script type="text/javascript" src="js/component/shared/ModelFormPanel.js"></script>

<%-- Global ncETL script --%>
<script type="text/javascript" src="js/ui/ncetl.js"></script>

<%-- Models --%>
<script type="text/javascript" src="js/model/Access.js"></script>
<script type="text/javascript" src="js/model/Catalog.js"></script>
<script type="text/javascript" src="js/model/Contributor.js"></script>
<script type="text/javascript" src="js/model/ControlledVocabulary.js"></script>
<script type="text/javascript" src="js/model/Creator.js"></script>
<script type="text/javascript" src="js/model/Dataset.js"></script>
<script type="text/javascript" src="js/model/DateTypeFormatted.js"></script>
<script type="text/javascript" src="js/model/Documentation.js"></script>
<script type="text/javascript" src="js/model/GeospatialCoverage.js"></script>
<script type="text/javascript" src="js/model/Ingestor.js"></script>
<script type="text/javascript" src="js/model/Keyword.js"></script>
<script type="text/javascript" src="js/model/Project.js"></script>
<script type="text/javascript" src="js/model/Property.js"></script>
<script type="text/javascript" src="js/model/Publisher.js"></script>
<script type="text/javascript" src="js/model/Service.js"></script>
<script type="text/javascript" src="js/model/SpatialRange.js"></script>
<script type="text/javascript" src="js/model/TimeCoverage.js"></script>

<%-- Added UI Components --%>
<script type="text/javascript" src="js/component/IngestorGrid.js"></script>

<%-- Ext.onReady UI Script --%>
<script type="text/javascript" src='${param["UIScriptFile"]}'></script>
