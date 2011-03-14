var decorate = function() {

	var summaryPanel = new Ext.Panel({
		title : "Summary",
		id : 'summaryTab',
		layout: 'fit',
		contentEl : "metaSummary"
	});
	var identificationForm = new Ext.FormPanel({
		title : "Identification",
		id : 'idTab',
		layout: 'fit',
		contentEl : "identification"
	});

	var textForm = new Ext.FormPanel({
		title : "Text Search",
		id : 'textTab',
		layout: 'fit',
		contentEl : "textSearch"
	});
	var extentForm = new Ext.FormPanel({
		title : "Extent Search",
		id : 'extentTab',
		layout: 'fit',
		contentEl : "extentSearch"
	});
	var otherExtentForm = new Ext.FormPanel({
		title : "Other Extent Information",
		id : 'otherExtentTab',
		layout: 'fit',
		contentEl : "otherExtentInformation"
	});
	var creatorForm = new Ext.FormPanel({
		title : "Creator Search",
		id : 'creatorTab',
		layout: 'fit',
		contentEl : "creatorSearch"
	});
	var contribForm = new Ext.FormPanel({
		title : "Contributor Search",
		id : 'contribTab',
		layout: 'fit',
		contentEl : "contributorSearch"
	});
	var publisherForm = new Ext.FormPanel({
		title : "Publisher Search",
		id : 'pubTab',
		layout: 'fit',
		contentEl : "publisherSearch"
	});
	var otherForm = new Ext.FormPanel({
		title : "Other Attributes",
		id : 'otherTab',
		layout: 'fit',
		contentEl : "otherAttributes"
	});

	var tabpanel = new Ext.TabPanel({
		activeTab : 0,
		border: false,
//		defaults : {
//			autoScroll: true
//		},
		items: [summaryPanel, identificationForm, textForm, extentForm,	otherExtentForm,
			creatorForm, contribForm, publisherForm, otherForm]
	});

	var decoratePanel = new Ext.Panel({
		renderTo : 'decorate',
		layout: 'fit',
		border: false,
		items: [tabpanel,
			{
				contentEl: 'rubricVersion'
			}]
	})

	//var table = Ext.get("identificationTab");

};

var loadContent = function(filename) {
	Ext.Ajax.request({
		url: 'nciso',
		params: {
			file: filename
		},
		success: function(response) {
			document.getElementById("decorateContent").innerHTML = response.responseText;
			decorate();
		}
	});
};