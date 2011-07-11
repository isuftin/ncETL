var Page = function() {
	return {
		queryString : Ext.Object.fromQueryString(window.location.search.substring(1))
	};
}();
