Ext.define("ncETL.data.writer.Kvp", {
	extend: 'Ext.data.writer.Writer',
	alias: 'writer.kvp',
	writeRecords: function(request, data) {
		var params = {};
		Ext.apply(params, request.params);
		
		var addParam = Ext.Function.flexSetter(function(name, value) {
			
			params[name] = Ext.Array.from(params[name]);
			params[name].push(value);
			
		});
		
		Ext.each(data, function(item) {
			addParam(item);
		}); 
		
		request.params = params;
		
		return request;
	},
	getRecordData: function(record) {
        var isPhantom = record.phantom === true,
            writeAll = this.writeAllFields || isPhantom,
            nameProperty = this.nameProperty,
            fields = record.fields,
            data = {},
            changes,
            name,
            field,
            key;
        
        if (writeAll) {
            fields.each(function(field){
                if (field.persist) {
                    name = field[nameProperty] || field.name;
                    
                    var unclean = record.get(field.name);
                    
                    if (field.type === Ext.data.Types.DATE && field.dateFormat) {
                    	data[name] = Ext.Date.format(unclean, field.dateFormat);
                    } else {
                    	data[name] = unclean;
                    }
                }
            });
        } else {
            // Only write the changes
            changes = record.getChanges();
            for (key in changes) {
                if (changes.hasOwnProperty(key)) {
                    field = fields.get(key);
                    name = field[nameProperty] || field.name;
                    data[name] = changes[key];
                }
            }
            if (!isPhantom) {
                // always include the id for non phantoms
                data[record.idProperty] = record.getId();
            }
        }
        return data;
    }
});


