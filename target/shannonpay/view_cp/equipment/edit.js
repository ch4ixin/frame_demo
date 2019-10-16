var edit_form_panel;
var edit_form_panel_win;

	edit_form_panel = Ext.create("Ext.form.Panel", {
		url : "/shannonpay/cp/equipment/edit.htm",
		buttonAlign : "center",
		bodyStyle : "padding: 10px;",
		width : 500,
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
			anchor : '100%',
			name : "name",
			allowBlank : false
		}, {
			fieldLabel : "版本型号",
			anchor : '100%',
			name : "version",
			allowBlank : false
		}, {
			fieldLabel : "MAC地址",
			anchor : '100%',
			name : "mac_address",
			allowBlank : false
		}, {
			fieldLabel : "运行内存容量",
			anchor : '100%',
			name : "operating_capacity",
			allowBlank : false,
		}, {
			fieldLabel : "存储内存容量",
			name : "internal_storage",
			anchor : '100%',
			allowBlank : false,
		}, {
			xtype : 'datefield',
			fieldLabel : "保修截止期限",
			format: 'Y-m-d H:i:s', 
			anchor : '100%',
			name : "repair_deadline_note",
			editable:false,	
			allowBlank : false
		},{
			xtype : "radiogroup",
			anchor : '100%',
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
		}, {
			xtype : 'textarea',
			fieldLabel : "备注",
			anchor : '100%',
			name : "note",
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
		title : "设备编辑",
		closeAction : "hide",
		items : edit_form_panel,
		modal:true
	});

function myEdit(kid) {
	Ext.Ajax.request({
		url : "/shannonpay/cp/equipment/edit_form.htm?kid=" + kid,
		success : function(response) {
			var json = Ext.JSON.decode(response.responseText);
			edit_form_panel.getForm().reset();

			edit_form_panel.getForm().setValues(json);

			edit_form_panel.getForm().findField('repair_deadline_note').setValue(dateFormat_2(json.repair_deadline));
			edit_form_panel_win.show();
		},
		failure : function(response) {
			Ext.Msg.alert("提示", "操作失败!");
		}
	});
}// #myEdit

//返回 01-12 的月份值  
function getMonth(date){ 
  var month = ""; 
  month = date.getMonth() + 1; //getMonth()得到的月份是0-11 
  if(month<10){ 
    month = "0" + month; 
  } 
  return month; 
} 
//返回01-30的日期 
function getDay(date){ 
  var day = ""; 
  day = date.getDate(); 
  if(day<10){ 
    day = "0" + day; 
  } 
  return day; 
}

function dateFormat_2(longTypeDate){ 
  var dateType = ""; 
  var date = new Date(); 
  date.setTime(longTypeDate); 
  dateType = date.getFullYear()+"-"+getMonth(date)+"-"+getDay(date);//yyyy-MM-dd格式日期
  return dateType;
}