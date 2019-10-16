package com.tmsps.frame_demo.util.wx;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.tmsps.ne4Weixin.config.WxConfig;

public class WxConfigTools {

	private static Logger log = LoggerFactory.getLogger(WxConfigTools.class);
	private static Cache<String, WxConfig> cache = CacheBuilder.newBuilder().build();

	private WxConfigTools() {
	}

	public static WxConfig getWxConfig() {
		WxConfig wc = null;
		try {
			wc = cache.get("default", new Callable<WxConfig>() {
				@Override
				public WxConfig call() throws Exception {
					log.warn("not find cache and new it");
					//太原科技大学服务号
					String appid = "wxc666666c9bb2ede4";
					String secret = "061adc4383e8c77f51f1e2d52a9a690a";
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
