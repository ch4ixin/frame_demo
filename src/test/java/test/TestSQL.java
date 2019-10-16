package test;
import java.io.File;
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
import java.sql.Statement;

import com.tmsps.frame_demo.util.oos.HelloOSS;
  
public class TestSQL {  
	
    public static void main1(String[] args){
        String driver = "org.mariadb.jdbc.Driver";
        String url = "jdbc:mysql://xltest1.sxxlkj.com:3306/frame_demo?useUnicode=true&amp;characterEncoding=utf8";
        String username = "root";
        String password = "xlkjCS3826";
        Connection conn = null;  
        Statement stat = null;  
        ResultSet rs = null;  
        try{
            Class.forName(driver); 
            conn = DriverManager.getConnection(url,username,password); 
            stat = conn.createStatement();
//            String sql = "select t.code,t.name,t.face_img,t.kid from t_units_user t "
//            		+ "left outer join t_shop_units tsu on t.units_id = tsu.kid "
//            		+ "where t.status!=-100 and (t.face_img != '') " ;
            
          String sql = "select tuu.code,tuu.name,t.face_img,t.user_id from t_user_img t "
    		+ "left outer join t_units_user tuu on t.user_id = tuu.kid ";
            rs = stat.executeQuery(sql);
            
            while(rs.next()){
            	String code = rs.getString(1);
            	String name = rs.getString(2);
            	String firstKey = rs.getString(3);

				String[] list = firstKey.split("/");
            	System.out.println(code+","+name+","+firstKey);
            	try {
                    File file = new File("/Users/chaixin/Downloads/imgs/"+code+"_"+name+"\\"+list[1]);
                    File file_dir = new File("/Users/chaixin/Downloads/imgs/"+code+"_"+name);
                    judeDirExists(file,file_dir,firstKey,list[1]);
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        } catch(ClassNotFoundException e){  
            e.printStackTrace();  
        } catch(SQLException e){  
            e.printStackTrace();  
        } finally {  
            if(rs != null){  
                try{  
                    rs.close();  
                } catch(SQLException e){  
                    e.printStackTrace();  
                } finally {  
                    if(stat != null){  
                        try{  
                            stat.close();
                        } catch(SQLException e){
                            e.printStackTrace();  
                        } finally {
                            if(conn != null){}  
                                try{  
                                    conn.close();  
                                } catch(SQLException e){  
                                    e.printStackTrace();  
                                }  
                        }  
                    }   
                }  
            }  
        }  
    }
    
    // 判断文件夹是否存在
     public static void judeDirExists(File file,File file_dir,String firstKey,String name) throws Exception {
         if (file.exists()) {
             if (file.isDirectory()) {
                 System.out.println("目录存在");
             } else {
                 System.out.println("存在同名文件，无法创建目录");
             }
         } else {
        	 file_dir.mkdir();
             System.out.println("目录不存在，创建…");
             HelloOSS.downloadFile("tykd-face", firstKey, file_dir.getPath()+"/"+name);
         }
     }

}