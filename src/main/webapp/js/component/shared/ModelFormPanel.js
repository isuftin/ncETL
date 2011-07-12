Ext.define("ncETL.panel.ModelForm", {
	extend: 'Ext.panel.Panel',
	constructor: function(config) {
		if (!config) config = {};
		
		var buildItems = function(modelInst) {
			var _itemsArray = [];
			
			var _baseForm = new ncETL.form.Model({
				model : modelInst.self.getName(),
				defaults : {
					anchor : '100%'
				}
			});
			_baseForm.loadRecord(modelInst);
			
			_itemsArray.push(_baseForm);
			
			var buildItemsAux = function(array, mInst) {
				
				mInst.associations.each(function(association) {
					if (association.type === 'hasMany') {
						array.push(new ncETL.panel.ModelFormGroup({
							store : mInst[association.associatedName.toLowerCase() + 's']()
						}));
					}
				});
			};
			
			buildItemsAux(_itemsArray, modelInst);
			
			return _itemsArray;
		};
		
		var _modelName = config.model;
		
		var _items = buildItems(_modelName);
		
		var _saveButton = new Ext.button.Button({
			text : 'Save Values',
			id : 'stupidButton'
		});
		
		_saveButton.on("click", function() {
				this.items.each(function(item) {
					if (item.saveRecord) {
						item.saveRecord();
					} else {
						item.saveRecords();
					}
					
				}, this);
			}, this);
		
		config = Ext.apply({
			fbar : [ _saveButton ],
			items : _items
		}, config);
		ncETL.panel.ModelForm.superclass.constructor.call(this, config);
	}
});