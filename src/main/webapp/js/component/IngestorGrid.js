Ext.define('ncETL.grid.Ingestor', {
	extend: 'Ext.grid.Panel',
	title : "Ingestors",
	forceFit : true,
	plugins : [ new Ext.grid.plugin.RowEditing({
		errorSummary : false
	}) ],
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
	} ],
	constructor : function(config) {
		if (!config) config = {};
		
		config = Ext.apply({
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
					this.getStore().add(e);
				},
				scope: this
			}, {
				ref : '../removeBtn',
				// iconCls: 'icon-user-delete',
				text : 'Remove',
				handler : function() {
					var s = this.getSelectionModel().getSelection();
					this.getStore().remove(s);
				},
				scope : this
			}, {
				text : 'Save',
				handler : function() {
					this.getStore().sync();
				},
				scope : this
			} ]
		}, config);
		
		ncETL.grid.Ingestor.superclass.constructor.call(this, config);
	}
});