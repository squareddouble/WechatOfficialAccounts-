package Util.QueryGarde;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 *		Created by IntelliJ IDEA.
 *		User:龙猫
 *		Date: 2019/1/24
 *		Time: 13:01
 *       email: foxmaillucien@126.com
 *       Description:根据时间判断当前第几学期，提供爬虫爬取数据
 */
public class NumberOfJudgingTerms {
	public static String JudgingTime(){
		//返回的学期信息
		StringBuffer message = new StringBuffer("");
		//获取时间
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");     //格式化
		String date = new String(simpleDateFormat.format(new Date()));      //取得当前时间戳
		String dateString[] = date.split("-");							//分隔字符串
		//转换类型,赋值
		int year,month;
		year = Integer.parseInt(dateString[0]);
		month = Integer.parseInt(dateString[1]);

		if (month < 3){				//小于三月份则为第一学期，第二学期未开学
			year--;
			message.append(year + "年第一学期");
		}else if (month < 9){		//三月份到九月份为第二学期
			year--;
			message.append(year + "年第二学期");
		}else{						//大于九月份，则为当前学年第一学期
			message.append(year + "年第一学期");
		}

		return message.toString();
	}
}
