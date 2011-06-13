var ncmlpanel = function() {
    return new Ext.Panel({
		title: 'Ncml Wrapper',
        id: 'wrapper',
		items : new Ext.form.TextArea({
            //grow: true,
            id: 'ncmltext',
            readOnly: true,
            width: '100%',
            height: '100%'
        })
    });
}

var populateWrapper = function(filename, tabpanel) {
    Ext.Ajax.request({
		url: 'WrapperServlet',
		params: {
			location: filename
		},
		success: function(response) {
            //var pretty = response.responseText.replace(new RegExp('<', 'g'), "&lt;")
			tabpanel.get('wrapper').get('ncmltext').setValue(response.responseText);
		},
		failure: function(response, opts){
            alert(response.status + response.responseText);
        }
	});
}

function escapeHTMLEncode(str) {
    var div = document.createElement('div');
    var text = document.createTextNode(str);
    div.appendChild(text);
    return div.innerHTML;
}