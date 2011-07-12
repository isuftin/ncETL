//TODO
Ext.define('Keyword', {
	extend: 'Ext.data.Model',
	fields: [{
		name : 'id',
		type : 'int'
	}, {
		name : 'value',
		type : 'string'
	}, {
		name : 'controlled_vocabulary_id',
		type : 'int'
	}],
	proxy: {
		type : 'spec',
		api : {
			read : 'service/keyword/json/default',
			create : 'service/keyword/json/default/create',
			update : 'service/keyword/json/default/update',
			destroy : 'service/keyword/json/default/delete'
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