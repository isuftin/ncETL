//TODO
Ext.define('TimeCoverage', {
	extend: 'Ext.data.Model',
	fields: [{
		name : 'id',
		type : 'int'
	}, {
		name : 'dataset_id',
		type : 'int'
	}, {
		name : 'start_id',
		type : 'int'
	}, {
		name : 'end_id',
		type : 'int'
	}, {
		name : 'duration',
		type : 'string'
	}, {
		name : 'resolution',
		type : 'string'
	}],
	proxy: {
		type : 'spec',
		api : {
			read : 'service/timecoverage/json/default',
			create : 'service/timecoverage/json/default/create',
			update : 'service/timecoverage/json/default/update',
			destroy : 'service/timecoverage/json/default/delete'
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