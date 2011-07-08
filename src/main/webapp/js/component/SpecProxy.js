Ext.define('ncETL.data.proxy.Spec', {
	extend : 'Ext.data.proxy.Ajax',
	alias : 'proxy.spec',
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
	filterParam : undefined
});