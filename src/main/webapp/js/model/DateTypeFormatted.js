//TODO
Ext.define('DateTypeFormatted', {
	extend: 'Ext.data.Model',
	fields: [{
		name : 'id',
		type : 'int'
	}, {
		name : 'format',
		type : 'string'
	}, {
		name : 'value',
		type : 'string'
	}, {
		name : 'date_type_enum_id',
		type : 'int'
	}],
	proxy: {
		type : 'spec',
		api : {
			read : 'service/datetypeformatted/json/default',
			create : 'service/datetypeformatted/json/default/create',
			update : 'service/datetypeformatted/json/default/update',
			destroy : 'service/datetypeformatted/json/default/delete'
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