package com.tmsps.frame_demo.interceptor;

import com.tmsps.frame_demo.base.action.ProjBaseAction;
import com.tmsps.frame_demo.util.ChkTools;
import com.tmsps.frame_demo.util.ext.ExtTools;
import com.tmsps.frame_demo.util.json.JsonTools;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Ext 相关拦截器
 * 
 * @author Administrator
 *
 */
public class ExtInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (handler.getClass() == HandlerMethod.class) {
			HandlerMethod hm = (HandlerMethod) handler;
			ProjBaseAction baseAction = (ProjBaseAction) hm.getBean();
			// 设置查询参数s
			ExtTools.setSrhParams(baseAction, request);

			// 排序
			ExtTools.setSortParams(baseAction, request);

			// 设置 分页 页码
			String pageNo = request.getParameter("page");
			String limitStr = request.getParameter("limit");
			ProjBaseAction.page.setPageNumber(ChkTools.getInteger(pageNo));

			int limit = ChkTools.getInteger(limitStr);
			if (limit == 0)  {
				limit = 20;
			}
			ProjBaseAction.page.setPageSize(limit);
			baseAction.result.put("page", ProjBaseAction.page);

		}
		return true;

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if (handler.getClass() == HandlerMethod.class) {
			HandlerMethod hm = (HandlerMethod) handler;
			ProjBaseAction baseAction = (ProjBaseAction) hm.getBean();

			ResponseBody methodAnnotation = hm.getMethodAnnotation(ResponseBody.class);

			if (methodAnnotation != null && methodAnnotation.annotationType() == ResponseBody.class) {
				if (hm.getMethod().getReturnType() == void.class) {
					response.getWriter().print(JsonTools.toJson(baseAction.result));
					response.getWriter().flush();
				}
			}

		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}
}
