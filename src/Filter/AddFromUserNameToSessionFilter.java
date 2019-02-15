package Filter;


import Util.SessionParameter.AcquireUserOpenID.AddFromUserNameToSession;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/*
 *		Created by IntelliJ IDEA.
 *		User:龙猫
 *		Date: 2019/2/15
 *		Time: 17:12
 *       email: foxmaillucien@126.com
 *       Description:该过滤器只对指定页面（在自定义菜单中使用snsapi_base方法的页面）进行过滤，获取用户openid并写入session中
 */
public class AddFromUserNameToSessionFilter implements Filter {
	private FilterConfig filterConfig;
	private String needWorkingURL;		//需要进行操作的URL

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		ServletContext servletContext = filterConfig.getServletContext();
		//获取XML配置参数
		needWorkingURL = servletContext.getInitParameter("needWorkingURL");
	}
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		//获取请求URL
		String servletPath = request.getServletPath();

		//判断URL是否存在XML配置参数中
		List<String> urls = Arrays.asList(needWorkingURL.split(","));
		if (urls.contains(servletPath)){
			//调用静态方法获取用户openid并写入session中；
			AddFromUserNameToSession.doGet(request, response);
		}

		//否则放行
		chain.doFilter(request, response);
	}
	public void destroy() {
	}
}
