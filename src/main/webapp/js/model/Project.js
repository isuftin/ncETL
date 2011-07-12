//TODO
Ext.define('Project', {
	extend: 'Ext.data.Model',
	fields: [{
		name : 'id',
		type : 'int'
	}, {
		name : 'name',
		type : 'string'
	}, {
		name : 'controlled_vocabulary_id',
		type : 'int'
	}],
	proxy: {
		type : 'spec',
		api : {
			read : 'service/project/json/default',
			create : 'service/project/json/default/create',
			update : 'service/project/json/default/update',
			destroy : 'service/project/json/default/delete'
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