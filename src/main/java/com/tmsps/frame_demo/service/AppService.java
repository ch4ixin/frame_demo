package com.tmsps.frame_demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tmsps.frame_demo.base.service.BaseService;



@Service
public class AppService extends BaseService{
	
	// 无参查询所有APP版本管理列表
	public List<Map<String, Object>> selectAppList() {
		String sql = "select * from t_fk_version t where t.status=0 ";
		List<Map<String, Object>> list = bs.findList(sql);
		return list;
	}
	
}
