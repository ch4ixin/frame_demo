var edit_form_panel = Ext.create("Ext.form.Panel", {
	url : "/shannonpay/cp/car/edit.htm",
	buttonAlign : "center",
	bodyStyle : "padding: 10px;",
	defaultType : "textfield",
	items : [ {
		fieldLabel : "司机姓名",
		name : "name",
		allowBlank : false
	}, {
		fieldLabel : "车牌号码",
		name : "code",
		allowBlank : false
	},{
		xtype:'datefield',
		fieldLabel : "载货日期",
		name : "loading_date_note",
		format:'Y-m-d H:i:s',
		allowBlank : false
	},{
		fieldLabel : "实收（kg）",
		name : "real",
		allowBlank : false
	},{
		fieldLabel : "毛重（kg）",
		name : "gross_weight",
		allowBlank : false
	},{
		fieldLabel : "起始地点",
		name : "start_locale",
		allowBlank : false
	},{
		fieldLabel : "到达地点",
		name : "end_locale",
		allowBlank : false
	},{
		fieldLabel : "过路费用（元）",
		name : "road_toll",
		allowBlank : false
	},{
		fieldLabel : "加油费用（元）",
		name : "refuel_toll",
		allowBlank : false
	},{
		fieldLabel : "餐饮住宿（元）",
		name : "catering_toll",
		allowBlank : false
	},{
		fieldLabel : "卸车费用（元）",
		name : "unload_toll",
		allowBlank : false
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

var edit_form_panel_win = Ext.create("Ext.Window", {
	title : "修改",
	closeAction : "hide",
	items : edit_form_panel,
	modal:true
});

function myEdit(kid) {
	Ext.Ajax.request({
		url : "/shannonpay/cp/car/edit_form.htm?kid=" + kid,
		success : function(response) {
			var json = Ext.JSON.decode(response.responseText);
			edit_form_panel.getForm().reset();

			edit_form_panel.getForm().setValues(json);
			edit_form_panel.getForm().findField('loading_date_note').setValue(dateFormat_2(json.loading_date));
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