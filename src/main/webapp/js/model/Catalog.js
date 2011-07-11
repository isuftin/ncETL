Ext.define('Catalog', {
	extend: 'Ext.data.Model',
	idProperty: 'id',
	fields: [{
		name : 'id',
		type : 'string'
	}, {
		name : 'name',
		type : 'string'
	}, {
		name : 'location',
		type : 'string'
	}, {
		name : 'expires',
		type : 'date',
		dateFormat : 'Y-m-d'
	}, {
		name : 'version',
		type : 'string'
	}],
	proxy: {
		type : 'spec',
		api : {
			read : 'service/catalog/json/default',
			create : 'service/catalog/json/default/create',
			update : 'service/catalog/json/default/update',
			destroy : 'service/catalog/json/default/delete'
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