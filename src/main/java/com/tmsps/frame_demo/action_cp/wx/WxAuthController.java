package com.tmsps.frame_demo.action_cp.wx;

import com.tmsps.ne4Weixin.api.MenuAPI;
import com.tmsps.ne4Weixin.beans.BaseBean;
import com.tmsps.frame_demo.base.action.ProjBaseAction;
import com.tmsps.frame_demo.base.tip.Tip;
import com.tmsps.frame_demo.model.t_fk_wx_auth;
import com.tmsps.frame_demo.service.AuthService;
import com.tmsps.frame_demo.util.json.JsonTools;
import com.tmsps.frame_demo.util.tree.TreeTools;
import com.tmsps.frame_demo.util.tree.WxTreeTools;
import com.tmsps.frame_demo.util.wx.QywxConfigTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 微信菜单
 * 
 * @author chaixin
 *
 */
@Controller("wxAuth")
@Scope("prototype")
@RequestMapping("/cp/wx/auth")
public class WxAuthController extends ProjBaseAction {

	@Autowired
	private AuthService authService;

	@RequestMapping("/auth_data")
	@ResponseBody
	public String auth_data_shop() {
		List<Map<String, Object>> list = authService.selectAuthList();
		return TreeTools.turnListToTree(list);
	}

	@RequestMapping("/add")
	@ResponseBody
	public void add(t_fk_wx_auth auth, String up_code) {
		auth.setCode(up_code + auth.getCode());
		bs.saveObj(auth);

		this.setTipMsg(true, "保存成功!", Tip.Type.success);
	}

	@RequestMapping("/edit_form")
	@ResponseBody
	public String edit_form(String kid) {
		t_fk_wx_auth auth = bs.findById(kid, t_fk_wx_auth.class);
		return JsonTools.toJson(auth);
	}

	@RequestMapping("/edit")
	@ResponseBody
	public void edit(t_fk_wx_auth auth) {
		t_fk_wx_auth authDb = bs.findById(auth.getKid(), t_fk_wx_auth.class);
		authDb.setName(auth.getName());
		authDb.setCode(auth.getCode());
		authDb.setUrl(auth.getUrl());
		bs.updateObj(authDb);

		this.setTipMsg(true, "保存成功!", Tip.Type.success);
	}

	@RequestMapping("/del")
	@ResponseBody
	public void del(String kid) {
		t_fk_wx_auth auth = bs.findById(kid, t_fk_wx_auth.class);
		boolean isSubNode = authService.isHaveSubAuth(auth.getCode());
		if (isSubNode) {
			this.setTipMsg(true, "删除失败!存在子节点!", Tip.Type.success);
			return;
		}

		bs.deleteObjById(kid, t_fk_wx_auth.class);

		this.setTipMsg(true, "删除成功!", Tip.Type.success);
	}

	@RequestMapping("/sync")
	@ResponseBody
	public void sync() {
		MenuAPI api = new MenuAPI(QywxConfigTools.getWxConfig());
		List<Map<String, Object>> list = authService.selectAuthList();
		String json = WxTreeTools.turnListToTree(list);
		logger.info(json);
		BaseBean createMenu = api.createMenu(json);

		if (createMenu.getErrcode() == null || createMenu.getErrcode() == 0) {
			this.setTipMsg(true, "同步成功!", Tip.Type.success);
		}else{
			logger.error(createMenu.toJSON());
			this.setTipMsg(true, "同步失败!"+createMenu.getErrmsg(), Tip.Type.error);
		}
	}
	
	@RequestMapping("/check")
	@ResponseBody
	public String check(){
		MenuAPI api = new MenuAPI(QywxConfigTools.getWxConfig());
		String json=api.getMenu();
		System.out.println(json);
		return JsonTools.toJson(json);
	}

}
