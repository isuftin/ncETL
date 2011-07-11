Ext.define('Ingestor', {
	extend : 'Ext.data.Model',
	idProperty : 'id',
	belongsTo : 'Catalog',
	fields : [ {
		name : 'id',
		type : 'int'
	}, {
		name : 'catalog_id',
		type : 'int'
	}, {
		name : 'name',
		type : 'string',
		editor : {
			xtype: 'textfield',
			fieldLabel : 'Name',
			allowBlank : false,
			name : 'name'
		}
	}, {
		name : 'ftpLocation',
		type : 'string',
		editor : {
			xtype : 'textfield',
			fieldLabel : 'Location',
			name : 'ftpLocation',
			allowBlank : false,
			vtype : 'url'
		}
	}, {
		name : 'rescanEvery',
		type : 'int',
		editor : {
			xtype : 'numberfield',
			fieldLabel : 'Rescan (ms)',
			name : 'rescanEvery',
			allowBlank : false
		}
	}, {
		name : 'fileRegex',
		type : 'string',
		editor : {
			xtype : 'textfield',
			fieldLabel : 'Pattern (regex)',
			name : 'fileRegex',
			allowBlank : false
		}
	}, {
		name : 'successDate',
		type : 'date',
		dateFormat : 'Y-m-d'
	}, {
		name : 'successTime',
		type : 'date',
		dateFormat : 'H:i:s'
	}, {
		name : 'username',
		type : 'string',
		editor : {
			xtype : 'textfield',
			fieldLabel : 'Username',
			name : 'username',
			allowBlank : true
		}
	}, {
		name : 'password',
		type : 'string',
		editor : {
			xtype : 'textfield',
			fieldLabel : 'Password',
			name : 'password',
			inputType : 'password'
		}
	}, {
		name : 'active',
		type : 'bool',
		editor : {
			xtype : 'checkbox',
			fieldLabel : 'Active',
			name : 'active',
			inputValue : true
		}
	} ],
	proxy : {
		type : 'spec',
		api : {
			read : 'service/task/json/ingest',
			create : 'service/task/json/ingest/create',
			update : 'service/task/json/ingest/update',
			destroy : 'service/task/json/ingest/delete'
		},
		reader : {
			type : 'spec',
			idProperty : 'id'
		},
		writer : {
			type : 'kvp',
			writeAllFields : false
		},
		listeners : {
			"exception" : function(proxy, response, operation, options) {
//				console.log("Proxy Exception");
			}
		}
	}
});