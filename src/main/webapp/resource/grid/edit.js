var ed_context;
var edit_form_panel = Ext.create("Ext.form.Panel", {
	url : "/traceback/shop/grid/edit.htm",
	buttonAlign : "center",
	bodyStyle : "padding: 10px;",
	defaultType : "textfield",
	items : [{
		fieldLabel : "说明",
		name : "note",
		anchor : '100%',
		allowBlank : false
	}, {
		xtype : "panel",
		html : '<iframe src= "/traceback/view_shop(Lvliang)/grid/eeditors.html" width="100%" height="100%" marginwidth="0" framespacing="0" marginheight="0" frameborder="0" ></iframe>',
		width : '100%',
		height : 650,
		border : false,
		style : {
			marginBottom : '10px',
			marginLeft : '10px',
		}
	}, {
		xtype : 'textarea',
		width : 500,
		height : 300,
		name : 'content',
		fieldLabel : '内容',
		hidden : true
	}, {
		fieldLabel : "id",
		name : "kid",
		hidden : true
	}],
	buttons : [ {
		text : "保存",
		formBind : true, // only enabled once the form is valid
		disabled : true,
		handler : function() {
			var form = this.up("form").getForm();
			is_submiting = true;
			//console.log(ed_context);
			form.findField("content").setValue(ed_context);
			if (form.isValid()) {
				form.submit({
					waitMsg : "保存中...",
					success : function(form, action) {
						Ext.Msg.alert("提示", action.result.tip.msg);
						edit_form_panel_win.close();
						dataStore.load();
					},
					failure : function(form, action) {
						Ext.Msg.alert("提示", "保存失败");
					}
				});
			}
		}
	} ]
});

var edit_form_panel_win = Ext.create("Ext.Window", {
	title : "编辑",
	closeAction : "hide",
	items : edit_form_panel,
	buttonAlign : "center",
	width : 1100, // 宽度
	height : 700, // 长度
	layout : "fit", // 窗口布局类型
	maximizable : true, // 设置是否可以最大化,
	modal:true
});

function myEdit(kid) {
	Ext.Ajax.request({
		url : "/traceback/shop/grid/edit_form.htm?kid=" + kid,
		success : function(response) {
			var json = Ext.JSON.decode(response.responseText);
			edit_form_panel.getForm().reset();
			edit_form_panel.getForm().setValues(json);
			
			ed_context = json.content;
			ed_submiting = true;
			edit_form_panel_win.show();
		},
		failure : function(response) {
			Ext.Msg.alert("提示", "操作失败!");
		}
	});
}// #myEdit