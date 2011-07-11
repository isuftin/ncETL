Ext.onReady(function() {

	var ingestStore = new Ext.data.Store({
		storeId : 'ingestStore',
		autoSync : false,
		autoLoad : true,
		model : "Ingestor"
	});

	var ingestGrid = new Ext.grid.Panel({
		title : "Ingestors",
		store : ingestStore,
		forceFit : true,
		plugins : [ new Ext.grid.plugin.RowEditing({errorSummary:false}) ],
		tbar : [ {
			// iconCls: 'icon-user-add',
			text : 'Add',
			handler : function() {
				var e = new Ingestor({
					name : '',
					ftpLocation : '',
					rescanEvery : '300000',
					fileRegex : '.*',
					successDate : '2011-01-01',
					successTime : '00:00:00',
					username : '',
					password : '',
					active : false
				});
				ingestStore.insert(0, [e]);
			}
		}, {
			ref : '../removeBtn',
			// iconCls: 'icon-user-delete',
			text : 'Remove',
			handler : function() {
				var s = ingestGrid.getSelectionModel().getSelections();
				ingestStore.remove(s);
			}
		}, {
			text : 'Save',
			handler : function() {
				ingestStore.sync();
			}
		} ],
		columns : [ {
			header : 'Name',
			dataIndex : 'name',
			xtype : 'gridcolumn',
			editor : {
				xtype : 'textfield',
				allowBlank : false
			}
		}, {
			header : 'Location',
			xtype : 'gridcolumn',
			dataIndex : 'ftpLocation',
			editor : {
				xtype : 'textfield',
				allowBlank : false,
				vtype : 'url'
			}
		}, {
			header : 'Rescan (ms)',
			xtype : 'numbercolumn',
			format : '0',
			dataIndex : 'rescanEvery',
			editor : {
				xtype : 'numberfield',
				allowBlank : false
			}
		}, {
			header : 'Pattern (regex)',
			xtype : 'gridcolumn',
			dataIndex : 'fileRegex',
			editor : {
				xtype : 'textfield',
				allowBlank : false
			}
		}, {
			header : 'Username',
			xtype : 'gridcolumn',
			dataIndex : 'username',
			editor : {
				xtype : 'textfield',
				allowBlank : true
			}
		}, {
			header : 'Password',
			xtype : 'gridcolumn',
			dataIndex : 'password',
			editor : {
				xtype : 'textfield',
				inputType : 'password'
			},
			renderer : function(value) {
				return (value.length > 0) ? "******" : "";
			}
		}, {
			header : 'Active',
			xtype : 'booleancolumn',
			dataIndex : 'active',
			editor : {
				xtype : 'checkbox',
				inputValue : true
			}
		}, {
			header : 'Success Date',
			xtype : 'datecolumn',
			dataIndex : 'successDate',
			format : 'Y-m-d'
		}, {
			header : 'Success Time',
			xtype : 'datecolumn',
			dataIndex : 'successTime',
			format : 'H:i:s'
		} ]
	});
	
	var ncISOPanel = new Ext.panel.Panel({
		title: 'ncISO',
		layout : 'fit',
		border : false,
		contentEl : 'decorate'
	});
	
	var editMetadataPanel = new Ext.panel.Panel({
		title: 'Edit Metadata',
		layout : 'fit',
		border : false,
		contentEl : 'dummy-delete-me'
	});
	
	new Ext.container.Viewport({
		layout : 'fit',
		items : [ new Ext.tab.Panel({
			title : "CATALOG/DATASET NAME HERE",
			layout : 'fit',
			region : 'center',
			margins : '2 2 2 2',
			border : false,
			defaults : {
				autoScroll : true
			},
			items : [ ingestGrid, ncISOPanel, editMetadataPanel ]
		}) ]
	});

});