package com.tmsps.frame_demo.service;

import org.springframework.stereotype.Service;

import com.tmsps.frame_demo.base.service.BaseService;
import com.tmsps.frame_demo.model.t_fk_website;
import com.tmsps.frame_demo.util.ChkTools;

@Service
public class WebsiteService extends BaseService {

	public t_fk_website findWebsite() {
		// TODO 查找站点设置
		String sql = "select * from t_fk_website t where t.status=0 and t.code='website' ";

		t_fk_website web = bs.findObj(sql, t_fk_website.class);
		if (ChkTools.isNull(web)) {
			web = new t_fk_website();
			web.setStatus(0);
			web.setCode("website");
			bs.saveObj(web);
		}
		return web;
	}
}
