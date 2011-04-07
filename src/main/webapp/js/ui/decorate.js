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
	sb.add({contentEl: 'rubricVersion'});
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
	sb.add({contentEl: 'rubricVersion'});
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

var loadContent = function(filename) {

	var p = new Ext.data.HttpProxy({
		url: 'nciso',
		params:{
			file: filename,
			output: 'ncml'
		}
	});
	var results = Ext.getCmp('nciso');
	results.add(createXmlTree({
		title: filename,
		layout: 'fit'
	},
	p
	));

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
//			var otherForm = new Ext.tree.TreePanel({
//				title : filename,
//				//id : 'otherTab',
//				layout: 'fit',
//				data: response.responseText
//			});
//
//			results.add(otherForm);
//		}
//	});

};

function createXmlTree(el, p, callback) {
		var tree = new Ext.tree.TreePanel(el);

		p.on("loadexception", function(o, response, e) {
			if (e) throw e;
		});
		p.doRequest({
			action: 'read',
			params: p.params,
			reader: {
				read: function(response) {
					var doc = response.responseXML;
					tree.setRootNode(treeNodeFromXml(doc.documentElement || doc));
				}
			},
			callback: callback || tree.render,
			scope: tree});
		return tree;
	}

function treeNodeFromXml(XmlEl) {
//	Text is nodeValue to text node, otherwise it's the tag name
	var t = ((XmlEl.nodeType == 3) ? XmlEl.nodeValue : XmlEl.tagName);

//	No text, no node.
	if (t.replace(/\s/g,'').length == 0) {
		return null;
	}
	var result = new Ext.tree.TreeNode({
        text : t
    });

//	For Elements, process attributes and children
	if (XmlEl.nodeType == 1) {
		Ext.each(XmlEl.attributes, function(a) {
			var c = new Ext.tree.TreeNode({
				text: a.nodeName
			});
			c.appendChild(new Ext.tree.TreeNode({
				text: a.nodeValue
			}));
			result.appendChild(c);
		});
		Ext.each(XmlEl.childNodes, function(el) {
//		Only process Elements and TextNodes
			if ((el.nodeType == 1) || (el.nodeType == 3)) {
				var c = treeNodeFromXml(el);
				if (c) {
					result.appendChild(c);
				}
			}
		});
	}
	return result;
}