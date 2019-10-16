var add_form_panel_win;
var add_form_panel;

	add_form_panel = Ext.create("Ext.form.Panel", {
		url : "/frame_demo/cp/equipment/add.htm",
		buttonAlign : "center",
		bodyStyle : "padding: 10px;",
		defaultType : "textfield",
		items : [{
			xtype : 'combo',
			anchor : '100%',
			editable:false,
			minChars : 1,
			fieldLabel : "单位",
			name : "units_id",
			emptyText : '请选择单位',
			store : comboxStore,
			displayField : 'name',
			valueField : 'kid',
			hiddenName : 'units_id',
			multiSelect:false,
			mode : 'remote',
			triggerAction : 'all',
			selectOnFocus : true,
			forceSelection : true,
		},{
			fieldLabel : "名称",
			name : "name",
			allowBlank : false
		}, {
			fieldLabel : "版本型号",
			name : "version",
			allowBlank : false
		}, {
			fieldLabel : "MAC地址",
			name : "mac_address",
			allowBlank : false,
		}, {
			fieldLabel : "运行内存容量",
			name : "operating_capacity",
			allowBlank : false,
		}, {
			fieldLabel : "存储内存容量",
			name : "internal_storage",
			allowBlank : false,
		}, {
			xtype : 'datefield',
			fieldLabel : "保修截止期限",
			format: 'Y-m-d H:i:s', 
			anchor : '100%',
			name : "repair_deadline_note",
			editable:false,	
			allowBlank : false
		}, {
			fieldLabel : "备注",
			name : "note",
		},{
			xtype : "radiogroup",
			fieldLabel : "状态",
			items : [ {
				name : "status_sys",
				boxLabel : "正常状态",
				inputValue : "正常状态",
			}, {
				name : "status_sys",
				boxLabel : "锁定限制",
				inputValue : "锁定限制",
				checked : true
			} ]
		}],
		buttons : [ {
			text : "保存",
			formBind : true, // only enabled once the form is valid
			disabled : true,
			handler : function() {
				var form = this.up("form").getForm();
				
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
		title : "设备添加",
		closeAction : "hide",
		items : add_form_panel,
		modal:true
	});

function myAdd() {
	add_form_panel.getForm().reset();
	add_form_panel_win.show();
}