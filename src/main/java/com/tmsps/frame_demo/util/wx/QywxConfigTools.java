package com.tmsps.frame_demo.util.wx;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.tmsps.ne4Weixin.config.WxConfig;

public class QywxConfigTools {

	private static Logger log = LoggerFactory.getLogger(QywxConfigTools.class);
	private static Cache<String, WxConfig> cache = CacheBuilder.newBuilder().build();

	private QywxConfigTools() {
	}

	public static WxConfig getWxConfig() {
		WxConfig wc = null;
		try {
			wc = cache.get("default", new Callable<WxConfig>() {
				@Override
				public WxConfig call() throws Exception {
					log.warn("not find cache and new it");
					//太原科技大学 企业微信
					String appid = "ww1ba7fb0be6e718ff";
					String secret = "wK2HG5MsM8Qk6d12T8oSGgJ3TWaaEgxV3A-ReL8jOUk";//
//					String secret = "i7LO0pN7GqAQBY99jJjGXdyl04aAwzM7f6DlNAUredc";//新生检录
					String encodingaeskey = "";
					String token = "";

					WxConfig wxConfig = new WxConfig(appid, secret, encodingaeskey, false, token, 3600);

					return wxConfig;
				}
			});
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return wc;
	}

}
