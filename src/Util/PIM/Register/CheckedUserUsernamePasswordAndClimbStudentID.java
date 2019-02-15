package Util.PIM.Register;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/*
 *		Created by IntelliJ IDEA.
 *		User:龙猫
 *		Date: 2019/1/11
 *		Time: 21:50
 *       email: foxmaillucien@126.com
 *       Description:验证用户账号密码是否正确，若正确则爬取学生ID并返回，不正确则返回数值为null的studentID
 */
public class CheckedUserUsernamePasswordAndClimbStudentID {
	public static String CheckedUserUsernamePasswordAndClimbStudentID(String username, String password){
		//学生ID
		String studentID = null;
		// 登陆 Url
		String loginUrl = "http://class.sise.com.cn:7001/sise/login_check_login.jsp";
		// 需登陆后访问的 Url，爬取学生ID的URL
		String HomeUrl = "http://class.sise.com.cn:7001/sise/module/student_states/student_select_class/main.jsp";

		//获取请求头的随机参数
		String requestHeadRandomData[] = QueryDataToLogin.queryDataToLogin();

		//new一个HTTPClient对象
		HttpClient httpClient = new HttpClient();
		// 模拟登陆，按实际服务器端要求选用 Post 或 Get 请求方式
		PostMethod postMethod = new PostMethod(loginUrl);

		// 设置登陆时要求的信息，用户名和密码
		NameValuePair[] data = {
				new NameValuePair(requestHeadRandomData[0], requestHeadRandomData[1]),
				new NameValuePair("random", requestHeadRandomData[2]),
				new NameValuePair("token", requestHeadRandomData[3]),
				new NameValuePair("username", username),
				new NameValuePair("password", password)};
		postMethod.setRequestBody(data);
		try {
			// 设置 HttpClient 接收 Cookie,用与浏览器一样的策略
			httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
			int statusCode = httpClient.executeMethod(postMethod);

			// 获得登陆后的 Cookie
			Cookie[] cookies = httpClient.getState().getCookies();
			StringBuffer tmpcookies = new StringBuffer();
			for (Cookie c : cookies) {
				tmpcookies.append(c.toString() + ";");
//				System.out.println("cookies :"+c.toString());
			}

			// 进行登陆后的操作，跳转到信息页面
			//获取home页面
			GetMethod getMethodForHome = new GetMethod(HomeUrl);
			// 每次访问需授权的网址时需带上前面的 cookie 作为通行证
			// （httpClient客户端 会自动带上  如不是特殊要求一般不进行设置）
			getMethodForHome.setRequestHeader("cookie", tmpcookies.toString());
			// 你还可以通过 PostMethod/GetMethod 设置更多的请求后数据
			// 例如，referer 从哪里来的，UA 像搜索引擎都会表名自己是谁，无良搜索引擎除外
			postMethod.setRequestHeader("Referer", "http://class.sise.com.cn:7001/sise/index.jsp");
			postMethod.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0");
			//发送执行
			httpClient.executeMethod(getMethodForHome);
			//将服务器返回的个人信息页面html文本保存在字符串中
			String homeHtml = getMethodForHome.getResponseBodyAsString();

			//判断是否登录成功
			if (homeHtml.indexOf("/sise/login.jsp") != -1){			//!-1则找到，既是登陆失败,返回数值为null的studentID
				return studentID;
			}

			//使用完后关闭连接
			getMethodForHome.releaseConnection();

			//使用jsoup解析
			Document homeDocument = Jsoup.parse(homeHtml);
			//查找具有该属性的标签
			Elements personalInformationForHomeTagTr = homeDocument.getElementsByAttributeValue("title","个人信息查询");
			//获取该标签onclick后执行跳转操作的链接
			String personalInformationForHomeUrl = personalInformationForHomeTagTr.get(0).child(0).attr("onclick");
			//通过字符串切割过滤出参数
			studentID = personalInformationForHomeUrl.split("studentid=")[1];
			studentID = studentID.split("'")[0];

			System.out.println("studentid：" + studentID);

		}catch (Exception e){
			e.printStackTrace();
		}
		return studentID;
	}
}
