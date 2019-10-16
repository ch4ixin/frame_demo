	var units_id = '';	
	
//	var sm = new Ext.selection.CheckboxModel({});//		checkOnly : false,singleSelect : true
	
	var equipmentDataStore = Ext.create('Ext.data.Store', {
		remoteSort : true,
		autoLoad : true,
		pageSize : 10,
		sorters : {
			property : 'name',
			direction : 'ASC'
		},
		proxy : {
			type : "ajax",
			url : "/frame_demo/shop/ai/units/equipment/list_data.htm",
			reader : {
				type : 'json',
				root : 'list',
				totalProperty : 'page.totalRow'
			}
		},
		listeners : {
			'beforeload' : function(store, op, options) {
				var params = {units_id : units_id};
				Ext.apply(equipmentDataStore.proxy.extraParams, params);
			}
		}
	}); //#equipmentDataStore
	
	var equipmentStore = Ext.create('Ext.data.Store', {
		remoteSort : true,
		autoLoad : true,
		id : 'store',
		sorters : {
			property : 'created',
			direction : 'DESC'
		},
		proxy : {
			type : "ajax",
			url : "/frame_demo/shop/ai/units/equipment/list_data_combox.htm",
			reader : {
				type : 'json',
				root : 'list',
				totalProperty : 'page.rowTotal'
			}
		}
	}); //#equipmentStore
	
	var add_equipment_panel = Ext.create('Ext.grid.Panel', {
		store : equipmentStore,
		width : 570,
		height : 250, // 长度
		columns : [{
			text : '名称',
			dataIndex : 'name',
			flex : 0.5
		},{
			text : '机器编号',
			dataIndex : 'mac_address',
			flex : 1
		}, {
			text : '保修截止',
			dataIndex : 'repair_deadline',
			flex : 1,
			renderer : function(val) {
				if (val != '') {
					return Ext.Date.format(new Date(val), "Y-m-d H:i:s");
				}
			}
		}, {
			text : '状态',
			dataIndex : 'status_sys',
			flex : 0.5
		}],selModel : new Ext.selection.CheckboxModel({})
	});
	
	var edit_equipment_panel = Ext.create('Ext.grid.Panel', {
		store : equipmentStore,
		width : 570,
		height : 250, // 长度
		columns : [{
			text : '名称',
			dataIndex : 'name',
			flex : 0.5
		},{
			text : '机器编号',
			dataIndex : 'mac_address',
			flex : 1
		}, {
			text : '保修截止',
			dataIndex : 'repair_deadline',
			flex : 1,
			renderer : function(val) {
				if (val != '') {
					return Ext.Date.format(new Date(val), "Y-m-d H:i:s");
				}
			}
		}, {
			text : '状态',
			dataIndex : 'status_sys',
			flex : 0.5
		}],selModel : new Ext.selection.CheckboxModel({})
	});
	
	var find_equipment_panel = Ext.create('Ext.grid.Panel', {
		tbar : [ {
			text : '新增',
			xtype : 'button',
			icon : jcapp.getIcon("add.png"),
			handler : function() {
				myEquipmentAdd();
			}
		} ],
		store : equipmentDataStore,
		buttonAlign : "center",
		dockedItems : [ {
			xtype : 'pagingtoolbar',
			store : equipmentDataStore, // same store GridPanel is using
			dock : 'bottom',
			displayInfo : true
		} ],
		columns : [{
			text : '考勤类目',
			dataIndex : 'name',
			flex : 1
		}, {
			text : '开始日期',
			dataIndex : 'start_time',
			flex : 1,
			renderer : function(val) {
				if (val != '') {
					return Ext.Date.format(new Date(val), "Y-m-d ");
				}
			}
		}, {
			text : '结束日期',
			dataIndex : 'end_time',
			flex : 1,
			renderer : function(val) {
				if (val != '') {
					return Ext.Date.format(new Date(val), "Y-m-d ");
				}
			}
		}, {
			text : '签到时间',
			dataIndex : 'sign_in_time',
			flex : 1,
			renderer : function(value) {
				return timeFormat(value);
			}
		}, {
			text : '签退时间',
			dataIndex : 'sign_out_time',
			flex : 1,
			renderer : function(value) {
				return timeFormat(value);
			}
		}, {
			xtype : "actioncolumn",
			align : "center",
			text : '操作',
			items : [{
				xtype : 'button',
				tooltip : '编辑',
				icon : jcapp.getIcon("edit.png"),
				handler : function(grid, rowIndex, colIndex) {
					var rec = grid.getStore().getAt(rowIndex);
					myEquipmentEdit(rec.get('kid'));
				}
			}, {
				xtype : "container"
			}, {
				xtype : 'button',
				tooltip : '删除',
				icon : jcapp.getIcon("delete.png"),
				handler : function(grid, rowIndex, colIndex) {
					var rec = grid.getStore().getAt(rowIndex);
					myEquipmentDel(rec.get('kid'));
				}
			} ]
		}]
	});

	var equipment_form_panel_win = Ext.create("Ext.Window", {
		title : "考勤管理",
		closeAction : "hide",
		buttonAlign : "center",
		closeAction : "hide",
		width : 800, // 宽度
		height : 590, // 长度
		layout : "fit", // 窗口布局类型
		maximizable : true, // 设置是否可以最大化,
		modal:true,
		items : [find_equipment_panel]
	});

	function myEquipment() {
		units_id = kid;
		equipment_form_panel_win.show();
	}//#myequipment
	
	function myEquipmentDel(kid) {
		Ext.Msg.confirm("提示:", "确定删除选定的记录?", function(e) {
			if (e == "yes") {
				Ext.Ajax.request({
					url : "/frame_demo/shop/ai/units/equipment/del.htm?kid=" + kid,
					success : function(response) {
						var json = Ext.JSON.decode(response.responseText);
						Ext.Msg.alert("提示", json.tip.msg);
						equipmentDataStore.load();
					},
					failure : function(response) {
						Ext.Msg.alert("提示", "操作失败!");
					}
				});
			}//#if
		});
	}//#myequipmentDel
	
	var add_equipment_form_panel = Ext.create("Ext.form.Panel", {
		url : "/frame_demo/shop/ai/units/equipment/add.htm",
		buttonAlign : "center",
		bodyStyle : "padding: 10px;",
		width : 700,
		defaultType : "textfield",
		anchor : '100%',
		items : [{
			fieldLabel : "units_id",
			name : "units_id",
			hidden : true
		}, {
			fieldLabel : "考勤类目",
			name : "name",
			anchor : '100%',
			allowBlank : false
		},{
			xtype : 'datefield',
			fieldLabel : "开始日期",
			format: 'Y-m-d H:i:s', 
			anchor : '100%',
			name : "start_time_note",
			editable:false,	
			allowBlank : false
		},{
			xtype : 'datefield',
			fieldLabel : "结束日期",
			format: 'Y-m-d H:i:s', 
			anchor : '100%',
			name : "end_time_note",
			editable:false,	
			allowBlank : false
		},{
			xtype: "timefield",
			editable:false,
			fieldLabel: "签到时间",
			anchor : '100%',
			name : "sign_in_time_note",
			format:'H:i',//时间格式G（24小时进制）、i、s（时分秒，g是12小时进制）
			increment:30,//跨度，默认是15分钟
			allowBlank : false
		}, {
			xtype: "timefield",
			editable:false,
			fieldLabel: "签退时间",
			anchor : '100%',
			name : "sign_out_time_note",
			format:'H:i',//时间格式G（24小时进制）、i、s（时分秒，g是12小时进制）
			increment:30,//跨度，默认是15分钟
			allowBlank : false
		}, {
			xtype : "fieldcontainer",
			fieldLabel : "终端绑定",
			layout : "hbox",
			anchor : '100%',
			items : add_equipment_panel
		},{
			fieldLabel : "equipment_ids",
			name : "equipment_ids",
			hidden : true
		}],
		buttons : [ {
			text : "保存",
			formBind : true, //only enabled once the form is valid
			disabled : true,
			handler : function() {
				var form = this.up("form").getForm();
				
				var equipment_ids = "";  
				var selectedData = add_equipment_panel.getSelectionModel().getSelection();
				
				for (var i = 0; i < selectedData.length; i++) {    
					console.log(selectedData[i].get("mac_address"));
					equipment_ids += selectedData[i].get("mac_address")+",";    
				}  
				equipment_ids = equipment_ids.substring(0,equipment_ids.length-1);
//				console.log(equipment_ids);
				form.setValues({
					"equipment_ids" : equipment_ids
				});
				
				if (form.isValid()) {
					form.submit({
						waitMsg : "保存中...",
						success : function(form, action) {
							Ext.Msg.alert("提示", action.result.tip.msg);
							add_equipment_form_panel_win.close();
							equipmentDataStore.load();
						},
						failure : function(form, action) {
							Ext.Msg.alert("提示", "新增失败");
						}
					});
				}
			}
		} ]
	});

	var add_equipment_form_panel_win = Ext.create("Ext.Window", {
		title : "新增",
		closeAction : "hide",
		items : add_equipment_form_panel,
		modal:true
	});

	function myEquipmentAdd(up_code, up_name) {
		add_equipment_form_panel.getForm().reset();
		equipmentStore.load();
		add_equipment_form_panel.getForm().findField('units_id').setValue(units_id);
		add_equipment_form_panel_win.show();
	}
	
	
	var edit_equipment_form_panel = Ext.create("Ext.form.Panel", {
		url : "/frame_demo/shop/ai/units/equipment/edit.htm",
		buttonAlign : "center",
		bodyStyle : "padding: 10px;",
		width : 700,
		defaultType : "textfield",
		items : [ {
			id : "kid",
			name : "kid",
			hidden : true
		}, {
			fieldLabel : "考勤类目",
			name : "name",
			anchor : '100%',
			allowBlank : false
		}, {
			fieldLabel : "units_id",
			name : "units_id",
			hidden : true
		},{
			xtype : 'datefield',
			fieldLabel : "开始日期",
			format: 'Y-m-d H:i:s', 
			anchor : '100%',
			name : "start_time_note",
			editable:false,	
			allowBlank : false
		},{
			xtype : 'datefield',
			fieldLabel : "结束日期",
			format: 'Y-m-d H:i:s', 
			anchor : '100%',
			name : "end_time_note",
			editable:false,	
			allowBlank : false
		},{
			xtype: "timefield",
			editable:false,
			fieldLabel: "签到时间",
			anchor : '100%',
			name : "sign_in_time_note",
			format:'H:i',//时间格式G（24小时进制）、i、s（时分秒，g是12小时进制）
			increment:30,//跨度，默认是15分钟
			allowBlank : false
		}, {
			xtype: "timefield",
			editable:false,
			fieldLabel: "签退时间",
			anchor : '100%',
			name : "sign_out_time_note",
			format:'H:i',//时间格式G（24小时进制）、i、s（时分秒，g是12小时进制）
			increment:30,//跨度，默认是15分钟
			allowBlank : false
		}, {
			xtype : "fieldcontainer",
			fieldLabel : "终端绑定",
			layout : "hbox",
			anchor : '100%',
			items : edit_equipment_panel
		},{
			fieldLabel : "equipment_ids",
			name : "equipment_ids",
			hidden : true
		}],
		buttons : [ {
			text : "保存",
			formBind : true, //only enabled once the form is valid
			disabled : true,
			handler : function() {
				var form = this.up("form").getForm();
				
				var equipment_ids = "";  
				var selectedData = edit_equipment_panel.getSelectionModel().getSelection();
				
				for (var i = 0; i < selectedData.length; i++) {    
					console.log(selectedData[i].get("mac_address"));
					equipment_ids += selectedData[i].get("mac_address")+",";    
				}
				equipment_ids = equipment_ids.substring(0,equipment_ids.length-1);
//				console.log(equipment_ids);
				form.setValues({
					"equipment_ids" : equipment_ids
				});
				
				if (form.isValid()) {
					form.submit({
						waitMsg : "保存中...",
						success : function(form, action) {
							Ext.Msg.alert("提示", action.result.tip.msg);
							edit_equipment_form_panel_win.close();
							equipmentDataStore.load();
						},
						failure : function(form, action) {
							Ext.Msg.alert("提示", "保存失败");
						}
					});
				}
			}
		} ]
	});

	var edit_equipment_form_panel_win = Ext.create("Ext.Window", {
		title : "编辑",
		closeAction : "hide",
		items : edit_equipment_form_panel,
		modal:true
	});

	function myEquipmentEdit(kid) {
		edit_equipment_panel.getSelectionModel().clearSelections();
		edit_equipment_panel.getView().refresh();
		
		Ext.Ajax.request({
			url : "/frame_demo/shop/ai/units/equipment/edit_form.htm?kid=" + kid,
			success : function(response) {
				var json = Ext.JSON.decode(response.responseText);
				edit_equipment_form_panel.getForm().reset();
				edit_equipment_form_panel.getForm().setValues(json);
				
				if(json.hasOwnProperty("equipment_ids")){
					var equipment_ids = [];
					equipment_ids = json.equipment_ids.split(",");
					for (var i = 0; i < equipment_ids.length; i++) {
						equipment_id = equipment_ids[i];
						
						var store = Ext.StoreMgr.get('store');
						store.each(function(list) {
							var index = store.find('mac_address', equipment_id);
//							console.log(index);
							edit_equipment_panel.getSelectionModel().select(index,
									true);
						});
					}
				}else{
					equipmentStore.load();
				}

				edit_equipment_form_panel.getForm().findField('start_time_note').setValue(dateFormat_2(json.start_time));
				edit_equipment_form_panel.getForm().findField('end_time_note').setValue(dateFormat_2(json.end_time));
				edit_equipment_form_panel.getForm().findField('sign_in_time_note').setValue(timeFormat(json.sign_in_time));
				edit_equipment_form_panel.getForm().findField('sign_out_time_note').setValue(timeFormat(json.sign_out_time));
				edit_equipment_form_panel_win.show();
			},
			failure : function(response) {
				Ext.Msg.alert("提示", "操作失败!");
			}
		});
	}//#myEdit
	
	
	/**
	 * 时间转换
	 */
	function timeFormat(longTypeDate){
		 var dateTypeDate = "";  
		 var date = new Date();  
		 date.setTime(longTypeDate);  
		 dateTypeDate += "" + getHours(date); //时  
		 dateTypeDate += ":" + getMinutes(date);  //分
		 return dateTypeDate; 
	}  
	//小时 
	function getHours(date){ 
	 var hours = ""; 
	 hours = date.getHours(); 
	 if(hours<10){  
	  hours = "0" + hours;  
	 }  
	 return hours;  
	} 
	//分 
	function getMinutes(date){ 
	 var minute = ""; 
	 minute = date.getMinutes(); 
	 if(minute<10){  
	  minute = "0" + minute;  
	 }  
	 return minute;  
	}