package Util.QueryGarde;

/*
 *		Created by IntelliJ IDEA.
 *		User:龙猫
 *		Date: 2019/1/10
 *		Time: 12:01
 *       email: foxmaillucien@126.com
 *       Description:返回学生个人成绩信息
 */
import Config.MessageConfig;
import Data.Database;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class QueryGarde {

	public static String QueryPersonageGarde(String fromUserName) {		//根据用户ID查询成绩
		StringBuffer message = new StringBuffer();		//要返回的信息
		Database database = new Database();				//数据库对象
		String studentId = database.queryStudentId(fromUserName);		//向数据库查询学生id
		String schoolYear = NumberOfJudgingTerms.JudgingTime();			//获取当前学年

		//如果找不到，则返回未绑定账号信息
		if (studentId == null || studentId.isEmpty()){
			message.append(MessageConfig.UNLOGINMESSAGE);
			return new String(message);
		}

		try {
			//个人信息查询将要跳转的url
			String personalInformationDataUrl = "http://class.sise.com.cn:7001/SISEWeb/pub/course/courseViewAction.do?method=doMain&studentid=" + studentId;

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
			//第六个table
			Element table = tables.get(6);
			Elements trs = table.getElementsByTag("tr");
			//遍历过滤元素，找到成绩table中所有tr标签
			message.append("========必修课========\n");
			for (Element tr : trs) {
				if (tr.child(7).text().equals(schoolYear)) {        //判断第八个td是否和当前学年一样，例如“2018年第一学期”
					message.append("【" + tr.child(2).text() + "】：" + tr.child(8).text() + "\n");
				}
			}

			message.append("\n========选修课========\n");
			//第十个table标签为选修课程标签
			Element chooseClassTable = tables.get(9);
			//遍历获取tr
			Elements chooseClassTrs = chooseClassTable.child(1).children();
			for (Element chooseClassTr : chooseClassTrs){
				if (chooseClassTr.child(6).text().equals(schoolYear)) {				//判断第七个td是否和当前学年一样，例如“2018年第一学期”
					message.append("【" + chooseClassTr.child(1).text() + "】：" + chooseClassTr.child(7).text() + "\n");
				}
			}

			//第十一个table标签为页尾标签，获取平均学分绩点
			Element GPATalbe = tables.get(10);
			Elements GPATrs = GPATalbe.child(0).children();
			Elements GPATds = GPATrs.get(6).children();
			message.append("\n" + "myscse绩点：" + GPATds.get(3).child(0).child(0).text());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return new String(message);
	}
}