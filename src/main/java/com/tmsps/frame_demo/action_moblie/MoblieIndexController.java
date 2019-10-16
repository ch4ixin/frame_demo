package com.tmsps.frame_demo.action_moblie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tmsps.frame_demo.base.action.ProjBaseAction;
import com.tmsps.frame_demo.service.SettingService;
import com.tmsps.frame_demo.util.wx.WxJsApiTools;
import com.tmsps.frame_demo.web.WebTools;

@Controller
@Scope("prototype")
@RequestMapping("/moblie")
public class MoblieIndexController extends ProjBaseAction {

    @Autowired
    public SettingService settingService;
	
	@RequestMapping("/index")
	public ModelAndView index() {
		String geturl = WebTools.getRefererUrl();
		WxJsApiTools jsConfig = WxJsApiTools.getJsApiConfigWx(geturl);
		ModelAndView mv = new ModelAndView("/jsp_moblie/index");
		mv.addObject("jsConfig", jsConfig);
		return mv;
	}
	
	@RequestMapping("/wx_index")
	public ModelAndView wx_index() {
		String geturl = WebTools.getRefererUrl();
		WxJsApiTools jsConfig = WxJsApiTools.getJsApiConfigWx(geturl);
		ModelAndView mv = new ModelAndView("/jsp_moblie/wx_index");
		mv.addObject("jsConfig", jsConfig);
		return mv;
	}
	
	@RequestMapping("/input")
	public ModelAndView input(String units_id) {
		ModelAndView mv = new ModelAndView("/jsp_moblie/input");
		mv.addObject("units_id", units_id);
		return mv;
	}
}