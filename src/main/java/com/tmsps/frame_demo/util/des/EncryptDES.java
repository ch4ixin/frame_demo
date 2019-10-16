package com.tmsps.frame_demo.util.des;

import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.core.util.Base64Encoder;
import com.tmsps.frame_demo.util.http.HttpTools;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;

public class EncryptDES {
	
	public static String sendFaceData(String stdentId, String machineCode){
		String result = null;
		try {
			JSONObject StudentDES = new JSONObject();
			StudentDES.put("MachineCode", machineCode);
			StudentDES.put("StudentId", stdentId);
			String studentDES = encryptDES(StudentDES.toString(),"1234abcd");
//			System.out.println(studentDES);
			
			JSONObject FaceData = new JSONObject();
			FaceData.put("EncryptData", studentDES);
			FaceData.put("StudentId", stdentId);
			FaceData.put("MachineCode", machineCode);
			String urlString = URLEncoder.encode(FaceData.toString(), "utf-8");
			String SB = "http://yx.tyust.edu.cn/FaceRecognition.asmx/SaveFaceData?FaceData="+urlString;
			result = HttpTools.get(SB);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
    public static String encryptDES(String encryptString, String encryptKey){
        try {
            DESKeySpec dks = new DESKeySpec((encryptKey).getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new SecureRandom());
            byte[] encryptedData = cipher.doFinal(encryptString.getBytes(StandardCharsets.UTF_8));
            Base64Encoder encoder = new Base64Encoder();
            return encoder.encode(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

	public static void main(String[] args) {
		String stdentId = "19450331110086";
		String machineCode = "2018321";
		JSONObject json = JSONObject.parseObject(sendFaceData(stdentId,machineCode));
		System.out.println(json.getString("errcode"));
	}
}
