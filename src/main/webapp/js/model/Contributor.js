//TODO
Ext.define('Contributor', {
	extend: 'Ext.data.Model',
	fields: [{
		name : 'id',
		type : 'int'
	}, {
		name : 'role',
		type : 'string'
	}, {
		name : 'text',
		type : 'string'
	}],
	proxy: {
		type : 'spec',
		api : {
			read : 'service/contributor/json/default',
			create : 'service/contributor/json/default/create',
			update : 'service/contributor/json/default/update',
			destroy : 'service/contributor/json/default/delete'
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