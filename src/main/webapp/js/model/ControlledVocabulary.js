//TODO
Ext.define('ControlledVocabulary', {
	extend: 'Ext.data.Model',
	fields: [{
		name : 'id',
		type : 'int'
	}, {
		name : 'vocab',
		type : 'string'
	}],
	proxy: {
		type : 'spec',
		api : {
			read : 'service/controlledvocabulary/json/default',
			create : 'service/controlledvocabulary/json/default/create',
			update : 'service/controlledvocabulary/json/default/update',
			destroy : 'service/controlledvocabulary/json/default/delete'
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