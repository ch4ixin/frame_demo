var edit_form_panel = Ext.create("Ext.form.Panel", {
	url : getServerHttp()+"/shop/information/edit.htm",
	buttonAlign : "cnter",
	bodyStyle : "padding: 10px;",
	defaultType : "textfield",
	width: 600,
	items : [ {
		fieldLabel : "链接",
		name : "url",
		anchor : '100%',
		readOnly : true
	}],
	buttons : [ {
		text : "下载",
		formBind : true, // only enabled once the form is valid
		disabled : true,
		handler : function() {
			var form = this.up("form").getForm();
			window.open(form.getValues()["url"]);
		}
	} ]
});

var edit_form_panel_win = Ext.create("Ext.Window", {
	title : "下载",
	closeAction : "hide",
	items : edit_form_panel,
	modal:true
});

function myEdit(kid) {
	Ext.Ajax.request({
		url : getServerHttp()+"/cp/file/edit_form.htm?kid=" + kid,
		success : function(response) {
			var json = Ext.JSON.decode(response.responseText);
			edit_form_panel.getForm().reset();
			edit_form_panel.getForm().findField('url').setValue(window.location.host+getServerHttp()+"/download/"+kid+".htm");
			edit_form_panel_win.show();
		},
		failure : function(response) {
			Ext.Msg.alert("提示", "操作失败!");
		}
	});
}// #myEdit
