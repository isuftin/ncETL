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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Data Cleanup &amp; Publishing Toolkit</title>
		<meta name="title" content="DCPT">
        <meta name="description" content="DCPT">
        <meta name="author" content="Jordan Walker">
        <meta name="keywords" content="Department of the Interior,hazards, hazard,earth, USGS,U.S. Geological Survey, water, earthquakes, volcanoes, volcanos,
			  tsunami, tsunamis, flood, floods, wildfire, wildfires, natural hazards, environment, science, jobs, USGS Library, Ask USGS, maps, map">
        <meta name="date" content="20110209">
        <meta name="revised date" content="20110209">
        <meta name="reviewed date" content="20110209">
        <meta name="language" content="EN">
        <meta name="expiration date" content="Never">
		<link rel="stylesheet" type="text/css" href="js/ext/resources/css/ext-all.css" />
		<link rel="stylesheet" type="text/css" href="css/dcpt.css" />
		<link rel="stylesheet" type="text/css" href="http://www.ngdc.noaa.gov/ngdcbasicstyle.css" title="default">

		<script type="text/javascript" language="JavaScript" src="js/ext/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" language="JavaScript" src="js/ext/ext-all-debug.js"></script>

		<script type="text/javascript" language="JavaScript" src="js/ext/RowEditor.js"></script>
		<script type="text/javascript" language="JavaScript" src="js/ext/CheckColumn.js"></script>
		<script type="text/javascript" language="JavaScript" src="js/ext/TableGrid.js"></script>
		<script type="text/javascript" language="JavaScript" src="js/ui/ingest.js"></script>
		<script type="text/javascript" language="JavaScript" src="js/ui/decorate.js"></script>
    </head>
    <body>
        <script type="text/javascript">
			// Path to the blank image should point to a valid location on your server
			Ext.BLANK_IMAGE_URL = 'images/s.gif';

			Ext.onReady(function(){
				var main = new Ext.TabPanel({
					title : "ncISO",
					id : 'nciso',
					activeTab : 0,
					//renderTo: 'decorate',
					region: 'center',
					items: [
						{
							title : 'ncIso Metadata',
							layout : 'fit',
							closable : 'true',
							autoEl : {
								tag : 'p',
								html : 'This area shows the metadata associated with a dataset in a manner that scores the richness of the metadata.'
							}
						}
					]
				});

				var store = new Ext.data.Store({
					url : 'datasets',
					reader: new Ext.data.XmlReader({
					   // records will have an "Item" tag
					   record: 'Dataset',
					   id: 'path',
					   totalRecords: '@total'
					}, [
					   'path'
					])
				});

				var datasets = new Ext.grid.GridPanel({
					region: 'west',
					layout: 'fit',
					store: store,
					columns: [{header: "Dataset", width: 246, dataIndex: 'path', sortable: true}],
					width: 250,
					height: 500,
					split: true,
					selModel: new Ext.grid.RowSelectionModel({
						singleSelect: true
					}),
					listeners: {
						rowclick: function(thisGrid, rowIndex, event) {
							var record = thisGrid.selModel.getSelected();
							var dataset = record.get('path');
							loadContent(dataset);
						}
					}
				});

				var statusBar = new Ext.Panel({
					region: 'south',
					layout: 'fit',
					id: 'statusBar'
				});

				var vp = new Ext.Viewport({
					layout : 'border',
					id : 'viewport',
					renderTo : document.body,
					items : [main, datasets, statusBar]
				});
				store.load();
			}); //end onReady
        </script>

		<div id="decorate" class="x-hidden"></div>

		<div id="decorateContent" class="x-hidden"></div>
    </body>
</html>
