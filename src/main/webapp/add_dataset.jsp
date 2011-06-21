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
    </head>
    <body>
        <script type="text/javascript">
            // Path to the blank image should point to a valid location on your server
            Ext.BLANK_IMAGE_URL = 'images/s.gif';

            Ext.onReady(function(){
                var main = new Ext.Panel({
                    title : 'Add A Dataset',
                    layout: 'form',
                    region: 'center',
                    //activeTab : 0,
                    margins: '2 2 2 2',
                    border: false,
                    width: 320,
                    defaults : {
                        autoScroll: true
                    },
                    defaultType: 'textfield',
                    items : [{
                            fieldLabel: 'Name',
                            name: 'name',
                            allowBlank: false
                        },{
                            fieldLabel: 'Description',
                            name: 'description',
                            allowBlank: false
                        },{
                            fieldLabel: 'Documentation: Link',
                            name: 'doc-xlink',
                            allowBlank: true
                        },{
                            fieldLabel: 'Documentation: Rights',
                            name: 'doc-rights',
                            allowBlank: true
                        },{
                            fieldLabel: 'Documentation: Summary',
                            name: 'doc-summary',
                            allowBlank: true
                        },{
                            fieldLabel: 'Documentation: Reference',
                            name: 'doc-reference',
                            allowBlank: true
                        },{
                            xtype: 'checkboxgroup',
                            fieldLabel: 'Services',
                            columns: 1,
                            items: [
                                {boxLabel: 'OpenDAP', name: 'services-opendap', checked: true},
                                {boxLabel: 'NetCDF Subset', name: 'services-netcdfsubset', checked: true},
                                {boxLabel: 'HTTP Download', name: 'services-httpdownload', checked: true},
                                {boxLabel: 'WCS', name: 'services-wcs', checked: true},
                                {boxLabel: 'WMS', name: 'services-wms', checked: true},
                                {boxLabel: 'NcISO-NCML', name: 'services-nciso-ncml', checked: true},
                                {boxLabel: 'NcISO-ISO', name: 'services-nciso-iso', checked: true},
                                {boxLabel: 'NcISO-UDDC', name: 'services-nciso-uddc', checked: true}
                            ]
                        },{
                            xtype: 'combo',
                            name: 'dataformat',
                            fieldLabel: 'Data Format',
                            mode: 'local',
                            store: new Ext.data.ArrayStore({
                                id: 0,
                                fields: [
                                    'dataformat',  
                                    'displayText'
                                ],
                                data: [
                                    ['NONE', 'NONE'],
                                    ['BUFR', 'BUFR'],
                                    ['ESML', 'ESML'],
                                    ['GEMPAK', 'GEMPAK'],
                                    ['GINI', 'GINI'],
                                    ['GRIB-1', 'GRIB-1'],
                                    ['GRIB-2', 'GRIB-2'],
                                    ['HDF4', 'HDF4'],
                                    ['HFD5', 'HFD5'],
                                    ['NetCDF', 'NetCDF'],
                                    ['NEXRAD2', 'NEXRAD2'],
                                    ['NcML', 'NcML'],
                                    ['NIDS', 'NIDS'],
                                    ['McIDAS-AREA', 'McIDAS-AREA'],
                                    ['GIF', 'GIF'],
                                    ['JPEG', 'JPEG'],
                                    ['TIFF', 'TIFF'],
                                    ['PLAIN', 'PLAIN'],
                                    ['TSV', 'TSV'],
                                    ['XML', 'XML'],
                                    ['MPEG', 'MPEG'],
                                    ['QUICKTIME', 'QUICKTIME'],
                                    ['REALTIME', 'REALTIME'],
                                    ['OTHER/UNKNOWN', 'OTHER/UNKNOWN']
                                ]
                            }),
                            valueField: 'dataformat',
                            displayField: 'displayText',
                            triggerAction: 'all'
                        },{
                            fieldLabel: 'Keywords (csv)',
                            name: 'keywords',
                            allowBlank: false
                        },{
                            fieldLabel: 'Creator',
                            name: 'creator',
                            allowBlank: false
                        }
//                        ,{
//                            fieldLabel: 'Publisher',
//                            name: 'publisher',
//                            allowBlank: false
//                        }
                                                
                                                
                    ],
                    buttons: [{
                            text: 'Save'
                        },{
                            text: 'Cancel'
                        }]
                });
                main.render(document.body);
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
