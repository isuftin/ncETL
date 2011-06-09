var ncmlpanel = new Ext.Panel({
		title: 'Ncml Wrapper',
        id: 'wrapper',
		html: ''
});

var populateWrapper = function(filename, tabpanel) {
    Ext.Ajax.request({
		url: 'WrapperServlet',
		params: {
			location: filename
		},
		success: function(response) {
			tabpanel.get('wrapper').html = '<code>'+response.responseText+'</code>';
		},
		failure: function(response, opts){
            alert(response.status + response.responseText);
        }
	});
}