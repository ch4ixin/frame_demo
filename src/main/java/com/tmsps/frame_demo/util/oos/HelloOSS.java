package com.tmsps.frame_demo.util.oos;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.PutObjectResult;

public class HelloOSS {
	
	
    // endpoint是访问OSS的域名。如果您已经在OSS的控制台上 创建了Bucket，请在控制台上查看域名。
    // 如果您还没有创建Bucket，endpoint选择请参看文档中心的“开发人员指南 > 基本概念 > 访问域名”，
    // 链接地址是：https://help.aliyun.com/document_detail/oss/user_guide/oss_concept/endpoint.html?spm=5176.docoss/user_guide/endpoint_region
    // endpoint的格式形如“http://oss-cn-hangzhou.aliyuncs.com/”，注意http://后不带bucket名称，
    // 比如“http://bucket-name.oss-cn-hangzhou.aliyuncs.com”，是错误的endpoint，请去掉其中的“bucket-name”。
	public static String endpoint = "https://oss-cn-shenzhen.aliyuncs.com";

    // accessKeyId和accessKeySecret是OSS的访问密钥，您可以在控制台上创建和查看，
    // 创建和查看访问密钥的链接地址是：https://ak-console.aliyun.com/#/。
    // 注意：accessKeyId和accessKeySecret前后都没有空格，从控制台复制时请检查并去除多余的空格。
	public static String accessKeyId = "CSxcv1tyCDiqNyvP";
	public static String accessKeySecret = "AesnBQOjICambWFyFa9D33CCZGy9mh";

    // Bucket用来管理所存储Object的存储空间，详细描述请参看“开发人员指南 > 基本概念 > OSS基本概念介绍”。
    // Bucket命名规范如下：只能包括小写字母，数字和短横线（-），必须以小写字母或者数字开头，长度必须在3-63字节之间。
	public static String bucketName = "xl-face-pic";

    // Object是OSS存储数据的基本单元，称为OSS的对象，也被称为OSS的文件。详细描述请参看“开发人员指南 > 基本概念 > OSS基本概念介绍”。
    // Object命名规范如下：使用UTF-8编码，长度必须在1-1023字节之间，不能以“/”或者“\”字符开头。
	// private static String firstKey = "Tykd/chaixin.jpg";

    public static void main(String[] args) {

    	System.out.println("Started");
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

//        try {
    		String filePath = "C:\\Users\\Chailei\\Desktop\\tykd_face.txt";
    		List<String> atlist = readTxtFile(filePath);
    		for(int i=0; i<atlist.size(); i++){
//    			try {
    				boolean status = atlist.get(i).contains("/");  
    				String firstKey;
    				if(status) {
    					String[] list = atlist.get(i).split("/"); 
    					firstKey = list[1];
    					
    				}else {
    					firstKey = atlist.get(i);
    				}
    				System.out.println(i+": "+firstKey);
    				
//    				try {
//        				downloadFile("xl-face-pic", atlist.get(i), "C:\\Users\\Chailei\\Desktop\\imgs2\\"+firstKey);
//    				} catch (Exception e) {
//    					e.printStackTrace();
//        				downloadFile("tykd-xszc", atlist.get(i), "C:\\Users\\Chailei\\Desktop\\imgs2\\"+firstKey);
//    				}
    				
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
			}
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            ossClient.shutdown();
//        }
        System.out.println("Completed");
    }
    
    public static String UploadFile(String bucketName,String firstKey,byte[] bytes) throws Exception {
    	OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    	PutObjectResult res = null;
        try {
            InputStream is = new ByteArrayInputStream(bytes);
            res = ossClient.putObject(bucketName, firstKey, is);
//            System.out.println("Object：" + firstKey + "存入OSS成功。"+res.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }
        return res.toString();
    }

    public static String getFileUrl(String bucketName,String firstKey) throws Exception {
    	OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    	URL url = null;
    	try {
    		Date expiration = new Date(new Date().getTime() + 3600 * 1000);
    		// 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
    		url = ossClient.generatePresignedUrl(bucketName, firstKey, expiration);
    		// 设置URL过期时间为1小时。
    		System.out.println(url);
    	} catch (Exception e) {
    		e.printStackTrace();
		} finally {
			ossClient.shutdown();
		}
		return url.toString();
    }
    
    public static void downloadFile(String bucketName,String firstKey,String filepath) throws Exception {
    	OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    	try {
    	 	ossClient.getObject(new GetObjectRequest(bucketName, firstKey), new File(filepath));
    	} catch (Exception e) {
    		e.printStackTrace();
		} finally {
			ossClient.shutdown();
		}
    }
    
	public static List<String> readTxtFile(String filePath) {
		try {
			String encoding = "GBK";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				List<String> list = new ArrayList<>();
				while ((lineTxt = bufferedReader.readLine()) != null) {
					list.add(lineTxt);
				}
				read.close();
				return list;
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return null;

	}
}