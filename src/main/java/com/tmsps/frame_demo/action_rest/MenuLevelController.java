package com.tmsps.frame_demo.action_rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmsps.frame_demo.base.action.ProjBaseAction;
import com.tmsps.frame_demo.service.MenuLevelService;
import com.tmsps.frame_demo.util.ChkTools;
import com.tmsps.frame_demo.util.json.JsonTools;

@Controller
@Scope("prototype")
@RequestMapping("/menu")
public class MenuLevelController extends ProjBaseAction {

	@Autowired
	private MenuLevelService menuLevelService;

    @RequestMapping("/all_data_by_field")
	@ResponseBody
	public String all_data_by_field(String table, String field, String value) throws Exception {

		if (ChkTools.isNotNull(value)) {
			List<Map<String, Object>> list = menuLevelService.selectTableByFieldList(table, field, value);
			result.put("list", list);
		}
		return JsonTools.toJson(result);
	}
}