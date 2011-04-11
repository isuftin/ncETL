var decorate = function() {

	var results = Ext.getCmp('nciso');
	results.removeAll();

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

	//	var tabpanel = new Ext.TabPanel({
	//		activeTab : 0,
	//		border: false,
	//		defaults : {
	//			autoScroll: true
	//		},
	//		items: [summaryPanel, identificationForm, textForm, extentForm,	otherExtentForm,
	//			creatorForm, contribForm, publisherForm, otherForm]
	//	});
	
	results.add([
		summaryPanel,
		identificationForm,
		textForm,
		extentForm,
		otherExtentForm,
		creatorForm,
		contribForm,
		publisherForm,
		otherForm,
		
		]);
	results.activate(0);

	var sb = Ext.getCmp('statusBar');
	sb.add({
		contentEl: 'rubricVersion'
	});
	var viewport = Ext.getCmp('viewport');
	viewport.doLayout();
//results.render();
//results.doLayout();
//	var decoratePanel = new Ext.Panel({
//		renderTo : 'decorate',
//		layout: 'anchor',
//		region: 'center',
//		border: false,
//		items: [tabpanel,
//			{
//				contentEl: 'rubricVersion'
//			}]
//	})

//var table = Ext.get("identificationTab");

};

var decorate_ncml = function(filename) {

	var results = Ext.getCmp('nciso');

	var otherForm = new Ext.FormPanel({
		title : filename,
		//id : 'otherTab',
		layout: 'fit',
		contentEl : "otherAttributes"
	});

	//	var tabpanel = new Ext.TabPanel({
	//		activeTab : 0,
	//		border: false,
	//		defaults : {
	//			autoScroll: true
	//		},
	//		items: [summaryPanel, identificationForm, textForm, extentForm,	otherExtentForm,
	//			creatorForm, contribForm, publisherForm, otherForm]
	//	});

	results.add([
		summaryPanel,
		identificationForm,
		textForm,
		extentForm,
		otherExtentForm,
		creatorForm,
		contribForm,
		publisherForm,
		otherForm,

		]);
	results.activate(0);

	var sb = Ext.getCmp('statusBar');
	sb.add({
		contentEl: 'rubricVersion'
	});
	var viewport = Ext.getCmp('viewport');
	viewport.doLayout();
//results.render();
//results.doLayout();
//	var decoratePanel = new Ext.Panel({
//		renderTo : 'decorate',
//		layout: 'anchor',
//		region: 'center',
//		border: false,
//		items: [tabpanel,
//			{
//				contentEl: 'rubricVersion'
//			}]
//	})

//var table = Ext.get("identificationTab");

};

Ext.app.NcmlLoader = Ext.extend(Ext.ux.tree.XmlTreeLoader, {
	processAttributes: function(attr){
		attr.text = '';
		for (var key in attr) {
			if (key === 'tagName') {
				attr.iconCls = attr[key];
			}
			//			else if (attr.hasOwnProperty(key) && key !== 'text') {
			//				attr.text += '[' + key + '=' + attr[key] + '] ';
			//			}
			else if (key === 'name') {
				attr.text = attr[key];
			}
			else if (key === 'value') {
				attr.value = attr[key];
			}
		}
		attr.loaded = true;
		attr.expanded = false;
	}
});
var editElement = function() {
	var treePanel = Ext.getCmp('treepanel');
	var selectedNode = treePanel.getSelectionModel().getSelectedNode();
	var win = new Ext.Window({
		layout: 'fit',
		title: selectedNode.text,
		width: 500,
		height: 300,
		closeAction: 'hide',
		plain: true,
		items: new Ext.FormPanel({
			title: 'Edit Element',
			items: [{
				fieldLabel: 'name',
				xtype: 'textfield',
				name: 'name',
				value: selectedNode.attributes.name,
				allowBlank: false
			},{
				fieldLabel: 'value',
				xtype: 'textfield',
				value: selectedNode.attributes.value,
				name: 'value'
			}],
			border:false
		}),

		buttons: [{
			text:'Submit'
		},{
			text: 'Close',
			handler: function(){
				win.hide();
			}
		}]
	});
	win.show();
};

var menu;
var rightClick = function(node, event) {
	if (!menu) {
		menu = new Ext.menu.Menu({
			items: [{
				itemId: 'edit',
				text: 'Edit',
				scope: editElement,
				handler: editElement
			}]
		});
	}
	menu.showAt(event.getXY());
	this.getSelectionModel().select(node);
};

var loadContent = function(filename) {

	var results = Ext.getCmp('nciso');
	var treepanel = new Ext.tree.TreePanel({
		id: 'treepanel',
		columnWidth: .48,
		title: 'Ncml Source',
		rootVisible: true,
		root: new Ext.tree.AsyncTreeNode({
			text: filename,
			iconCls: 'netcdf'
		}),//treeNode,
		loader: new Ext.app.NcmlLoader({
			url: 'nciso',
			baseParams: {
				file: filename,
				output: 'ncml'
			}
		}),
		listeners: {
			contextmenu: rightClick
		}
	});

	var ncmlpanel = new Ext.Panel({
		columnWidth: .48,
		items: [{
			title: 'Ncml Wrapper',
			html: '&lt;ncml location="' + filename + '"&gt;<br />&lt;/ncml&gt;'
		}]
	});

	var ncisopanel = new Ext.Panel({
		title: filename,
		closable: true,
		layout: 'column',
		autoScroll:true,
		margins: '35 5 5 0'
	});

	ncisopanel.add([treepanel, ncmlpanel]);

	results.add(ncisopanel);
	results.activate(ncisopanel);
//	Ext.Ajax.request({
//		url: 'nciso',
//		params: {
//			file: filename,
//			output: 'ncml'
//		},
//		success: function(response) {
//			//document.getElementById("decorateContent").innerHTML = response.responseText;
//			//decorate();
//			//decorate_ncml(filename, response);
//			var results = Ext.getCmp('nciso');
//
//			document.getElementById("tempDiv").innerHTML = response.responseText;
//
//			var otherForm = new Ext.Panel({
//				title : filename,
//				closable: true,
//				layout: 'fit',
//				contentEl: 'tempDiv'
//			});
//
//			results.add(otherForm);
//			results.activate(otherForm);
//		},
//		failure: function(){alert("failure");}
//	});

};
