Ext.define('Service', {
	extend: 'Ext.data.Model',
	fields: [{
		name : 'id',
		type : 'int'
	}, {
		name : 'name',
		type : 'string'
	}, {
		name : 'catalog_id',
		type : 'int'
	}, {
		name : 'service_id',
		type : 'int'
	}, {
		name : 'service_type_id',
		type : 'int'
	}, {
		name : 'base',
		type : 'string'
	}, {
		name : 'description',
		type : 'string'
	}, {
		name : 'suffix',
		type : 'string'
	}],
	proxy: {
		type : 'spec',
		api : {
			read : 'service/srvc/json/default',
			create : 'service/srvc/json/default/create',
			update : 'service/srvc/json/default/update',
			destroy : 'service/srvc/json/default/delete'
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