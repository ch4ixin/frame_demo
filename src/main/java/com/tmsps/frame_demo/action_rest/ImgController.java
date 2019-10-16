package com.tmsps.frame_demo.action_rest;

import com.tmsps.frame_demo.base.action.ProjBaseAction;
import com.tmsps.frame_demo.model.t_fk_file;
import com.tmsps.frame_demo.service.SettingService;
import com.tmsps.frame_demo.util.ChkTools;
import com.tmsps.frame_demo.util.img.ImgTools;
import com.tmsps.frame_demo.util.json.JsonTools;
import com.tmsps.frame_demo.util.oos.HelloOSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Controller
@Scope("prototype")
public class ImgController extends ProjBaseAction {

	String bucketName = "tykd-face";

	@Autowired
	public SettingService settingService;

	@RequestMapping("/download/{file_id}")
	public void download(@PathVariable String file_id, String mw, String mh) throws Exception {

		t_fk_file file = bs.findById(file_id, t_fk_file.class);
		String DATA_PATH = settingService.getVal("DATA_PATH");
		ServletOutputStream os = resp.getOutputStream();
		FileInputStream fis = null;
		try {

			File img = null;
			if (file != null) {
				img = new File(DATA_PATH + file.getFolder(), file.getNew_file_name());
			}
			
			if (img == null || !img.exists()) {
				img = new File(req.getSession().getServletContext().getRealPath("") + File.separator + "resource"
						+ File.separator + "img", "file404.jpg");
			}

			// 设置页面不缓存
			resp.setHeader("Pragma", "No-cache");
			resp.setHeader("Cache-Control", "no-cache");
			resp.setDateHeader("Expires", 0);
			resp.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(file.getFile_name(), "UTF-8"));

			fis = new FileInputStream(img);
			byte[] b = new byte[10240];
			int len = -1;
			while ((len = fis.read(b)) != -1) {
				os.write(b, 0, len);
			}

		} catch (Exception e) {
			System.err.println("文件找不到:" + e);
		} finally {
			os.close();
			if (fis != null) {
				fis.close();
			}
		}
	}
	
	@RequestMapping("/aliyun/img")
	public void aliyun_upload(String filename) throws Exception {
		resp.setCharacterEncoding("utf-8");
        
		String url = HelloOSS.getFileUrl(bucketName,filename);
		Map<String, String> end = new HashMap<String, String>();
		// 发送保存结果
		end.put("url", url);
		resp.getWriter().print(JsonTools.toJson(end));
		resp.getWriter().flush();
	}
	

	@RequestMapping("/aliyun_img")
	@ResponseBody
    public String aliyun_img(String filename) throws Exception{
		String url = HelloOSS.getFileUrl(bucketName,filename);
		return url;
    }
	
	

	@RequestMapping("/img/{file_id}")
	public void downloadys(@PathVariable String file_id, String mw, String mh) throws Exception {
//		logger.info(""+file_id);

		t_fk_file file = bs.findById(file_id, t_fk_file.class);
		ServletOutputStream os = resp.getOutputStream();
		// 获取保存路径
		String DATA_PATH = settingService.getVal("DATA_PATH");
		File img = null;
		try {
			if (file != null) {
				img = new File(DATA_PATH  + file.getFolder() + File.separator + file.getNew_file_name());
			}
			
			if (img == null || !img.exists()) {
				img = new File(DATA_PATH  + file.getFolder_url() + File.separator + file.getNew_file_name());
			}
			
			if (img == null || !img.exists()) {
				img = new File(req.getSession().getServletContext().getRealPath("") + File.separator + "resource"
						+ File.separator + "img", "file404.jpg");
			}

			int height = ChkTools.getInteger(mh);
			int width = ChkTools.getInteger(mw);
			height = height < 0 ? 0 : height;
			width = width < 0 ? 0 : width;

			// 设置页面不缓存
			resp.setHeader("Pragma", "No-cache");
			resp.setHeader("Cache-Control", "no-cache");
			resp.setDateHeader("Expires", 0);

			ImgTools.thumbnail_w_h(img, width, height, os);

		} catch (Exception e) {
			System.err.println("图片找不到:" + e);
			
			img = new File(req.getSession().getServletContext().getRealPath("") + File.separator + "resource"
					+ File.separator + "img", "file404.jpg");
			
			int height = ChkTools.getInteger(mh);
			int width = ChkTools.getInteger(mw);
			height = height < 0 ? 0 : height;
			width = width < 0 ? 0 : width;
			
			// 设置页面不缓存
			resp.setHeader("Pragma", "No-cache");
			resp.setHeader("Cache-Control", "no-cache");
			resp.setDateHeader("Expires", 0);

			ImgTools.thumbnail_w_h(img, width, height, os);
		} finally {
			os.close();
		}
	}

	@RequestMapping("/download")
	public void download(String fileName) throws Exception {

		ServletOutputStream os = resp.getOutputStream();
		FileInputStream fis = null;
		try {
			String DATA_PATH = settingService.getVal("DATA_PATH");
			File file = new File(DATA_PATH + File.separator + fileName);

			// 设置页面不缓存
			resp.setHeader("Pragma", "No-cache");
			resp.setHeader("Cache-Control", "no-cache");
			resp.setDateHeader("Expires", 0);
			resp.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
			fis = new FileInputStream(file);
			byte[] b = new byte[10240];
			int len = -1;
			while ((len = fis.read(b)) != -1) {
				os.write(b, 0, len);
			}

		} catch (Exception e) {
			System.err.println("文件找不到:" + e);
		} finally {
			os.close();
			if (fis != null) {
				fis.close();
			}
		}
	}
	
	

}
