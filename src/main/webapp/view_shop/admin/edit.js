var edit_form_panel;
var edit_form_panel_win;

Ext.onReady(function() {
	edit_form_panel = Ext.create("Ext.form.Panel", {
		url : "/frame_demo/shop/admin/edit.htm",
		buttonAlign : "center",
		bodyStyle : "padding: 10px;",
		defaultType : "textfield",
		items : [{
			fieldLabel : "姓名",
			name : "name",
			allowBlank : false
		}, {
			fieldLabel : "手机号(账号)",
			name : "mobile",
			allowBlank : false/*,
			editable : false*/
		}, {
			fieldLabel : "密码",
			name : "pwd",
			inputType: 'password',
			allowBlank : false
		},{
			xtype : 'combo',
			editable:false,
			multiSelect:false,
			minChars : 1,
			fieldLabel : "权限组",
			name : "role_id",
			emptyText : '请选择权限组',
			store : comboxStore,
			displayField : 'name',
			valueField : 'kid',
			hiddenName : 'role_id',
			triggerAction : 'all',
			selectOnFocus : true,
			forceSelection : true,
			allowBlank : false
		},{
			xtype : 'combo',
			editable:false,
			multiSelect:false,
			minChars : 1,
			fieldLabel : "单位",
			name : "units_id",
			emptyText : '请选择单位',
			store : unitsStore,
			displayField : 'name',
			valueField : 'kid',
			hiddenName : 'units_id',
			triggerAction : 'all',
			selectOnFocus : true,
			forceSelection : true,
			allowBlank : false
		},{
			xtype : "fieldcontainer",
			fieldLabel : "单位组",
			layout : "hbox",
			items : [ editTreePanel ]
		}, {
			fieldLabel : "权限",
			name : "codes",
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
	
	edit_form_panel_win = Ext.create("Ext.Window", {
		title : "管理员编辑",
		closeAction : "hide",
		items : edit_form_panel,
		modal:true
	});
});

function myEdit(kid) {
	Ext.Ajax.request({
		url : "/frame_demo/shop/admin/edit_form.htm?kid=" + kid,
		success : function(response) {
			var json = Ext.JSON.decode(response.responseText);
			edit_form_panel.getForm().reset();

			edit_form_panel.getForm().setValues(json.admin);
			// 设置树的值
			editTreePanel.setSelections(json.admin["codes"]);
			edit_form_panel_win.show();
		},
		failure : function(response) {
			Ext.Msg.alert("提示", "操作失败!");
		}
	});
}// #myEdit