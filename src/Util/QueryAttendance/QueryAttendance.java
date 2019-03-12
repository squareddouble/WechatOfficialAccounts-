package Util.QueryAttendance;

import Config.MessageConfig;
import Data.Database;
import Util.QueryGarde.NumberOfJudgingTerms;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/*
 *		Created by IntelliJ IDEA.
 *		User:龙猫
 *		Date: 2019/2/21
 *		Time: 17:19
 *       email: foxmaillucien@126.com
 *       Description:根据用户openid查询考勤
 */
public class QueryAttendance {
	public static String QueryAttendance(String fromUserName) {//根据用户openid查询考勤
		StringBuffer message = new StringBuffer();		//要返回的信息
		Database database = new Database();				//数据库对象
		String studentId = database.queryStudentId(fromUserName);		//向数据库查询学生id
		String gzcode = database.queryStudentGzcode(fromUserName);		//向数据库查询学生gzcode

		//如果找不到，则返回未绑定账号信息
		if (studentId == null || studentId.isEmpty() || gzcode == null || gzcode.isEmpty()){
			message.append(MessageConfig.UNLOGINMESSAGE);
			return new String(message);
		}

		try {
			//个人信息查询将要跳转的url
			String personalInformationDataUrl = "http://class.sise.com.cn:7001/SISEWeb/pub/studentstatus/attendance/studentAttendanceViewAction.do?method=doMain&" +
					"studentID=" + studentId +
					"&gzcode=" + gzcode;

			//new一个HTTPClient对象
			HttpClient httpClient = new HttpClient();
			//获取个人信息页面
			GetMethod getMethod = new GetMethod(personalInformationDataUrl);

			//发送执行
			httpClient.executeMethod(getMethod);
			//将服务器返回的个人信息页面html文本保存在字符串中
			String personalInformationHtml = getMethod.getResponseBodyAsString();
			//使用完后关闭连接
			getMethod.releaseConnection();

			//使用jsoup解析
			Document document = Jsoup.parse(personalInformationHtml);

			//获取成绩
			//寻找所有table标签
			Elements tables = document.getElementsByTag("table");

			//获取第几学期
			String time = document.getElementsByAttributeValue("selected", "selected").text();

			//第六个table
			Element table = tables.get(6);
			//遍历过滤元素，找到成绩table中所有tr标签
			Elements trs = table.getElementsByTag("tr");

			message.append("======考勤信息======\n");
			message.append(time + "：\n");

			//布尔变量，用于判断考勤是否有缺勤或迟到或请假记录
			Boolean isAddMessage = false;
			for (Element tr : trs) {
				//判断第3个td是否有缺勤或迟到或请假，!=-1则说明找到，即有缺勤或迟到或请假
				if (tr.child(2).text().indexOf("迟到") != -1 || tr.child(2).text().indexOf("旷课") != -1 || tr.child(2).text().indexOf("请假") != -1) {
					//分离点击请假次数的数字所跳转的URL
					String url = tr.child(2).child(0).attr("href");
					url = url.replace("../", "");
					url = "http://class.sise.com.cn:7001/" + url;

					//记录次数
					String DataTime = tr.child(2).text().substring(3,4);

					message.append("【" + tr.child(1).text() + "】：" + tr.child(2).text().replace(DataTime, "<a href = '" + url + "'>" + DataTime + "</a>") + "\n");
					isAddMessage = true;
				}
			}

			//如果没有有缺勤或迟到或请假记录，则添加返回信息
			if (!isAddMessage){
				message.append("恭喜您！您没有不良记录，请继续保持！\n");
			}


		}
		catch (Exception e) {
			e.printStackTrace();
			message.append("恭喜您！您没有不良记录，请继续保持！\n");
		}
		return new String(message);
	}
}
