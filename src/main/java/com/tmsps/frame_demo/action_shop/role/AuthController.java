package com.tmsps.frame_demo.action_shop.role;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmsps.frame_demo.base.action.ProjBaseAction;
import com.tmsps.frame_demo.service.ShopMenuService;
import com.tmsps.frame_demo.util.tree.TreeTools;
import com.tmsps.frame_demo.web.SessionTools;

/**
 * 权限列表
 * 
 * @author Administrator
 *
 */
@Controller("shopAuth")
@Scope("prototype")
@RequestMapping("/shop/auth")
public class AuthController extends ProjBaseAction {

	@Autowired
	private ShopMenuService shopMenuService;

	// 权限组添加获取 权限树
	@RequestMapping("/auth_data_checkbo")
	@ResponseBody
	public String auth_data_checkbo() {
		List<Map<String, Object>> list = shopMenuService.selectMenuAuthShopList(SessionTools.getCurrentShop().getShop_menu_id());
		return TreeTools.turnListToTree(list, true);
	}

}
