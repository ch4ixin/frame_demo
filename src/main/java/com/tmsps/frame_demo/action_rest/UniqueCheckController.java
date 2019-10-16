package com.tmsps.frame_demo.action_rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmsps.frame_demo.base.action.ProjBaseAction;
import com.tmsps.frame_demo.service.DictService;
import com.tmsps.frame_demo.util.json.JsonTools;

/**
 * 唯一性校验通用函数
 * 
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/valid")
public class UniqueCheckController extends ProjBaseAction {

	@Autowired
	private DictService dictService;

	@RequestMapping("/check_unique")
	@ResponseBody
	public String check_unique(String table, String field, String value) throws Exception {
		boolean b = dictService.selectTableFindValue(table, field, value);
		result.put("fail", b + "");
		return JsonTools.toJson(result);
	}

	@RequestMapping("/check_unique_notme")
	@ResponseBody
	public String check_unique_notme(String table, String field, String value, String kid) throws Exception {
		boolean b = dictService.selectTableFindValueNotme(table, field, value, kid);
		result.put("fail", b + "");
		return JsonTools.toJson(result);
	}
	
	@RequestMapping("/check_unique_id")
	@ResponseBody
	public String check_unique_id(String table, String field) throws Exception {
		boolean b = dictService.contain(table, field);
		result.put("fail", b + "");
		return JsonTools.toJson(result);
	}
}