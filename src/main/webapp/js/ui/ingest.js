var ingest = function() {
    var Ingestor = Ext.data.Record.create([{
        name: 'name',
        type: 'string'
    },{
        name: 'ftpLocation',
        type: 'string'
    },{
        name: 'rescanEvery',
        type: 'int'
    },{
        name: 'filePattern',
        type: 'string'
    },{
        name: 'successDate',
        type: 'date',
        dateFormat: 'c'
    },{
        name: 'successTime',
        type: 'time',
        dateFormat: 't'
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
    
    proxy.on("exception", function(a, b, c, d, e, f, g, h) {
        console.log("put a breakpoint on me!");
    })

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
                name: 'name'
            },{
                name: 'ftpLocation'
            },{
                name: 'rescanEvery'
            },{
                name: 'filePattern'
            },{
                name: 'successDate'
            },{
                name : 'successTime'
            },{
                name: 'username'
            },{
                name: 'password'
            },{
                name: 'active'
            }
        ]
    });
    
    Ext.ns("NCETL");
    NCETL.SpecWriter = Ext.extend(Ext.data.DataWriter, {
        constructor: function(config) {
            NCETL.SpecWriter.superclass.constructor.call(this, config);
        },
        render: function(params, baseParams, data) {
            if (data) {
                return Ext.apply(params, data);
            }
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
        id: 'name',
        storeId : 'ingestStore',
        restful: true,
        autoSave: false,
        proxy: proxy,
        reader: jsonReader,
        writer: new NCETL.SpecWriter(),
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Origin", document.location.protocol + "//" + document.location.host);
        }
    });

    store.load();

//    var editor = new Ext.ux.grid.RowEditor({
//        saveText: 'Update'
//        
//    });

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
                    name: '',
                    ftpLocation: '',
                    rescanEvery: '300000',
                    filePattern: '.*',
                    successDate: '2011-01-01',
                    successTime: '00:00:00',
                    username: '',
                    password: '',
                    active: false
                });
                //editor.stopEditing();
                store.insert(0, e);
                grid.getView().refresh();
                grid.getSelectionModel().selectRow(0);
                //editor.startEditing(0);
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
        },{
            text: 'Save',
            handler: function() {
                store.save();
            }
        }],
        colModel: new Ext.grid.ColumnModel({
            defaults: {
                width: 120,
                sortable: true
            },
            columns: [
                {
                    header: 'Name',
                    id: 'name',
                    dataIndex: 'name',
                    xtype: 'gridcolumn',
                    editor:
                    {
                        xtype: 'textfield',
                        allowBlank: false
                    }
                },{
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
                    renderer: function(value) {
                        return (value.length > 0) ? "******": "";
                    }
                },{
                    header: 'Active',
                    xtype: 'checkcolumn',
                    dataIndex: 'active'
                },{
                    header: 'Success Date',
                    xtype: 'datecolumn',
                    dataIndex: 'successDate',
                    format: 'Y-m-d',
                    editable: false
                },{
                    header: 'Success Time',
                    xtype: 'datecolumn',
                    dataIndex: 'successTime',
                    format: 'h:i:s',
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



Ext.override(Ext.data.HttpProxy, {
    onWrite : function(action, o, response, rs) {
        var reader = o.reader;
        var res;
        try {
            res = reader.readResponse(action, response);
        } catch (e) {
            this.fireEvent('exception', this, 'response', action, o, response, e);
            o.request.callback.call(o.request.scope, null, o.request.arg, false);
            return;
        }
        if (res.success) {
            this.fireEvent('write', this, action, res.data, res, rs, o.request.arg);
        } else {
            this.fireEvent('exception', this, 'remote', action, o, res, rs);
        }
        o.request.callback.call(o.request.scope, res.data, res, res.success);
    }
})