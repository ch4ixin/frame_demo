package com.tmsps.frame_demo.util.ip;

import javax.servlet.http.HttpServletRequest;

import com.tmsps.frame_demo.web.WebTools;


public class IPTools {

	public static String getIP() {
		HttpServletRequest req = WebTools.getRequest();
		String ip = req.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getRemoteAddr();
		}

		return ip;
	}
}
