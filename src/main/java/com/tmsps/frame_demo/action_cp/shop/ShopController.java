package com.tmsps.frame_demo.action_cp.shop;

import com.tmsps.ne4spring.utils.MD5Util;
import com.tmsps.frame_demo.base.action.ProjBaseAction;
import com.tmsps.frame_demo.base.tip.Tip;
import com.tmsps.frame_demo.model.t_shop;
import com.tmsps.frame_demo.model.t_shop_admin;
import com.tmsps.frame_demo.model.t_shop_shengcanlicense;
import com.tmsps.frame_demo.model.t_shop_yingyelicense;
import com.tmsps.frame_demo.service.ShopService;
import com.tmsps.frame_demo.util.ChkTools;
import com.tmsps.frame_demo.util.json.JsonTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 企业管理
 */
@Controller
@Scope("prototype")
@RequestMapping("/cp/shop")
public class ShopController extends ProjBaseAction {

	@Autowired
	private ShopService shopService;

	@RequestMapping("/list_data")
	@ResponseBody
	public void list_data() {
		List<Map<String, Object>> list = shopService.selectShopList(srh, sort_params, page);
		result.put("list", list);
	}

    @RequestMapping("/add")
	@ResponseBody
	public void add(t_shop shop) {
		bs.saveObj(shop);
		
		t_shop_admin shopAdmin = new t_shop_admin();
		shopAdmin.setName(shop.getLink_man());
		shopAdmin.setShop_id(shop.getKid());
		shopAdmin.setMobile(shop.getLink_mobile());
		shopAdmin.setType("负责人");
		shopAdmin.setPwd(MD5Util.MD5("123456"));
		bs.saveObj(shopAdmin);
		
		shop.setAdmin_id(shopAdmin.getKid());
		bs.updateObj(shop);
		
		t_shop_yingyelicense shop_yingyelicense = new t_shop_yingyelicense();
		shop_yingyelicense.setCode(req.getParameter("ycode"));
		try {
			shop_yingyelicense.setStart_time(Timestamp.valueOf(req.getParameter("ystart_time")).getTime());
			shop_yingyelicense.setEnd_time(Timestamp.valueOf(req.getParameter("yend_time")).getTime());
		} catch (Exception e) {
			// TODO: handle exception
		}
		shop_yingyelicense.setImg_url(req.getParameter("yimg_url"));
		bs.saveObj(shop_yingyelicense);
		
		t_shop_shengcanlicense shop_shengcanlicense = new t_shop_shengcanlicense();
		shop_shengcanlicense.setCode(req.getParameter("scode"));
		try {
			shop_shengcanlicense.setStart_time(Timestamp.valueOf(req.getParameter("sstart_time")).getTime());
			shop_shengcanlicense.setEnd_time(Timestamp.valueOf(req.getParameter("send_time")).getTime());
		} catch (Exception e) {
			// TODO: handle exception
		}
		shop_shengcanlicense.setImg_url(req.getParameter("simg_url"));
		bs.saveObj(shop_shengcanlicense);
		
		shop.setYingye_id(shop_yingyelicense.getKid());
		shop.setShengcan_id(shop_shengcanlicense.getKid());
		bs.updateObj(shop);
		this.setTipMsg(true, "保存成功!", Tip.Type.success);
	}

	@RequestMapping("/edit_form")
	@ResponseBody
	public String edit_form(String kid) {
		t_shop shop = bs.findById(kid, t_shop.class);
		t_shop_yingyelicense shop_yingyelicense = bs.findById(shop.getYingye_id(), t_shop_yingyelicense.class);
		t_shop_shengcanlicense shop_shengcanlicense = bs.findById(shop.getShengcan_id(), t_shop_shengcanlicense.class);
		
		result.put("shop", shop);
		result.put("shop_yingyelicense", shop_yingyelicense);
		result.put("shop_shengcanlicense", shop_shengcanlicense);
		return JsonTools.toJson(result);
	}

	@RequestMapping("/edit")
	@ResponseBody
	public void edit(t_shop shop) {
		
		t_shop shopDb = bs.findById(shop.getKid(), t_shop.class);
		shopDb.setType(req.getParameter("type"));
		shopDb.setName(req.getParameter("name"));
		shopDb.setLink_man(req.getParameter("link_man"));
		shopDb.setLink_mobile(req.getParameter("link_mobile"));
		shopDb.setAddress(req.getParameter("address"));
		shopDb.setLogo_file_id(req.getParameter("logo_file_id"));
		shopDb.setShop_menu_id(req.getParameter("shop_menu_id"));
		bs.updateObj(shopDb);

		if(ChkTools.isNotNull(shopDb.getYingye_id())) {
			t_shop_yingyelicense shop_yingyelicenseDb = bs.findById(shopDb.getYingye_id(), t_shop_yingyelicense.class);
			shop_yingyelicenseDb.setCode(req.getParameter("ycode"));
			try {
				shop_yingyelicenseDb.setStart_time(Timestamp.valueOf(req.getParameter("ystart_time")).getTime());
				shop_yingyelicenseDb.setEnd_time(Timestamp.valueOf(req.getParameter("yend_time")).getTime());
			} catch (Exception e) {
				// TODO: handle exception
			}
			shop_yingyelicenseDb.setImg_url(req.getParameter("yimg_url"));
			bs.updateObj(shop_yingyelicenseDb);
		}else {
			t_shop_yingyelicense shop_yingyelicense = new t_shop_yingyelicense();
			shop_yingyelicense.setCode(req.getParameter("ycode"));
			try {
				shop_yingyelicense.setStart_time(Timestamp.valueOf(req.getParameter("ystart_time")).getTime());
				shop_yingyelicense.setEnd_time(Timestamp.valueOf(req.getParameter("yend_time")).getTime());
			} catch (Exception e) {
				// TODO: handle exception
			}
			shop_yingyelicense.setImg_url(req.getParameter("yimg_url"));
			bs.saveObj(shop_yingyelicense);
			shop.setYingye_id(shop_yingyelicense.getKid());
			bs.updateObj(shop);
		}
		
		if(ChkTools.isNotNull(shopDb.getShengcan_id())) {
			t_shop_shengcanlicense shop_shengcanlicenseDb = bs.findById(shopDb.getShengcan_id(), t_shop_shengcanlicense.class);
			shop_shengcanlicenseDb.setCode(req.getParameter("scode"));
			shop_shengcanlicenseDb.setStart_time(Timestamp.valueOf(req.getParameter("sstart_time")).getTime());
			shop_shengcanlicenseDb.setEnd_time(Timestamp.valueOf(req.getParameter("send_time")).getTime());
			shop_shengcanlicenseDb.setImg_url(req.getParameter("simg_url"));
			bs.updateObj(shop_shengcanlicenseDb);
		}else {
			t_shop_shengcanlicense shop_shengcanlicense = new t_shop_shengcanlicense();
			shop_shengcanlicense.setCode(req.getParameter("scode"));
			shop_shengcanlicense.setStart_time(Timestamp.valueOf(req.getParameter("sstart_time")).getTime());
			shop_shengcanlicense.setEnd_time(Timestamp.valueOf(req.getParameter("send_time")).getTime());
			shop_shengcanlicense.setImg_url(req.getParameter("simg_url"));
			bs.saveObj(shop_shengcanlicense);
			shop.setShengcan_id(shop_shengcanlicense.getKid());
			bs.updateObj(shop);
		}

		this.setTipMsg(true, "保存成功!", Tip.Type.success);
	}

	/**
	 * 删除列表记录
	 * 
	 * @param kid
	 */
	@RequestMapping("/del")
	@ResponseBody
	public void del(String kid) {
		t_shop shop = bs.findById(kid, t_shop.class);
		shop.setStatus(-100);
		bs.updateObj(shop);
		
		t_shop_admin admin = bs.findById(shop.getAdmin_id(), t_shop_admin.class);
		admin.setStatus(-100);
		bs.updateObj(admin);

		this.setTipMsg(true, "删除成功!", Tip.Type.success);
	}

}
