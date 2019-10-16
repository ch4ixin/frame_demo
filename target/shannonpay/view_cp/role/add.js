var add_form_panel;
var add_form_panel_win;
Ext.onReady(function() {
	add_form_panel = Ext.create("Ext.form.Panel", {
		url : getServerHttp()+"/cp/role/add.htm",
		buttonAlign : "center",
		bodyStyle : "padding: 10px;",
		defaultType : "textfield",
		items : [ {
			fieldLabel : "角色名 ",
			name : "name",
			allowBlank : false
		}, {
			fieldLabel : "权限",
			name : "codes",
			hidden : true
		}, {
			fieldLabel : "id",
			name : "kid",
			hidden : true
		}, {
			xtype : "fieldcontainer",
			fieldLabel : "权限选择",
			layout : "hbox",
			items : [ treePanel ]
		}, {
			fieldLabel : "备注",
			name : "note",
			hidden : true
		} ],
		buttons : [ {
			text : "保存",
			formBind : true, // only enabled once the form is valid
			disabled : true,
			handler : function() {
				var form = this.up("form").getForm();

				// 设置选择框
				var codes = treePanel.getLeafIdSelections();

				form.setValues({
					"codes" : codes
				});

				if (form.isValid()) {
					form.submit({
						waitMsg : "保存中...",
						success : function(form, action) {
							Ext.Msg.alert("提示", action.result.tip.msg);
							add_form_panel_win.close();
							dataStore.load();
						},
						failure : function(form, action) {
							Ext.Msg.alert("提示", action.result.tip.msg);
						}
					});
				}
			}
		} ]
	});

	add_form_panel_win = Ext.create("Ext.Window", {
		title : "权限组添加 ",
		closeAction : "hide",
		items : add_form_panel
	});
});

function myAdd() {
	add_form_panel.getForm().reset();
	treePanel.setSelections("");
	// add_form_panel.getForm().findField("name").readOnly = false;
	add_form_panel_win.show();
}

function myAdds(name, id) {
	add_form_panel.getForm().reset();
	treePanel.setSelections("");
	add_form_panel.getForm().setValues({
		"name" : name,
		"kid" : id
	});
	// add_form_panel.getForm().findField("name").readOnly = true;
	add_form_panel_win.show();
}
