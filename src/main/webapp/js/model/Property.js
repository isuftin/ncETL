//TODO
Ext.define('Property', {
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
		name : 'value',
		type : 'string'
	}],
	proxy: {
		type : 'spec',
		api : {
			read : 'service/property/json/default',
			create : 'service/property/json/default/create',
			update : 'service/property/json/default/update',
			destroy : 'service/property/json/default/delete'
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