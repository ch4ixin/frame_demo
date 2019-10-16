package com.tmsps.frame_demo.action_moblie;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.tmsps.frame_demo.base.action.ProjBaseAction;
import com.tmsps.frame_demo.service.SettingService;
import com.tmsps.frame_demo.util.des.EncryptDES;
import com.tmsps.frame_demo.util.json.JsonTools;
import com.tmsps.frame_demo.util.wx.WxJsApiTools;
import com.tmsps.frame_demo.web.SessionTools;
import com.tmsps.frame_demo.web.WebTools;

@Controller
@Scope("prototype")
@RequestMapping("/wx")
public class WxIndexController extends ProjBaseAction {

    @Autowired
    public SettingService settingService;

	@RequestMapping("/index")
	public ModelAndView index() {
		String geturl = WebTools.getRefererUrl();
		WxJsApiTools jsConfig = WxJsApiTools.getJsApiConfigQywx(geturl);
		ModelAndView mv = new ModelAndView("/jsp_moblie/wx/index");
		mv.addObject("jsConfig", jsConfig);
		return mv;
	}
	
	@RequestMapping("/input")
	public ModelAndView input() throws Exception {
		String geturl = WebTools.getRefererUrl();
		WxJsApiTools jsConfig = WxJsApiTools.getJsApiConfigQywx(geturl);
		ModelAndView mv = new ModelAndView("/jsp_moblie/wx/input");
		mv.addObject("jsConfig", jsConfig);
		return mv;
	}

	@RequestMapping("/sendFaceData")
	@ResponseBody
    public String sendFaceData(String stdentid,String machinecode) throws IOException{
		SessionTools.put(SessionTools.MACHINECODE, machinecode);
		JSONObject json = JSONObject.parseObject(EncryptDES.sendFaceData(stdentid,machinecode));
		logger.info(json.toString());
		return JsonTools.toJson(json);
    }
}