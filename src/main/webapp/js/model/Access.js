Ext.define('access', {
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
		name : 'service_id',
		type : 'int'
	}, {
		name : 'dataformat_id',
		type : 'int'
	}, {
		name : 'url_path',
		type : 'string'
	}],
	proxy: {
		type : 'spec',
		api : {
			read : 'service/access/json/default',
			create : 'service/access/json/default/create',
			update : 'service/access/json/default/update',
			destroy : 'service/access/json/default/delete'
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