package com.tmsps.frame_demo.action_shop.frame;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tmsps.frame_demo.base.action.ProjBaseAction;
import com.tmsps.frame_demo.model.t_shop;
import com.tmsps.frame_demo.model.t_shop_admin;
import com.tmsps.frame_demo.model.t_shop_role;
import com.tmsps.frame_demo.model.t_shop_shengcanlicense;
import com.tmsps.frame_demo.model.t_shop_units;
import com.tmsps.frame_demo.model.t_shop_yingyelicense;
import com.tmsps.frame_demo.service.JdbcService;
import com.tmsps.frame_demo.service.ShopMenuService;
import com.tmsps.frame_demo.web.SessionTools;

@Controller("shopFrame")
@Scope("prototype")
@RequestMapping("/shop/frame")
public class FrameController extends ProjBaseAction {

	@Autowired
	private ShopMenuService menuService;
	@Autowired
	private JdbcService jdbcService;

	@RequestMapping("/frame")
	public ModelAndView frame() {
		t_shop shop = SessionTools.getCurrentShop();
		t_shop_admin admin = SessionTools.getCurrentShopAdmin();
		t_shop_units shop_units = bs.findById(admin.getUnits_id(), t_shop_units.class);
		List<Map<String, Object>> sublist;
		boolean chk_root;
		if (admin.getKid().equals(shop.getAdmin_id())) {
			chk_root = true;
			sublist = menuService.selectAllMenuList(shop.getShop_menu_id());
		} else {
			chk_root = false;
			t_shop_role role = bs.findById(admin.getRole_id(), t_shop_role.class);
			logger.info(role.getCodes());
			sublist = menuService.selectAllMenuList(shop.getShop_menu_id(),role.getCodes());
		}

		//logger.info(sublist.toString());
		ModelAndView mv = new ModelAndView("/jsp_shop/frame/frame");
		mv.addObject("sublist", sublist);
		mv.addObject("index", sublist.get(0));
		mv.addObject("admin", admin);
		mv.addObject("shop", shop);
		mv.addObject("shop_units", shop_units);
		mv.addObject("chk_root", chk_root);
		return mv;
	}
	
	@RequestMapping("/index_v1")
	public ModelAndView index() {
		t_shop shop = SessionTools.getCurrentShop();
		t_shop_shengcanlicense sclicense = bs.findById(shop.getShengcan_id(), t_shop_shengcanlicense.class);
		t_shop_yingyelicense yylicense = bs.findById(shop.getYingye_id(), t_shop_yingyelicense.class);
		ModelAndView mv = new ModelAndView("/jsp_shop/frame/index_v1");
		mv.addObject("sclicense", sclicense);
		mv.addObject("yylicense", yylicense);
		mv.addObject("shop", shop);
		return mv;
	}
	
	@RequestMapping("/password")
	public ModelAndView password() {
		t_shop shop = SessionTools.getCurrentShop();
		t_shop_admin admin = SessionTools.getCurrentShopAdmin();
		ModelAndView mv = new ModelAndView("/jsp_shop/frame/password");
		mv.addObject("admin", admin);
		mv.addObject("shop", shop);
		return mv;
	}
}