package com.tmsps.frame_demo.action_moblie;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmsps.frame_demo.base.action.ProjBaseAction;
import com.tmsps.frame_demo.service.SettingService;
import com.tmsps.frame_demo.util.http.HttpJsonTools;

@Controller
@Scope("prototype")
@RequestMapping("/wx/mnp")
public class MnpIndexController extends ProjBaseAction {

	@Autowired
	public SettingService settingService;

	@RequestMapping("/get_openid")
	@ResponseBody
	public String xcx_get_openid(String appid, String secret, String code) throws IOException {
		String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + secret + "&js_code=" + code + "&grant_type=authorization_code";   //接口地址
		String body = HttpJsonTools.get(url);
		return body;
	}
}
