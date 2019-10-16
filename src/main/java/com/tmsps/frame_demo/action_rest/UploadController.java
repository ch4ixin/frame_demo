package com.tmsps.frame_demo.action_rest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.tmsps.ne4Weixin.config.WxConfig;
import com.tmsps.ne4Weixin.utils.HttpClient;
import com.tmsps.frame_demo.base.action.ProjBaseAction;
import com.tmsps.frame_demo.model.t_fk_file;
import com.tmsps.frame_demo.service.SettingService;
import com.tmsps.frame_demo.util.DateTools;
import com.tmsps.frame_demo.util.file.FileTools;
import com.tmsps.frame_demo.util.json.JsonTools;
import com.tmsps.frame_demo.util.ocr.XunLongOCR;
import com.tmsps.frame_demo.util.oos.HelloOSS;
import com.tmsps.frame_demo.util.wx.QywxConfigTools;
import com.tmsps.frame_demo.util.wx.WxConfigTools;

import sun.misc.BASE64Decoder;

@Controller
@Scope("prototype")
public class UploadController extends ProjBaseAction {
	
	static String bucketName = "tykd-face";

	@Autowired
	public SettingService settingService;

	static WxConfig qywxConfig = QywxConfigTools.getWxConfig();
	static WxConfig wxConfig = WxConfigTools.getWxConfig();

	@RequestMapping("/upload_form")
	public ModelAndView list(String area_parent_id) {
		ModelAndView mv = new ModelAndView("/jsp_cp/file/list");
		return mv;
	}

	@RequestMapping("/upload")
	public void upload(@RequestParam MultipartFile file) throws IOException {
		resp.setCharacterEncoding("utf-8");
		String suffix = FileTools.getSuffix(file.getOriginalFilename());
		String filename = System.currentTimeMillis() + "." + suffix;

		int year = DateTools.getYear();
		int month = DateTools.getMonth();
		String folder = year + File.separator + month;
		String folder_url = year + "/" + month;

		// 保存文件
		t_fk_file tf = new t_fk_file();
		tf.setFile_name(file.getOriginalFilename());
		tf.setNew_file_name(filename);
		tf.setFolder(folder);
		tf.setFolder_url(folder_url);
		tf.setContent_type(file.getContentType());
		tf.setSize(file.getSize());
		// 获取保存路径
		String DATA_PATH = settingService.getVal("DATA_PATH");
		
		System.out.println(DATA_PATH + folder + File.separator + tf.getNew_file_name());
		File newFile = new File(DATA_PATH + folder + File.separator + tf.getNew_file_name());
		if (!newFile.getParentFile().exists()) {
			newFile.getParentFile().mkdirs();
		}
		file.transferTo(newFile);
		bs.saveObj(tf);

		Map<String, String> end = new HashMap<String, String>();

		// 发送保存结果
		end.put("file_id", tf.getKid());
		end.put("file_name", tf.getFile_name());
		end.put("content_type", tf.getContent_type());

		resp.getWriter().print(JsonTools.toJson(end));
		resp.getWriter().flush();
	}
	
	@RequestMapping("/aliyun/upload")
	public void aliyun_upload(@RequestParam MultipartFile file,String filepath) throws Exception {
		resp.setCharacterEncoding("utf-8");
		String suffix = FileTools.getSuffix(file.getOriginalFilename());
		String filename = filepath+"/"+System.currentTimeMillis() + "." + suffix;
		HelloOSS.UploadFile(bucketName,filename, file.getBytes());
		String url = HelloOSS.getFileUrl(bucketName,filename);

		Map<String, String> end = new HashMap<String, String>();
		// 发送保存结果
		end.put("filename", filename);
		end.put("url", url);

		resp.getWriter().print(JsonTools.toJson(end));
		resp.getWriter().flush();
	}
	
	/**
	 * @param file
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping("/video_upload")
	public void video_upload(@RequestParam MultipartFile file)throws IOException {
		resp.setCharacterEncoding("utf-8");
		String suffix = FileTools.getSuffix(file.getOriginalFilename());
		String filename = System.currentTimeMillis() + "." + suffix;

		int year = DateTools.getYear();
		int month = DateTools.getMonth();
		String folder = year + File.separator + month;
		String folder_url = year + "/" + month;

		// 保存文件
		t_fk_file tf = new t_fk_file();
		tf.setFile_name(file.getOriginalFilename());
		tf.setNew_file_name(filename);
		tf.setFolder(folder);
		tf.setFolder_url(folder_url);
		tf.setContent_type(file.getContentType());
		tf.setSize(file.getSize());
		tf.setNote("视频");
		// 获取保存路径

		File newFile = new File("/usr/java/tomcat-8.5.8/webapps/ROOT/video" + File.separator + folder + File.separator + tf.getNew_file_name());
		if (!newFile.getParentFile().exists()) {
			newFile.getParentFile().mkdirs();
		}
		file.transferTo(newFile); 
		bs.saveObj(tf);

		Map<String, String> end = new HashMap<String, String>();

		// 发送保存结果
		end.put("file_id", tf.getKid());
		end.put("file_name", tf.getFile_name());
		end.put("content_type", tf.getContent_type());

		resp.getWriter().print(JsonTools.toJson(end));
		resp.getWriter().flush();
	}

	/**
	 * 字符识别
	 * @param file
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping("/wx_orc")
	@ResponseBody
	public String wx_orc(@RequestParam MultipartFile file) throws IOException{
		resp.setCharacterEncoding("utf-8");
		String suffix = FileTools.getSuffix(file.getOriginalFilename());
		String filename = System.currentTimeMillis() + "." + suffix;

		int year = DateTools.getYear();
		int month = DateTools.getMonth();
		String folder = year + File.separator + month;
		String folder_url = year + "/" + month;

		// 保存文件
		t_fk_file tf = new t_fk_file();
		tf.setFile_name(file.getOriginalFilename());
		tf.setNew_file_name(filename);
		tf.setFolder(folder);
		tf.setFolder_url(folder_url);
		tf.setContent_type(file.getContentType());
		tf.setSize(file.getSize());
		// 获取保存路径
		String DATA_PATH = settingService.getVal("DATA_PATH");

		File newFile = new File(DATA_PATH + File.separator + folder + File.separator + tf.getNew_file_name());
		if (!newFile.getParentFile().exists()) {
			newFile.getParentFile().mkdirs();
		}
		file.transferTo(newFile);
		bs.saveObj(tf);
		
		System.out.println(DATA_PATH + File.separator + folder + File.separator + tf.getNew_file_name());
		byte[] bytes = XunLongOCR.getImageBinary(DATA_PATH + File.separator + folder + File.separator + tf.getNew_file_name());
		String result = XunLongOCR.xunlongOcr("http://xlai.sxxlkj.com:8787/ocr", bytes, "{'Content-type':'image/jpeg'}");
		System.out.println(result);
		
		Map<String, Object> end = JsonTools.jsonStrToJsonObject(result);
		return end.get("pred_batch").toString();
	}
	
	/**
	 * 图像识别
	 * @param file
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping("/wx_classify")
	@ResponseBody
	public String wx_classify(@RequestParam MultipartFile file) throws IOException{
		resp.setCharacterEncoding("utf-8");
		String suffix = FileTools.getSuffix(file.getOriginalFilename());
		String filename = System.currentTimeMillis() + "." + suffix;

		int year = DateTools.getYear();
		int month = DateTools.getMonth();
		String folder = year + File.separator + month;
		String folder_url = year + "/" + month;

		// 保存文件
		t_fk_file tf = new t_fk_file();
		tf.setFile_name(file.getOriginalFilename());
		tf.setNew_file_name(filename);
		tf.setFolder(folder);
		tf.setFolder_url(folder_url);
		tf.setContent_type(file.getContentType());
		tf.setSize(file.getSize());
		// 获取保存路径
		String DATA_PATH = settingService.getVal("DATA_PATH");

		File newFile = new File(DATA_PATH + File.separator + folder + File.separator + tf.getNew_file_name());
		if (!newFile.getParentFile().exists()) {
			newFile.getParentFile().mkdirs();
		}
		file.transferTo(newFile);
		bs.saveObj(tf);
		
		System.out.println(DATA_PATH + File.separator + folder + File.separator + tf.getNew_file_name());
		byte[] bytes = XunLongOCR.getImageBinary(DATA_PATH + File.separator + folder + File.separator + tf.getNew_file_name());
		String result = XunLongOCR.xunlongOcr("http://xlai.sxxlkj.com:8787/classify", bytes, "{'Content-type':'image/jpeg'}");
		System.out.println(result);
		
		Map<String, Object> end = JsonTools.jsonStrToJsonObject(result);
		return end.get("id")+","+end.get("name")+","+end.get("scor"); 
	}

	@RequestMapping("/wx_upload_base")
	@ResponseBody
	public void wx_upload_base(String imgBase64,String filepath) throws Exception  {
		resp.addHeader("Access-Control-Allow-Origin", "*");   //用于ajax post跨域（*，最好指定确定的http等协议+ip+端口号）
		resp.setCharacterEncoding("UTF-8");
		
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] bytes = decoder.decodeBuffer(imgBase64);
        
        String filename = filepath+ "/" +System.currentTimeMillis() + ".png";
        String url = null;
		try {
			HelloOSS.UploadFile(bucketName,filename, bytes);
			url = HelloOSS.getFileUrl(bucketName,filename);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, String> end = new HashMap<String, String>();
		// 发送保存结果
		end.put("filename", filename);
		end.put("url", url);

		resp.getWriter().print(JsonTools.toJson(end));
		resp.getWriter().flush();
	}
	
	@RequestMapping("/wx_upload")
	@ResponseBody
	public void wx_upload(String media_id,String filepath) throws IOException {
		String filename = filepath+ "/" +System.currentTimeMillis() + ".png";

		String access_token = HttpClient.httpGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+wxConfig.getAppid()+"&secret="+wxConfig.getSecret(), null);

		access_token = JSONObject.parseObject(access_token).getString("access_token");

		Map<String, String> end = new HashMap<String, String>();
		byte[] data = null;
		String url = null;
	    InputStream input = null;
	    try{
	    	URL furl = new URL("https://api.weixin.qq.com/cgi-bin/media/get?access_token="+access_token+"&media_id="+media_id);
	        HttpURLConnection httpUrl = (HttpURLConnection) furl.openConnection();
	        httpUrl.connect();
	        httpUrl.getInputStream();
	        input = httpUrl.getInputStream();
	  	    ByteArrayOutputStream output = new ByteArrayOutputStream();
		    byte[] buf = new byte[1024];
		    int numBytesRead = 0;
		    while ((numBytesRead = input.read(buf)) != -1) {
		          output.write(buf, 0, numBytesRead);
		    }
		    data = output.toByteArray();
		    output.close();
		    input.close();
			HelloOSS.UploadFile(bucketName,filename, data);
			url = HelloOSS.getFileUrl(bucketName,filename);

			end.put("filename", filename);
			end.put("url", url);
	    }catch (Exception e) {
	          e.printStackTrace();
	    }

		resp.getWriter().print(JsonTools.toJson(end));
		resp.getWriter().flush();
	}
	
	@RequestMapping("/qywx_upload")
	@ResponseBody
	public void qywx_upload(String media_id,String filepath) throws Exception {
		logger.info(media_id);
		String filename = filepath + "/"+System.currentTimeMillis() + ".jpg";

		String access_token = HttpClient.httpGet("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid="+qywxConfig.getAppid()+"&corpsecret="+qywxConfig.getSecret(), null);
		access_token = JSONObject.parseObject(access_token).getString("access_token");

		Map<String, String> end = new HashMap<String, String>();
		byte[] data = null;
		String url = null;
	    InputStream input = null;
	    try{
	    	URL furl = new URL("https://qyapi.weixin.qq.com/cgi-bin/media/get?access_token="+access_token+"&media_id="+media_id);
	        HttpURLConnection httpUrl = (HttpURLConnection) furl.openConnection();
	        httpUrl.connect();
	        httpUrl.getInputStream();
	        input = httpUrl.getInputStream();
	  	    ByteArrayOutputStream output = new ByteArrayOutputStream();
		    byte[] buf = new byte[1024];
		    int numBytesRead = 0;
		    while ((numBytesRead = input.read(buf)) != -1) {
		          output.write(buf, 0, numBytesRead);
		    }
		    data = output.toByteArray();
		    output.close();
		    input.close();
			HelloOSS.UploadFile(bucketName,filename, data);
			url = HelloOSS.getFileUrl(bucketName,filename);

			end.put("filename", filename);
			end.put("url", url);
	    }catch (Exception e) {
	          e.printStackTrace();
	    }

		resp.getWriter().print(JsonTools.toJson(end));
		resp.getWriter().flush();
	}
	
	public static Map<String, String> get_binary_file(String imgBase64,String filepath) throws IOException {
		BASE64Decoder decoder = new BASE64Decoder();
        byte[] bytes = decoder.decodeBuffer(imgBase64);

		String filename = filepath + "/"+System.currentTimeMillis() + ".jpg";
        
        String url = null;
		try {
			HelloOSS.UploadFile(bucketName,filename, bytes);
			url = HelloOSS.getFileUrl(bucketName,filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Map<String, String> end = new HashMap<String, String>();
		// 发送保存结果
		end.put("filename", filename);
		end.put("url", url);
        return end;
    }
	
	public static void main(String[] args) {
		String file = HttpClient.httpGet("https://qyapi.weixin.qq.com/cgi-bin/media/get?access_token=yyhaxa67ObOwGwJ-WP7FjAVQWKTcDg3_AG8wajZkPB_PNtyESvp8OzUJ0d8nTfu9G1iYzlwXOKqsNMD06v9LT15tpAzyUVo7CJCrSxUda_DNyhOu9e0bFmOp-GDG2FVmwjwXJ6-rG1Ly8jzQZlXCw5lb-0zfWayuzlA7SlOVFXxeiwqridypONBPm8zLG8C-3IjkA_Dz392dv-kr6FBf3A&media_id=1oRzQjC8XeKLyA4cD3nfNIaoK4VID2t4Vpzg2TKX52rc", null);
		System.out.println(JSONObject.parseObject(file).toJSONString());
		System.out.println(JSONObject.parseObject(file).toString());
	}
}