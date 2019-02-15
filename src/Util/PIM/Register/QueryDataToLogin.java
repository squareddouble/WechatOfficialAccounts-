package Util.PIM.Register;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/*
 *		Created by IntelliJ IDEA.
 *		User:龙猫
 *		Date: 2019/1/11
 *		Time: 20:30
 *       email: foxmaillucien@126.com
 *       Description:因为登陆验证时需要验证表单的隐藏域，而隐藏域参数又会改变，故需要爬下来
 */
public class QueryDataToLogin {
	public static String[] queryDataToLogin(){
		//登陆界面url
		String url = "http://class.sise.com.cn:7001/sise/login.jsp";
		//new一个HTTPClient对象
		HttpClient httpClient = new HttpClient();
		//要返回的字符串数组，字符数组按顺序分别是：加密后的隐藏域name,该name对应的value，随机数random，验证码token
		String data[] = new String[4];
		// 模拟登陆，按实际服务器端要求选用 Post 或 Get 请求方式
		GetMethod postMethod = new GetMethod(url);

		try {
			//发送执行
			httpClient.executeMethod(postMethod);
			//将服务器返回的个人信息页面html文本保存在字符串中
			String html = postMethod.getResponseBodyAsString();
			//使用完后关闭连接
			postMethod.releaseConnection();
			//使用jsoup解析
			Document homeDocument = Jsoup.parse(html);
			Elements forms = homeDocument.getElementsByAttributeValue("name", "form1");
			Elements inputs = forms.get(0).children();
			//要返回的字符串数组，字符数组按顺序分别是：加密后的隐藏域name,该name对应的value，随机数random，验证码token
			data[0] = inputs.eq(0).attr("name");
			data[1] = inputs.eq(0).attr("value");
			data[2] = inputs.eq(1).attr("value");
			data[3] = inputs.eq(2).attr("value");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
}
