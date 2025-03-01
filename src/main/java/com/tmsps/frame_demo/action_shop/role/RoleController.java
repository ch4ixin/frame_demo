package com.tmsps.frame_demo.action_shop.role;

import com.tmsps.frame_demo.base.action.ProjBaseAction;
import com.tmsps.frame_demo.base.tip.Tip;
import com.tmsps.frame_demo.model.t_shop_role;
import com.tmsps.frame_demo.service.ShopRoleService;
import com.tmsps.frame_demo.util.json.JsonTools;
import com.tmsps.frame_demo.web.SessionTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller("roleShop")
@Scope("prototype")
@RequestMapping("/shop/role")
public class RoleController extends ProjBaseAction {
	@Autowired
	private ShopRoleService shopRoleService;

	@RequestMapping("/list_data")
	@ResponseBody
	public void list_data() {
		String shopId = SessionTools.getCurrentShopId();
		List<Map<String, Object>> list = shopRoleService.selectRoleList(shopId, srh, sort_params, page);

		result.put("list", list);
	}
	
	@RequestMapping("/list_data_combox")
	@ResponseBody
	public void list_data_combox() {
		List<Map<String, Object>> list = shopRoleService.selectRoleList();
		result.put("list", list);
	}

	@RequestMapping("/del")
	@ResponseBody
	public void del(String kid) {
		t_shop_role role = bs.findById(kid, t_shop_role.class);
		role.setStatus(-100);
		bs.updateObj(role);

		this.setTipMsg(true, "删除成功!", Tip.Type.success);
	}

	@RequestMapping("/add")
	@ResponseBody
	public void add(t_shop_role role) {
		role.setShop_id(SessionTools.getCurrentShopId());
		bs.saveObj(role);
		this.setTipMsg(true, "保存成功!", Tip.Type.success);
	}

	@RequestMapping("/edit_form")
	@ResponseBody
	public String edit_form(String kid) {
		t_shop_role role = bs.findById(kid, t_shop_role.class);
		// System.out.println("==========="+role+"=================");
		return JsonTools.toJson(role);
	}

	@RequestMapping("/edit")
	@ResponseBody
	public void edit(t_shop_role role) {
		t_shop_role roleDb = bs.findById(role.getKid(), t_shop_role.class);
		// DbLogTools.log(roleDb);
		roleDb.setName(role.getName());
		roleDb.setCodes(role.getCodes());
		bs.updateObj(roleDb);
		this.setTipMsg(true, "保存成功!", Tip.Type.success);
	}

}
