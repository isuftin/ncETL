Ext.define('Ingestor', {
	extend : 'Ext.data.Model',
	idProperty : 'id',
	fields : [ {
		name : 'id',
		type : 'string'
	}, {
		name : 'name',
		type : 'string'
	}, {
		name : 'ftpLocation',
		type : 'string'
	}, {
		name : 'rescanEvery',
		type : 'int'
	}, {
		name : 'fileRegex',
		type : 'string'
	}, {
		name : 'successDate',
		type : 'date',
		dateFormat : 'Y-m-d'
	}, {
		name : 'successTime',
		type : 'date',
		dateFormat : 'H:i:s'
	}, {
		name : 'username',
		type : 'string'
	}, {
		name : 'password',
		type : 'string'
	}, {
		name : 'active',
		type : 'bool'
	} ],
	proxy : {
		type : 'spec',
		api : {
			read : 'service/task/json/ingest',
			create : 'service/task/json/ingest/create',
			update : 'service/task/json/ingest/update',
			destroy : 'service/task/json/ingest/delete'
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
				console.log("Proxy Exception");
			}
		}
	}
});