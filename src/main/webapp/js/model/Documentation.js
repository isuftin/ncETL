Ext.define('Documentation', {
	extend: 'Ext.data.Model',
	fields: [{
		name : 'id',
		type : 'int'
	}, {
		name : 'name',
		type : 'string'
	}, {
		name : 'dataset_id',
		type : 'int'
	}, {
		name : 'documentation_type_id',
		type : 'int'
	}, {
		name : 'xlink_href',
		type : 'string'
	}, {
		name : 'xlink_title',
		type : 'string'
	}, {
		name : 'text',
		type : 'string'
	}],
	proxy: {
		type : 'spec',
		api : {
			read : 'service/documentation/json/default',
			create : 'service/documentation/json/default/create',
			update : 'service/documentation/json/default/update',
			destroy : 'service/documentation/json/default/delete'
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