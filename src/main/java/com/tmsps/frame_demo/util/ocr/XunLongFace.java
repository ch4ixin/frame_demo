package com.tmsps.frame_demo.util.ocr;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
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


public class XunLongFace { 

  static Logger logger = LoggerFactory.getLogger(XunLongFace.class);
  
  public static void main(String[] args) throws Exception {
	  String contentType = "{'Content-type':'image/jpeg'}";//图片类型
//	  String url = "https://www.mengxsh.net/recognize"; //接口连接
	  String url = "http://120.78.168.139:8000/recognize"; //接口连接
	  byte[]  bytes = getImageBinary("C:\\Users\\Administrator\\Desktop\\chaixin.jpg");//转换二进制
	  System.out.println(xunlongOcr(url, bytes, contentType));
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
}
			