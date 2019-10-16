package com.tmsps.frame_demo.action_cp.app;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmsps.frame_demo.base.action.ProjBaseAction;
import com.tmsps.frame_demo.base.tip.Tip;
import com.tmsps.frame_demo.model.t_fk_version;
import com.tmsps.frame_demo.service.AppService;
import com.tmsps.frame_demo.util.json.JsonTools;

/**
 * APP版本管理
 * @author 张志亮
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/cp/app")
public class AppController extends ProjBaseAction {

	@Autowired
	private AppService appService;

	@RequestMapping("/list_data")
	@ResponseBody
	public void list_data() {

		List<Map<String, Object>> list = appService.selectAppList();
		result.put("list", list);
	}

	@RequestMapping("/add")
	@ResponseBody
	public void add(t_fk_version app) {
		bs.saveObj(app);

		this.setTipMsg(true, "保存成功!", Tip.Type.success);
	}

	@RequestMapping("/del")
	@ResponseBody
	public void del(String kid) {
		t_fk_version app = bs.findById(kid, t_fk_version.class);
		app.setStatus(-100);
		// bs.deleteObjById(kid, t_fk_setting.class);
		bs.updateObj(app);
		this.setTipMsg(true, "删除成功!", Tip.Type.success);
	}

	@RequestMapping("/edit_form")
	@ResponseBody
	public String edit_form(String kid) {
		t_fk_version set = bs.findById(kid, t_fk_version.class);
		return JsonTools.toJson(set);
	}

	@RequestMapping("/edit")
	@ResponseBody
	public void edit(t_fk_version app) {
		t_fk_version appDb = bs.findById(app.getKid(), t_fk_version.class);
		appDb.setType(app.getType());
		appDb.setVersion(app.getVersion());
		bs.updateObj(appDb);
		this.setTipMsg(true, "保存成功!", Tip.Type.success);
	}

}
