var add_form_panel = Ext.create("Ext.form.Panel", {
		url : "/shannonpay/shop/ai/units/add.htm",
		buttonAlign : "center",
		bodyStyle : "padding: 10px;",
		width : 500,
		defaultType : "textfield",
		anchor : '100%',
		items : [ {
			name : "kid",
			hidden : true
		}, {
			fieldLabel : "父节点",
			name : "up_node",
			anchor : '100%',
			readOnly : true
		}, {
			xtype : "fieldcontainer",
			fieldLabel : "编号",
			layout : "hbox",
			items : [ {
				xtype : "textfield",
				anchor : '100%',
				id : "up_code",
				name : "up_code",
				readOnly : true
			}, {
				xtype : "textfield",
				anchor : '100%',
				name : "code",
				allowBlank : false,
				minLength : 3,
				maxLength : 3,
				validator : function() {
					var error = true;
					Ext.Ajax.request({
						url : '/shannonpay/valid/check_unique.htm',
						params : {
							table:'t_shop_units',
							field:'code',
							value : Ext.getCmp("up_code").getValue() + this.value
						},
						scope : true,
						async : false,
						method : 'POST',
						success : function(response) {
							var result = Ext.JSON.decode(response.responseText);
							if ("true" == result.fail) {
								error = "该编号己经存在,请重新输入！";
							}
						}
					});
					return error;
				}
			} ]
		}, {
			fieldLabel : "单位名称",
			name : "name",
			anchor : '100%',
			allowBlank : false
		}],
		buttons : [ {
			text : "保存",
			formBind : true, //only enabled once the form is valid
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
							Ext.Msg.alert("提示", "新增失败");
						}
					});
				}
			}
		} ]
	});

	var add_form_panel_win = Ext.create("Ext.Window", {
		title : "单位新增",
		closeAction : "hide",
		items : add_form_panel,
		modal:true
	});

	function myAdd(up_code, up_name) {
		add_form_panel.getForm().reset();
		add_form_panel.getForm().setValues({
			"up_node" : up_name + "[" + up_code + "]",
			"up_code" : up_code
		});
		add_form_panel_win.show();
	}
	
	