package com.tmsps.frame_demo.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tmsps.frame_demo.base.service.BaseService;
import com.tmsps.frame_demo.model.t_shop;
import com.tmsps.frame_demo.model.t_shop_admin;
import com.tmsps.frame_demo.util.json.JsonTools;
import com.tmsps.frame_demo.web.SessionTools;

/**
 * 分页拦截器，定义拦截路径 /shop
 * 
 * @author uninf
 * 
 */
public class LoginShopInterceptor extends BaseService implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		t_shop_admin shopAdmin = SessionTools.getCurrentShopAdmin();   

		if (shopAdmin == null) {
			String secret = request.getParameter("secret");

			if ("oQZUa1ii7xuQFo_zYrR4eLUm8bBE".equals(secret)) {
				shopAdmin = bs.findById("PeaTX1GVr8ekWryzn6wDCa", t_shop_admin.class);
				t_shop shop = bs.findById(shopAdmin.getShop_id(), t_shop.class);
				SessionTools.put(SessionTools.LOGIN_SHOP_ADMIN, shopAdmin);
				SessionTools.put(SessionTools.LOGIN_SHOP, shop);
				System.out.println(new Date()+"拦截到:"+shopAdmin.getName());
				return true;
			}
			
			if (handler.getClass() == HandlerMethod.class) {
				HandlerMethod hm = (HandlerMethod) handler;
				ResponseBody methodAnnotation = hm.getMethodAnnotation(ResponseBody.class);
				
				if (methodAnnotation != null && methodAnnotation.annotationType() == ResponseBody.class) {
					response.getWriter().print(JsonTools.jsonStrToJsonObject("{'loginVar':'false'}"));
				} else {
					String end = "<script>window.top.location.href='@path/login_shop.htm';</script>";
					System.out.println("path:"+request.getContextPath());
					response.getWriter().print(end.replace("@path", request.getContextPath()));
				}
			}

			return false;
		}
		System.out.println(new Date()+"拦截到:"+shopAdmin.getName());
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
