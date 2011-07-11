Ext.onReady(function() {

	var store = new Ext.data.Store({
		storeId : 'catalogStore',
		autoSync : false,
		autoLoad : true,
		model : 'Catalog'
	});

	var grid = new Ext.grid.Panel({
		title : 'Choose Dataset',
		forceFit : true,
		store: store,
		plugins : [ new Ext.grid.plugin.RowEditing({
			errorSummary : false
		}) ],
		tbar : [ {
			// iconCls: 'icon-user-add',
			text : 'Add',
			handler : function() {
				var e = new Catalog({
					name : '',
					location : '',
					expires : '2011-01-01',
					version : ''
				});
				store.insert(0, [ e ]);
			}
		}, {
			ref : '../removeBtn',
			// iconCls: 'icon-user-delete',
			text : 'Remove',
			handler : function() {
				var s = grid.getSelectionModel().getSelection();
				store.remove(s);
			}
		}, {
			text : 'Save',
			handler : function() {
				store.sync();
			}
		}, '->', {
			text : 'Export to THREDDS Container...',
			handler : function() {
				//TODO
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
			dataIndex : 'location'
		}, {
			header : 'Expires',
			xtype : 'datecolumn',
			dataIndex : 'expires',
			format : 'Y-m-d'
		}, {
			header : 'Version',
			xtype : 'gridcolumn',
			dataIndex : 'version',
			editor : {
				xtype : 'textfield',
				allowBlank : false
			}
		}, {
            header : 'View',
            xtype:'actioncolumn',
            width:50,
            items: [{
                icon: 'images/edit.png',
                tooltip: 'Edit',
                handler: function(grid, rowIndex, colIndex) {
                	var cat_id = grid.getStore().getAt(rowIndex).getId();
                	window.location.href = 'ingests.jsp?catalog_id=' + cat_id;
                }
            }]
        } ]
	});

	new Ext.container.Viewport({
		layout : 'fit',
		items : [ {
			xtype : 'panel',
			title : "THREDDS",
			layout: 'fit',
			store : store,
			region : 'center',
			border : false,
			items : [ grid ]
		} ]
	});

});