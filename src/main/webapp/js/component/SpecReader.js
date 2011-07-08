Ext.define('ncETL.data.reader.Spec', {
	extend : 'Ext.data.reader.Json',
	alias : 'reader.spec',
	root : 'success.data',
	totalProperty : 'success["@rowCount"]',
	successProperty : 'success'
});