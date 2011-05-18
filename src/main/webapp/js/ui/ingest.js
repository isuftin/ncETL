var ingest = function() {
    var Ingestor = Ext.data.Record.create([{
        //						name: 'id',
        //						type: 'int'
        //					},{
        name: 'ftpLocation',
        type: 'string'
    }, {
        name: 'rescanEvery',
        type: 'int'
    }, {
        name: 'filePattern',
        type: 'string'
    },{
        name: 'lastSuccess',
        type: 'date',
        dateFormat: 'c'
    },{
        name: 'username',
        type: 'string'
    },{
        name: 'password',
        type: 'string'
    },{
        name: 'active',
        type: 'bool'
    }]);

    var proxy = new Ext.data.HttpProxy({
        api: {
            read   : 'service/ingest/json/',
            create : 'service/ingest/json/default/create',
            update : 'service/ingest/json/default/update',
            destroy: 'service/ingest/json/default/delete'
        }
    });

    //	var store = new Ext.data.XmlStore({
    //		fields: ['id', 'ftpLocation', 'rescanEvery', 'filePattern', 'lastSuccess' , 'username', 'password', 'active'],
    //		proxy: proxy,
    //		root: 'ingestors',
    //		totalProperty: 'count',
    //		successProperty: 'success',
    //		autoLoad: true,
    //		autoSave: false,
    //		autoCreate: true,
    //		autoDestroy: true,
    //		idProperty: 'id',
    //		writer: writer
    //	});
    //
    //	var writer = new Ext.data.XmlWriter({
    //		writeAllFields: true
    //	});

    var jsonReader = new Ext.data.JsonReader({
        idProperty: 'name',
        root: 'success.data',
        totalProperty: 'success["@rowCount"]',
        successProperty: 'success',
        fields: [
        {
            name: 'name',
            mapping: 'name'
        },
        
        {
            name: 'ftpLocation', 
            mapping: 'ftpLocation'
        },

        {
            name: 'rescanEvery', 
            mapping: 'rescanEvery'
        },

        {
            name: 'filePattern', 
            mapping: 'filePattern'
        },

        {
            name: 'lastSuccess', 
            mapping: 'lastSuccess'
        },
        
        {name : 'successTime'},

        {
            name: 'username', 
            mapping: 'username'
        },

        {
            name: 'password', 
            mapping: 'password'
        },

        {
            name: 'active', 
            mapping: 'active'
        }
        ]
    });

    var jsonWriter = new Ext.data.JsonWriter({
        encode: false
    });
    
    Ext.ns("NCETL");
    NCETL.SpecWriter = Ext.extend(Ext.data.DataWriter, {
        constructor: function(config) {
            NCETL.SpecWriter.superclass.constructor.call(this, config);
        },
        render: function(action, rs, params, data) {
            return Ext.apply(params, data[this.meta.root]);
        },
        createRecord: function(rec) {
            return this.toHash(rec);
        },
        updateRecord: function(rec) {
            return this.toHash(rec);
        },
        destroyRecord: function(rec) {
            var data = {};
            data[this.meta.idProperty] = rec.id;
            return data;
        }
    });

    var store = new Ext.data.Store({
        id: '_id',
        restful: true,
        proxy: proxy,
        reader: jsonReader,
        writer: new NCETL.SpecWriter(),
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Origin", document.location.protocol + "//" + document.location.host);
        }
    });

    store.load();

    var editor = new Ext.ux.grid.RowEditor({
        saveText: 'Update'
    });

    var grid = new Ext.grid.EditorGridPanel({
        title: "Ingestors",
        renderTo: 'ingest',
        store: store,
        autoHeight: true,
        tbar: [{
            //iconCls: 'icon-user-add',
            text: 'Add',
            handler: function(){
                var e = new Ingestor({
                    ftpLocation: '',
                    rescanEvery: '300000',
                    filePattern: '.*',
                    lastSuccess: '2011-01-01',
                    username: '',
                    password: '',
                    active: false
                });
                editor.stopEditing();
                store.insert(0, e);
                grid.getView().refresh();
                grid.getSelectionModel().selectRow(0);
                editor.startEditing(0);
            }
        },{
            ref: '../removeBtn',
            //iconCls: 'icon-user-delete',
            text: 'Remove',
            disabled: true,
            handler: function(){
                editor.stopEditing();
                var s = grid.getSelectionModel().getSelections();
                for(var i = 0, r; r = s[i]; i++){
                    store.remove(r);
                }
            }
        }],
        colModel: new Ext.grid.ColumnModel({
            defaults: {
                width: 120,
                sortable: true
            },
            columns: [
            //							{
            //								id: 'id',
            //								dataIndex: 'id',
            //								hidden: true
            //							},
            {
                //id: 'ftpLocation',
                header: 'Location',
                xtype: 'gridcolumn',
                dataIndex: 'ftpLocation',
                editor:
                {
                    xtype: 'textfield',
                    allowBlank: false,
                    vtype: 'url'
                }
            },{
                header: 'Rescan (ms)',
                xtype: 'numbercolumn',
                format: '0',
                dataIndex: 'rescanEvery',
                editor:
                {
                    xtype: 'numberfield',
                    allowBlank: false
                }
            },{
                header: 'Pattern (regex)',
                xtype: 'gridcolumn',
                dataIndex: 'filePattern',
                editor:
                {
                    xtype: 'textfield',
                    allowBlank: false
                }
            },{
                header: 'Username',
                xtype: 'gridcolumn',
                dataIndex: 'username',
                editor:
                {
                    xtype: 'textfield',
                    allowBlank: true
                }
            },{
                header: 'Password',
                //xtype: 'gridcolumn',
                //inputType: 'password',
                dataIndex: 'password',
                editor:
                {
                    xtype: 'textfield',
                    inputType: 'password'
                },
                renderer: function(value){
                    return (value.length > 0) ? "******": "";
                }
            },{
                header: 'Active',
                xtype: 'checkcolumn',
                dataIndex: 'active'
            },{
                header: 'Last Success',
                xtype: 'datecolumn',
                dataIndex: 'lastSuccess',
                format: 'c',
                editable: false
            }
            ]
        }),
        viewConfig: {
            forceFit: true
        },
        sm: new Ext.grid.RowSelectionModel({
            singleSelect: true
        })
    });
};