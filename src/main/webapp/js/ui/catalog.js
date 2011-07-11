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
				var s = grid.getSelectionModel().getSelections();
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
			dataIndex : 'location',
			editor : {
				xtype : 'textfield',
				allowBlank : false,
				vtype : 'url'
			}
		}, {
			header : 'Expires',
			xtype : 'datecolumn',
			dataIndex : 'successDate',
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
            xtype:'actioncolumn', 
            width:50,
            items: [{
                icon: 'images/edit.png',
                tooltip: 'Edit',
                handler: function(grid, rowIndex, colIndex) {
//                    var rec = grid.getStore().getAt(rowIndex);
//                    alert("Edit " + rec.get('firstname'));
                }
            }]
        } ]
	});

	new Ext.container.Viewport({
		layout : 'fit',
		items : [ {
			xtype : 'panel',
			title : "THREDDS",
			store : store,
			region : 'center',
			border : false,
			items : [ grid ]
		} ]
	});

});