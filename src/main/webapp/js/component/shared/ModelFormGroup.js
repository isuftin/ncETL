Ext.define("ncETL.panel.ModelFormGroup", {
	extend: 'Ext.panel.Panel',
	constructor: function(config) {
		if (!config) config = {};
		
		var _store = config.store;
		
		var _items = [];
		
		_store.on('load', function(store, records, successful, operation, options) {
			store.each(function(record) {
				var form = new ncETL.form.Model({
					model : record.self.getName(),
					defaults : {
						anchor : '100%'
					}
				});
				form.loadRecord(record);
				this.add(form);
			}, this);
		}, this);
		
		config = Ext.apply({
		items : _items	
		}, config);
		ncETL.panel.ModelFormGroup.superclass.constructor.call(this, config);
	},
	saveRecords : function() {
		this.items.each(function(item){
			item.saveRecord();
		}, this);
	}
});