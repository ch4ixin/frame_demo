package com.tmsps.frame_demo.util.wx.will_del;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.tmsps.ne4Weixin.api.BaseAPI;
import com.tmsps.ne4Weixin.config.WxConfig;
import com.tmsps.frame_demo.util.wx.QywxConfigTools;

/**
 * 多媒体api
 * 
 * @author L.cm
 */
public class MediaApi extends BaseAPI {

	public MediaApi(WxConfig config) {
		super(config);
	}

	static WxConfig wxConfig = QywxConfigTools.getWxConfig();

	// 文档地址：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
	private static final String getTempMediaUrl = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s";
	private static final String getQyWxTempMediaUrl = "https://qyapi.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s";

	public void getTempMedia(String media_id, OutputStream out) {
		String url = String.format(getTempMediaUrl, config.getAccessToken(), media_id);

		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 设置代理
		InputStream input = null;
		try {
			HttpGet httpget = new HttpGet(url);
			HttpResponse response = httpClient.execute(httpget);
			HttpEntity entity = response.getEntity();
			input = entity.getContent();
			try {
				IOUtils.copy(input, out);
			} finally {
				IOUtils.closeQuietly(out);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(input);
		}
	}
	
	public void getQyWxTempMedia(String media_id, OutputStream out) {
		String url = String.format(getQyWxTempMediaUrl, config.getAccessToken(), media_id);

		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 设置代理
		InputStream input = null;
		try {
			HttpGet httpget = new HttpGet(url);
			HttpResponse response = httpClient.execute(httpget);
			HttpEntity entity = response.getEntity();
			input = entity.getContent();
			try {
				IOUtils.copy(input, out);
			} finally {
				IOUtils.closeQuietly(out);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(input);
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		OutputStream out = new FileOutputStream("C:\\Users\\Chailei\\Desktop\\1.png");
		new MediaApi(QywxConfigTools.getWxConfig()).getQyWxTempMedia("1UNbKlnsXnviOGEERALzz-Pye6C0OgBu9gfYmscIAqNWilgdpYtoXTkCunm7vHPRf", out);
	}

}
