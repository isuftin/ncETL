Ext.define('ncETL.data.proxy.Spec', {
	extend : 'Ext.data.proxy.Ajax',
	alias : 'proxy.spec',
	batchActions : false,
	pageParam : undefined,
	startParam : undefined,
	limitParam : undefined,
	sortParam : "orderby",
	encodeSorters : function(sorters) {
		var length = sorters.length, sortStrs = [], sorter, i;

		for (i = 0; i < length; i++) {
			sorter = sorters[i];
			sortStrs[i] = sorter.property + ' ' + sorter.direction;
		}

		return sortStrs.join(",");
	},
	filterParam : 'narrow',
	encodeFilters : function(filters) { //Currently filtering does not work on multiple parameters at once.
		var length = filters.length, filterStr = '', filter, i;

		if (0 < length) {
			filter = filters[0];
			filterStr = 's_' + filter.property + ',' + filter.value;
		}

		return filterStr;
	}
});