package Filter;

import Data.Database;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/*
 *		Created by IntelliJ IDEA.
 *		User:龙猫
 *		Date: 2019/2/16
 *		Time: 14:08
 *      email: foxmaillucien@126.com
 *      Description:检查是否已登录过滤器，检查方式为检查会话中是否存在studentEncryptionID变量；
 *      如若没有，将用户openid向数据库查询，如果存在则已验证，不存在则跳转登录页面
 */
public class CheckIsLoginedFilter implements Filter {
	private FilterConfig filterConfig;
	private String UnNeedCheckingIsLoginURL;		//不需要检测的URL
	private Database database = new Database();						//数据库类对象
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		ServletContext servletContext = filterConfig.getServletContext();
		//获取XML配置参数
		UnNeedCheckingIsLoginURL = servletContext.getInitParameter("UnNeedCheckingIsLoginURL");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		//类型转换
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

		String servletPath = httpServletRequest.getServletPath();		//获取请求URL
		//拆分字符串，将要跳转的页面拆分出来
		String splitPath[] = servletPath.split("/");
		String Path = splitPath[splitPath.length - 1];

		//1、判断URL是否需要检查
		List<String> urls = Arrays.asList(UnNeedCheckingIsLoginURL.split(","));
		if (urls.contains(Path)){
			filterChain.doFilter(httpServletRequest,httpServletResponse);
			return;
		}

		//2、判断用户是否登录
		//获取session中存储的学生用户加密ID以判断是否登录
		String studentEncryptionID = (String)httpServletRequest.getSession().getAttribute("studentEncryptionID");
		//如果studentEncryptionID为空则说明session未存储，为未登录状态
		if (studentEncryptionID == null){
			//获取session中存储的openid
			String openid = (String)httpServletRequest.getSession().getAttribute("openid");
			//向数据库查询是否有该记录，没有返回结果为null
			studentEncryptionID = database.queryStudentId(openid);
			if (studentEncryptionID == null){		//如果数据库中也不存在记录，则说明用户从未登录过或已注销，则跳转至登录页面
				httpServletResponse.sendRedirect("/wechatAutoResponder/PIM/Register/login.jsp");
				return;
			}else {		//如果数据库中有记录则说明已登录，将学生加密ID写入session，放行
				httpServletRequest.getSession().setAttribute("studentEncryptionID", studentEncryptionID);
				filterChain.doFilter(httpServletRequest,httpServletResponse);
				return;
			}
		}

		//3、若存在，则放行
		filterChain.doFilter(httpServletRequest,httpServletResponse);
	}

	@Override
	public void destroy() {

	}
}
