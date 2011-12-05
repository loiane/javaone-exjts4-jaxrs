Ext.define('BrazilJS.store.Contacts', {
    extend: 'Ext.data.Store',
    model: 'BrazilJS.model.Contact',
    autoLoad: true,
    pageSize: 35,
    autoLoad: {start: 0, limit: 35},
    
    proxy: {
        /*type: 'ajax',
        api: {
            read :   'resources/contact/view.action',
            create : 'resources/contact/create.action',
            update:  'resources/contact/update.action',
            destroy: 'resources/contact/delete.action'
        },*/
        type: 'rest',
        url: 'resources/rest',
        reader: {
            type: 'json',
            root: 'data',
            successProperty: 'success'
        },
        writer: {
            type: 'json',
            writeAllFields: true,
            encode: false,
            root: 'data'
        },
        listeners: {
            exception: function(proxy, response, operation){
                Ext.MessageBox.show({
                    title: 'REMOTE EXCEPTION',
                    msg: operation.getError(),
                    icon: Ext.MessageBox.ERROR,
                    buttons: Ext.Msg.OK
                });
            }
        }
    }
});