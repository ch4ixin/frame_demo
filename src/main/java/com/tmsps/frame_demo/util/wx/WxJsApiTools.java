package com.tmsps.frame_demo.util.wx;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import com.alibaba.fastjson.JSONObject;
import com.tmsps.ne4Weixin.config.WxConfig;
import com.tmsps.ne4Weixin.encrypt.SHA1;
import com.tmsps.ne4Weixin.utils.HttpClient;
import com.tmsps.ne4Weixin.utils.StrUtil;
import com.tmsps.ne4spring.utils.PKUtil;
import com.tmsps.frame_demo.util.wx.will_del.PaymentKit;

public class WxJsApiTools {

	private String appId;
	private long timestamp;
	private String nonceStr;
	private String signature;
	
	// js sdk token
	private static String jsAccessToken;
	private final static AtomicBoolean jsAccessTokenTag = new AtomicBoolean(false);
	private static Long jsAccessTokenExpireTime = System.currentTimeMillis();

	static WxConfig wxConfig = QywxConfigTools.getWxConfig();
	
	public static WxJsApiTools getJsApiConfigQywx(String url) {
		WxJsApiTools jsConfig = new WxJsApiTools();
		jsConfig.setAppId(wxConfig.getAppid());
		jsConfig.setTimestamp(System.currentTimeMillis());
		jsConfig.setNonceStr(PKUtil.getPK());
		
		String access_token = HttpClient.httpGet("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid="+wxConfig.getAppid()+"&corpsecret="+wxConfig.getSecret(), null);
		access_token = JSONObject.parseObject(access_token).getString("access_token");
		
		String ticket = getJsAccessToken(access_token);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("noncestr", jsConfig.getNonceStr());
		params.put("jsapi_ticket", ticket);
		params.put("timestamp", jsConfig.getTimestamp() + "");
		params.put("url", url);
		try {
			String packageSign = PaymentKit.packageSign(params, false);
			String sign = SHA1.getSHA1HexString(packageSign);
			jsConfig.setSignature(sign);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsConfig;
	}
	
	public static WxJsApiTools getJsApiConfigWx(String url) {
		WxJsApiTools jsConfig = new WxJsApiTools();
		WxConfig wxConfig = WxConfigTools.getWxConfig();
		jsConfig.setAppId(wxConfig.getAppid());
		jsConfig.setTimestamp(System.currentTimeMillis());
		jsConfig.setNonceStr(PKUtil.getPK());

		String jsAccessToken = wxConfig.getJsAccessToken();

		Map<String, String> params = new HashMap<String, String>();
		params.put("noncestr", jsConfig.getNonceStr());
		params.put("jsapi_ticket", jsAccessToken);
		params.put("timestamp", jsConfig.getTimestamp() + "");
		params.put("url", url);
		try {
			String packageSign = PaymentKit.packageSign(params, false);
			String sign = SHA1.getSHA1HexString(packageSign);
			jsConfig.setSignature(sign);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsConfig;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	// 初始化企业微信 JsAccessToken
	public synchronized static void initJsAccessToken(String access_token) {
		jsAccessTokenTag.set(true);
		final String url = "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket";
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", access_token);
		JSONObject result = JSONObject.parseObject(HttpClient.httpGet(url, params));
		System.out.println("getJsAccessToken result is {}"+result.toString());
		if (result.getInteger("errcode") == 0) {
			jsAccessToken = result.getString("ticket");
			jsAccessTokenExpireTime = System.currentTimeMillis() + result.getLongValue("expires_in") * 1000;
		} else {
			System.out.println(result.getString("errmsg"));
		}
		jsAccessTokenTag.set(false);
	}

	public static String getJsAccessToken(String access_token) {
		if (StrUtil.isBlank(jsAccessToken)) {
			System.out.println("jsAccessToken is blank initJsAccessToken()");
			initJsAccessToken(access_token);
		} else {
			long diffTime = jsAccessTokenExpireTime - System.currentTimeMillis();
			if (diffTime < 3600 * 1000 && jsAccessTokenTag.compareAndSet(false, true)) {
				System.out.println("jsAccessToken is exceed the time limit, initJsAccessToken()");
				initJsAccessToken(access_token);
			}
		}
		return jsAccessToken;
	}
}
