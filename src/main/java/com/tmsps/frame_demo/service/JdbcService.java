package com.tmsps.frame_demo.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tmsps.ne4spring.orm.param.NeParamList;
import com.tmsps.frame_demo.base.service.BaseService;
import com.tmsps.frame_demo.util.jdbc.JdbcUtils;
import com.tmsps.frame_demo.web.SessionTools;

@Service
public class JdbcService extends BaseService {

	public List<Map<String, Object>> getProduceinfo() {
        JdbcUtils jdbcUtil = new JdbcUtils();  
        jdbcUtil.getConnection();   
        String sql = "select * from produceinfo";
        
        List<Map<String, Object>> result = null;
        try {
            result = jdbcUtil.findResult(sql, null);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.releaseConn();
        }
		return result;
	}
	
	public List<Map<String, Object>> getProducefood(String userid,String createtime) {
        JdbcUtils jdbcUtil = new JdbcUtils();  
        jdbcUtil.getConnection();   
        String sql = "select * from producefood where userid = '"+userid+"' and createtime > '"+createtime+"' ORDER BY createtime DESC";
        
        System.out.println(sql);
        List<Map<String, Object>> result = null;
        try {
            result = jdbcUtil.findResult(sql, null);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.releaseConn();
        }
		return result;
	}
	
	public List<Map<String, Object>> getSalefood(String userid,String createtime) {
        JdbcUtils jdbcUtil = new JdbcUtils();  
        jdbcUtil.getConnection();   
        String sql = "select * from salefood where userid = '"+userid+"' and createtime > '"+createtime+"' ORDER BY createtime DESC";
        
        System.out.println(sql);
        List<Map<String, Object>> result = null;
        try {
            result = jdbcUtil.findResult(sql, null);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.releaseConn();
        }
		return result;
	}
	
	public List<Map<String, Object>> selectShopProductionList() {
		String sql = "select t.* from t_shop_production t where t.status=0 and (t.shop_id = ?) ORDER BY register_time DESC";
		NeParamList param = NeParamList.makeParams();
		param.add(SessionTools.getCurrentShopId());
		List<Map<String, Object>> list = bs.findList(sql, param);
		return list;
	}
	
	public List<Map<String, Object>> selectShopSellList() {
		String sql = "select t.* from t_shop_sell t where t.status=0 and (t.shop_id = ?) ORDER BY created_bill_time DESC";
		NeParamList param = NeParamList.makeParams();
		param.add(SessionTools.getCurrentShopId());
		List<Map<String, Object>> list = bs.findList(sql, param);
		return list;
	}
}
