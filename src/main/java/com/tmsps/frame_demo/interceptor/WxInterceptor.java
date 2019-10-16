package com.tmsps.frame_demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.tmsps.ne4Weixin.config.WxConfig;
import com.tmsps.ne4Weixin.utils.HttpClient;
import com.tmsps.frame_demo.util.wx.QywxConfigTools;

/**
 * 分页拦截器，定义拦截路径 /wx
 * 
 * @author uninf
 * 
 */
public class WxInterceptor implements HandlerInterceptor {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	static WxConfig wxConfig = QywxConfigTools.getWxConfig();
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}
	
	public static void main(String[] args) {
		String access_token = HttpClient.httpGet("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid="+wxConfig.getAppid()+"&corpsecret="+wxConfig.getSecret(), null);
		System.out.println(access_token);
		JSONObject user_info = JSONObject.parseObject(access_token);
		System.out.println(user_info.getString("access_token"));
	}
	
}