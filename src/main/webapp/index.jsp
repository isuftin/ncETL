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
		<script type="text/javascript" language="JavaScript" src="js/ext/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" language="JavaScript" src="js/ext/ext-all.js"></script>
		<script type="text/javascript" language="JavaScript" src="js/ext/RowEditor.js"></script>
		<script type="text/javascript" language="JavaScript" src="js/ext/CheckColumn.js"></script>
    </head>
    <body>
        <script type="text/javascript">
			// Path to the blank image should point to a valid location on your server
			Ext.BLANK_IMAGE_URL = 'images/s.gif';

			Ext.onReady(function(){

				var Ingestor = Ext.data.Record.create([{
//						name: 'id',
//						type: 'int'
//					},{
						name: 'ftpLocation',
						type: 'string'
					}, {
						name: 'rescanEvery',
						type: 'int'
					}, {
						name: 'filePattern',
						type: 'string'
					},{
						name: 'lastSuccess',
						type: 'date',
						dateFormat: 'c'
					},{
						name: 'username',
						type: 'string'
					},{
						name: 'password',
						type: 'string'
					},{
						name: 'active',
						type: 'bool'
					}]);

				var proxy = new Ext.data.HttpProxy({
					api: {
						read    : 'ingestors?action=read',
						create  : 'ingestors?action=create',
						update  : 'ingestors?action=update',
						destroy : 'ingestors?action=destroy'
					}
				});

				var store = new Ext.data.XmlStore({
					fields : ['id', 'ftpLocation', 'rescanEvery', 'filePattern', 'lastSuccess' , 'username', 'password', 'active'],
					proxy : proxy,
					root : 'ingestors',
					totalProperty : 'count',
					successProperty : 'success',
					autoLoad : true,
					autoSave : false,
					autoCreate : true,
					autoDestroy : true,
					idProperty : 'id',
					writer : writer
				});

				var writer = new Ext.data.XmlWriter({
					writeAllFields : true
				});

//				var jsonReader = new Ext.data.JsonReader({
//					idProperty : 'ftpLocation',
//					root : 'rows',
//					totalProperty : 'results',
//					fields : [
//						{name : 'ftpLocation', mapping : 'ftpLocation'},
//						{name : 'rescanEvery', mapping : 'rescanEvery'},
//						{name : 'filePattern', mapping : 'filePattern'},
//						{name : 'lastSuccess', mapping : 'lastSuccess'},
//						{name : 'username', mapping : 'username'},
//						{name : 'password', mapping : 'password'},
//						{name : 'active', mapping : 'active'}
//					]
//				});


				var editor = new Ext.ux.grid.RowEditor({
					saveText: 'Update'
				});

				var tabs = new Ext.TabPanel({
					title : "Dataset options",
					renderTo : document.body,
					activeTab : 0,
					defaults : {autoScroll: true},
					autoHeight : true,
					items : [{
							title : "Ingest",
							html : '<div id="ingest"></div>'
						},{
							title : "Verify",
							html : '<div id="verify"></div>'
						},{
							title : "Cleanup",
							html : '<div id="cleanup"></div>'
						},{
							title : "Decorate",
							html : '<div id="decorate"></div>'
						},{
							title : "Publish",
							html : '<div id="publish"></div>'
						}
					]
				});

				var grid = new Ext.grid.EditorGridPanel({
					title : "Ingestors",
					renderTo : 'ingest',
					store : store,
					autoHeight : true,
					tbar: [{
							//iconCls: 'icon-user-add',
							text: 'Add',
							handler: function(){
								var e = new Ingestor({
									ftpLocation: '',
									rescanEvery: '300000',
									filePattern: '.*',
									lastSuccess: '2011-01-01',
									username: '',
									password: '',
									active: false
								});
								editor.stopEditing();
								store.insert(0, e);
								grid.getView().refresh();
								grid.getSelectionModel().selectRow(0);
								editor.startEditing(0);
							}
						},{
							ref: '../removeBtn',
							//iconCls: 'icon-user-delete',
							text: 'Remove',
							disabled: true,
							handler: function(){
								editor.stopEditing();
								var s = grid.getSelectionModel().getSelections();
								for(var i = 0, r; r = s[i]; i++){
									store.remove(r);
								}
							}
						}],
					colModel : new Ext.grid.ColumnModel({
						defaults : {
							width : 120,
							sortable : true
						},
						columns : [
//							{
//								id : 'id',
//								dataIndex : 'id',
//								hidden : true
//							},
							{
								//id : 'ftpLocation',
								header : 'Location',
								xtype : 'gridcolumn',
								dataIndex : 'ftpLocation',
								editor :
									{
									xtype: 'textfield',
									allowBlank: false,
									vtype: 'url'
								}
							},{
								header : 'Rescan (ms)',
								xtype : 'numbercolumn',
								format : '0',
								dataIndex : 'rescanEvery',
								editor:
									{
									xtype: 'numberfield',
									allowBlank: false
								}
							},{
								header : 'Pattern (regex)',
								xtype : 'gridcolumn',
								dataIndex : 'filePattern',
								editor :
									{
									xtype: 'textfield',
									allowBlank: false
								}
							},{
								header : 'Username',
								xtype : 'gridcolumn',
								dataIndex : 'username',
								editor :
									{
									xtype: 'textfield',
									allowBlank: true
								}
							},{
								header : 'Password',
								//xtype : 'gridcolumn',
								//inputType : 'password',
								dataIndex : 'password',
								editor :
									{
									xtype: 'textfield',
									inputType: 'password'
								},
								renderer : function(value){
									return (value.length > 0) ? "******" : "";
								}
							},{
								header : 'Active',
								xtype : 'checkcolumn',
								dataIndex : 'active'
							},{
								header : 'Last Success',
								xtype : 'datecolumn',
								dataIndex : 'lastSuccess',
								format : 'c',
								editable : false
							}
						]
					}),
					viewConfig : {
						forceFit: true
					},
					sm : new Ext.grid.RowSelectionModel({singleSelect : true})
				});

			}); //end onReady
        </script>
    </body>
</html>
