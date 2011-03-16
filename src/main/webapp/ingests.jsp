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
				var main = new Ext.Panel({
					title : "Dataset options",
					layout: 'accordion',
					region: 'center',
					//activeTab : 0,
					margins: '2 2 2 2',
					border: false,
					defaults : {
						autoScroll: true
					},
					items : [{
							title : "Ingest",
							layout: 'fit',
							contentEl : "ingest"
						},{
							title : "Verify",
							layout: 'fit',
							contentEl : "verify"
						},{
							title : "Cleanup",
							layout: 'fit',
							contentEl : "cleanup"
						},{
							title : "Decorate",
							layout: 'fit',
							contentEl : "decorate"
						},{
							title : "Publish",
							layout: 'fit',
							contentEl : "publish"
						}
					]
				});

				var datasets = new Ext.Panel({
					region: 'west',
					width: 50,
					maxSize: 200,
					split: true,
					border: false,
					items: [
						{title: 'test'}
					]
				});

				var p = new Ext.Viewport({
					layout: 'border',
					renderTo : document.body,
					items: [main, datasets]
				});

				ingest();
				loadContent('/home/scratch/QPE.20040102.009.157.grb');
			}); //end onReady
        </script>
		<div id="ingest" class="x-hidden"></div>
		<div id="verify" class="x-hidden"></div>
		<div id="cleanup" class="x-hidden"></div>
		<div id="decorate" class="x-hidden"></div>
		<div id="publish" class="x-hidden"></div>

		<div id="decorateContent" class="x-hidden"></div>
    </body>
</html>
