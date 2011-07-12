//TODO
Ext.define('SpatialRange', {
	extend: 'Ext.data.Model',
	fields: [{
		name : 'id',
		type : 'int'
	}, {
		name : 'start',
		type : 'string'
	}, {
		name : 'size',
		type : 'string'
	}, {
		name : 'resolution',
		type : 'string'
	}, {
		name : 'units',
		type : 'string'
	}, {
		name : 'geospatial_coverage_id',
		type : 'int'
	}, {
		name : 'spatial_range_type_id',
		type : 'int'
	}],
	proxy: {
		type : 'spec',
		api : {
			read : 'service/spatialrange/json/default',
			create : 'service/spatialrange/json/default/create',
			update : 'service/spatialrange/json/default/update',
			destroy : 'service/spatialrange/json/default/delete'
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