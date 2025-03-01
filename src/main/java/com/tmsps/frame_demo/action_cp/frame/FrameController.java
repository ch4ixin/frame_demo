package com.tmsps.frame_demo.action_cp.frame;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tmsps.frame_demo.base.action.ProjBaseAction;
import com.tmsps.frame_demo.model.t_fk_admin;
import com.tmsps.frame_demo.service.MenuService;
import com.tmsps.frame_demo.util.ChkTools;
import com.tmsps.frame_demo.util.tree.TreeTools;
import com.tmsps.frame_demo.web.SessionTools;

@Controller
@Scope("prototype")
@RequestMapping("/cp/frame")
public class FrameController extends ProjBaseAction {

	@Autowired
	private MenuService menuService;

	@SuppressWarnings("unchecked")
	@RequestMapping("/frame")
	public ModelAndView frame() {
		t_fk_admin admin = SessionTools.getCurrentLoginCpAdmin();
		String role_id = admin.getRole_id();
		List<Map<String, Object>> list;
		List<Map<String, Object>> sublist;
		
		if(ChkTools.isNull(role_id)){
			list = menuService.selectFirstLevelMenuList("cp系统");
			sublist = menuService.selectAllMenuList("cp系统");
		}else{
			Map<String,Object> map = menuService.selectRoleMenuList(role_id);
			list = (List<Map<String, Object>>) map.get("first");
			sublist = (List<Map<String, Object>>) map.get("all");
		}
		
		ModelAndView mv = new ModelAndView("/jsp_cp/frame/frame");
		mv.addObject("list",list);
		mv.addObject("sublist",sublist);
		mv.addObject("admin",admin);
		return mv;
	}

	@RequestMapping("/left_data")
	@ResponseBody
	public String left_data() {
		t_fk_admin admin = SessionTools.getCurrentLoginCpAdmin();

		List<Map<String, Object>> list = null;
		if (ChkTools.isNull(admin.getRole_id())) {
			list = menuService.selectMenuAuthList("cp系统");
		} else {
			list = menuService.selectMenuAuthList("cp系统");
		}
		return TreeTools.turnListToTree(list);
	}
}