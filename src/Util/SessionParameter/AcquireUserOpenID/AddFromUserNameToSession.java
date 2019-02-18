package Util.SessionParameter.AcquireUserOpenID;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;

import Config.MessageConfig;
import net.sf.json.JSONObject;


/*
 *		Created by IntelliJ IDEA.
 *		User:龙猫
 *		Date: 2019/2/15
 *		Time: 15:49
 *       email: foxmaillucien@126.com
 *       Description:微信通过get方式回调到该页面以获取用户openid（snsapi_base方式）；
 */
public class AddFromUserNameToSession {
		public static void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			//静默授权
			String get_access_token_url = "https://api.weixin.qq.com/sns/oauth2/access_token?"
					+ "appid="
					+ MessageConfig.appID
					+ "&secret="
					+ MessageConfig.appsecret
					+ "&code=CODE&grant_type=authorization_code";
			//获取参数
			String code = request.getParameter("code");
			//如果code不为空则说明是微信服务器回调请求，执行方法写入openid
			if (code != null) {
				//更换get_access_token_url的code
				get_access_token_url = get_access_token_url.replace("CODE", code);
				//通过get方法访问
				String json = HttpsGetUtil.doHttpsGetJson(get_access_token_url);
				//解析json数据
				JSONObject jsonObject = JSONObject.fromObject(json);
				//获取openid
				String openid = jsonObject.getString("openid");
				//将openid写入session中
				request.getSession().setAttribute("openid", openid);

				System.out.println("openid = " + openid);
			}
		}
}
