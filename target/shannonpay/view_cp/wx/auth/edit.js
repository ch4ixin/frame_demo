
	//校验 code 长度
	var codeLength = 0;
	
	var edit_form_panel = Ext.create("Ext.form.Panel", {
		url : getServerHttp() + "/cp/wx/auth/edit.htm",
		buttonAlign : "center",
		bodyStyle : "padding: 10px;",
		width : 500,
		defaultType : "textfield",
		items : [ {
			id : "kid",
			name : "kid",
			hidden : true
		}, {
			id : "code",
			fieldLabel : "编号",
			name : "code",
			editable:false
		}, {
			fieldLabel : "名称",
			name : "name",
			allowBlank : false
		}, {
			fieldLabel : "URL",
			name : "url"
		}, ],
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
							edit_form_panel_win.close();
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

	var edit_form_panel_win = Ext.create("Ext.Window", {
		title : "菜单编辑",
		closeAction : "hide",
		items : edit_form_panel
	});

	function myEdit(kid) {
		Ext.Ajax.request({
			url : getServerHttp() + "/cp/wx/auth/edit_form.htm?kid=" + kid,
			success : function(response) {
				console.log(response.responseText);
				var json = Ext.JSON.decode(response.responseText);
				edit_form_panel.getForm().reset();
				edit_form_panel.getForm().setValues(json);
				edit_form_panel_win.show();
				
				//alert(edit_form_panel.getForm().findField("kid").getValue());
				codeLength = Ext.getCmp("code").getValue().length;
			},
			failure : function(response) {
				Ext.Msg.alert("提示", "操作失败!");
			}
		});
	}//#myEdit
