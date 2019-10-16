var add_form_panel_win;
var add_form_panel;

Ext.onReady(function() {
	add_form_panel = Ext.create("Ext.form.Panel", {
		url : "/shannonpay/shop/admin/add.htm",
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
			allowBlank : false,
			validator : function() {
				return uniqueValidator('t_shop_admin','mobile',this.value)
			}
		}, {
			fieldLabel : "密码",
			name : "pwd",
			allowBlank : false,
			
			inputType: 'password',
		}, {
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
		}, {
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
		}, {
			xtype : "fieldcontainer",
			fieldLabel : "单位组",
			layout : "hbox",
			items : [ treePanel ]
		},{
			fieldLabel : "codes",
			name : "codes",
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
							add_form_panel_win.close();
							dataStore.load();
						},
						failure : function(form, action) {
							Ext.Msg.alert("提示", "添加失败");
						}
					});
				}
			}
		} ]
	});
	
	add_form_panel_win = Ext.create("Ext.Window", {
		title : "管理员添加",
		closeAction : "hide",
		items : add_form_panel,
		modal:true
	});
});

function myAdd() {
	add_form_panel.getForm().reset();
	treePanel.setSelections("");
	add_form_panel_win.show();
}