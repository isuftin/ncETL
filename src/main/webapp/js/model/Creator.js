//TODO
Ext.define('Creator', {
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
	}, {
		name : 'contact_url',
		type : 'string'
	}, {
		name : 'contact_email',
		type : 'string'
	}],
	proxy: {
		type : 'spec',
		api : {
			read : 'service/creator/json/default',
			create : 'service/creator/json/default/create',
			update : 'service/creator/json/default/update',
			destroy : 'service/creator/json/default/delete'
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