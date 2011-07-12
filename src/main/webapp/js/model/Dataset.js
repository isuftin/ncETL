Ext.define('Dataset', {
	extend: 'Ext.data.Model',
	associations : [{
		type: 'hasMany',
		model: 'Access',
		primaryKey: 'id',
		foreignKey: 'dataset_id',
		autoLoad: true
	}, {
		type: 'hasMany',
		model: 'Documentation',
		primaryKey: 'id',
		foreignKey: 'dataset_id',
		autoLoad: true
	}, {
		type: 'hasMany',
		model: 'Property',
		primaryKey: 'id',
		foreignKey: 'dataset_id',
		autoLoad: true
	}, {
		type: 'hasMany',
		model: 'GeospatialCoverage',
		primaryKey: 'id',
		foreignKey: 'dataset_id',
		autoLoad: true
	}, {
		type: 'hasMany',
		model: 'TimeCoverage',
		primaryKey: 'id',
		foreignKey: 'dataset_id',
		autoLoad: true
	}],
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
		name : 'data_type_id',
		type : 'int'
	}, {
		name : 'ncid',
		type : 'string'
	}, {
		name : 'authority',
		type : 'string'
	}],
	belongsTo: 'Catalog',
	proxy: {
		type : 'spec',
		api : {
			read : 'service/dataset/json/default',
			create : 'service/dataset/json/default/create',
			update : 'service/dataset/json/default/update',
			destroy : 'service/dataset/json/default/delete'
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