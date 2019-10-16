package com.tmsps.frame_demo.util.ocr;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class XunLongOCR {

  static Logger logger = LoggerFactory.getLogger(XunLongOCR.class);
  
  public static void main(String[] args) throws Exception {
	  
	  String contentType = "{'Content-type':'image/jpeg'}";//图片类型
	  
	  String url = "http://xlai.sxxlkj.com:8989/feature"; //接口连接  classify ocr recognize feature
	  
	  byte[]  bytes = image2byte("https://tykd-face.oss-cn-shenzhen.aliyuncs.com/allLogFace/1567592288674.jpg?Expires=1567595889&OSSAccessKeyId=CSxcv1tyCDiqNyvP&Signature=0ZH6U%2F36GAVCaKyVPEoFVGrZpAE%3D");//转换二进制
//	  byte[]  bytes = getImageBinary("C:\\Users\\Chailei\\Desktop\\352.jpg");
	  String str = xunlongOcr(url, bytes, contentType);
	  System.out.println(str);// 输出结果
//	  JSONObject object= JSONObject.parseObject(str);
//      str = object.get("result").toString().replace("[","").replace("]","").replace(",", " ");
//	  System.out.println(str);// 输出结果
  }
  
  /** 
   * 将图片转换成二进制 
   * @return 
   */  
  public static byte[] getImageBinary(String imgurl) {
      File f = new File(imgurl);  
      BufferedImage bi;  
      try {
          bi = ImageIO.read(f);  
          ByteArrayOutputStream baos = new ByteArrayOutputStream();  
          ImageIO.write(bi, "jpg", baos);  //经测试转换的图片是格式这里就什么格式，否则会失真  
          byte[] bytes = baos.toByteArray();  

          return bytes;  
      } catch (IOException e) {  
          e.printStackTrace();  
      }  
      return null;  
  } 

  /**
   * OCR接口方法
   * @param url 请求连接
   * @param bytes 图片二进制
   * @param contentType 图片类型
   * @return String 识别结果
   */
  public static String xunlongOcr(String url, byte[] bytes, String contentType) throws IOException {
	  HttpPost httpPost = new HttpPost(url);
      httpPost.setEntity(new ByteArrayEntity(bytes));
      String result = null;
      
      HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
      CloseableHttpClient m_HttpClient = httpClientBuilder.build();
      if (contentType != null)
          httpPost.setHeader("Content-type", contentType);
      CloseableHttpResponse httpResponse = m_HttpClient.execute(httpPost);
      
      try{
          HttpEntity httpEntity = httpResponse.getEntity();
          result = EntityUtils.toString(httpEntity, "utf-8");
          EntityUtils.consume(httpEntity);
      }finally{
          try{
              if(httpResponse!=null){
                  httpResponse.close();
              }
          }catch(IOException e){
              logger.info("## release resouce error ##" + e);
          }
      }
	return result;
  }
  
  /**
   * 图片转为byte数组
   *
   * @param path
   * @return
   */
  public static byte[] image2byte(String path) throws IOException {
      byte[] data = null;
      URL url = null;
      InputStream input = null;
      try{
          url = new URL(path);
          HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
          httpUrl.connect();
          httpUrl.getInputStream();
          input = httpUrl.getInputStream();
      }catch (Exception e) {
          e.printStackTrace();
          return null;
      }
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      byte[] buf = new byte[1024];
      int numBytesRead = 0;
      while ((numBytesRead = input.read(buf)) != -1) {
          output.write(buf, 0, numBytesRead);
      }
      data = output.toByteArray();
      output.close();
      input.close();
      return data;
  }
}
			