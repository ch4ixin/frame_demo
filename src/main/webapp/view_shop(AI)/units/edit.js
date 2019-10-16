	//校验 code 长度
	var codeLength = 0;
	
	var edit_form_panel = Ext.create("Ext.form.Panel", {
		url : "/frame_demo/shop/ai/units/edit.htm",
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
			anchor : '100%',
			name : "code",
			readOnly : true
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
		title : "单位编辑",
		closeAction : "hide",
		items : edit_form_panel,
		modal:true
	});

	function myEdit(kid) {
		Ext.Ajax.request({
			url : "/frame_demo/shop/ai/units/edit_form.htm?kid=" + kid,
			success : function(response) {
				console.log(response.responseText);
				var json = Ext.JSON.decode(response.responseText);
				edit_form_panel.getForm().reset();
				edit_form_panel.getForm().setValues(json);
				edit_form_panel_win.show();
				codeLength = Ext.getCmp("code").getValue().length;
			},
			failure : function(response) {
				Ext.Msg.alert("提示", "操作失败!");
			}
		});
	}//#myEdit

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