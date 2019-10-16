var ad_context;
var add_form_panel = Ext.create("Ext.form.Panel", {
	url : "/traceback/shop/grid/add.htm",
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
		html : '<iframe src= "/traceback/view_shop(Lvliang)/grid/aeditors.html" width="100%" height="100%" marginwidth="0" framespacing="0" marginheight="0" frameborder="0" ></iframe>',
		width : '100%',
		height : 650,
		border : false,
		style : {
			marginBottom : '10px',
			marginLeft : '10px',
		}
	}, {
		fieldLabel : "名称",
		name : "name",
		anchor : '100%',	
		//hidden : true
	}, {
		xtype : 'textarea',
		width : 500,
		height : 300,
		name : 'content',
		fieldLabel : '内容',
		hidden : true
	}],
	buttons : [ {
		text : "保存",
		formBind : true, // only enabled once the form is valid
		disabled : true,
		handler : function() {
			var form = this.up("form").getForm();
			ad_submiting = true;
			form.findField("content").setValue(ad_context);
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

var add_form_panel_win = Ext.create("Ext.Window", {
	title : "新增",
	items : add_form_panel,
	buttonAlign : "center",
	closeAction : "hide",
	width : 1100, // 宽度
	height : 700, // 长度
	layout : "fit", // 窗口布局类型
	maximizable : true, // 设置是否可以最大化,
	modal:true
});

function myAdd(name) {
	Ext.Ajax.request({
		url : "/traceback/shop/grid/grid_template.htm?name=" + name,
		success : function(response) {
			var json = Ext.JSON.decode(response.responseText);
			add_form_panel.getForm().reset();
			add_form_panel.getForm().findField('name').setValue(json.name);
			ad_context = json.content;
			ad_submiting = true;
			add_form_panel_win.show();
		},
		failure : function(response) {
			Ext.Msg.alert("提示", "操作失败!");
		}
	});
}