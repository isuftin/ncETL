Ext.app.NcmlLoader = Ext.extend(Ext.ux.tree.XmlTreeLoader, {
	processAttributes: function(attr){
		attr.text = '';
		attr.qtip = '';
		for (var key in attr) {
			if (key === 'tagName') {
				attr.iconCls = attr[key];
			}
			//			else if (attr.hasOwnProperty(key) && key !== 'text') {
			//				attr.text += '[' + key + '=' + attr[key] + '] ';
			//			}
			else if (key === 'name') {
				attr.text = attr[key];
				attr.qtip += '<em>'+attr.text+'</em><br/>';
			}
			else if (!ignoreMe(key)) {
				attr.qtip += key + ': ' + attr[key] + '<br/>';
			}
		}
		attr.loaded = true;
		attr.expanded = false;
		
	}
});

var ignoreMe = function(attribute) {
	if (attribute === 'tagName' ||
		attribute === 'text' ||
		attribute === 'qtip' ||
		attribute === 'iconCls' ||
		attribute === 'loaded' ||
		attribute === 'expanded' ||
		attribute === 'loader' ||
		attribute === 'id') return true;
}

var editElement = function() {
	var treePanel = Ext.getCmp('treepanel');
	var selectedNode = treePanel.getSelectionModel().getSelectedNode();
	var innerForm = new Ext.FormPanel({
		title: 'Edit Element',
		border: false
	});
	Ext.iterate(selectedNode.attributes, function(key, value){
		if (!ignoreMe(key)) {
			innerForm.add(new Ext.form.TextField({
				fieldLabel: key,
				name: key,
				value: value
			}));
		}
	});
	var win = new Ext.Window({
		layout: 'fit',
		title: selectedNode.text,
		width: 500,
		height: 300,
		closeAction: 'close',
		plain: true,
		items: innerForm,
		buttons: [{
			text:'Submit',
                        scope: selectedNode,
                        handler: function() {
                            this.setText(this.text + ' (M)');
                            this.unselect(true);
                            
//                            ncmlpanel.html = ?
                            
                            win.close();
                        }
		},{
			text: 'Close',
			handler: function(){
				win.close();
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

var modifyNetCDF = function(filename, tabPanel) {

	Ext.QuickTips.init();

	var treepanel = new Ext.tree.TreePanel({
		id: 'treepanel',
		title: 'Ncml Source',
		rootVisible: true,
		autoScroll:true,
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
	
//	var ncisopanel = new Ext.Panel({
//		title: 'NetCDF View',
//		autoScroll:true,
//		margins: '35 5 5 0'
//	});

//	ncisopanel.add([treepanel, ncmlpanel]);

	tabPanel.add(treepanel);
	tabPanel.add(ncmlpanel());
	tabPanel.activate(treepanel);
};

var generateRubric = function(filename, tabPanel) {
	divArea = document.getElementById("decorateContent");
	Ext.Ajax.request({
		url: 'nciso',
		params: {
			file: filename,
			output: 'rubric'
		},
		success: function(response) {

			// element is destroyed when switching datasets, so need to recreate everytime
			var div = document.getElementById("decorateContent");
			var el = document.createElement("div");
			div.insertBefore(el, null);
			el.innerHTML = response.responseText;

			var rubric = new Ext.Panel({
				title : 'ncISO Rubric',
				layout: 'fit',
				autoScroll: true,
				autoDestroy: false,
				contentEl: el
			});
			tabPanel.add(rubric);
		},
		failure: function(){alert("failure");}
	});
}