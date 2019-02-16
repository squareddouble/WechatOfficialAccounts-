package Util.PIM.BindUser;

import Data.Database;
import Util.PIM.Register.CheckedUserUsernamePasswordAndClimbStudentID;

/*
 *		Created by IntelliJ IDEA.
 *		User:龙猫
 *		Date: 2019/1/11
 *		Time: 21:48
 *       email: foxmaillucien@126.com
 *       Description:绑定用户信息，并返回绑定成功或失败字符串
 */
public class BindUser {
	public static String bindUser(String fromUserName, String content){			//接收到用户发送绑定请求后将字符串参数直接传入再拆分
		//返回的字符串
		String message = null;
		//数据库对象
		Database database = new Database();
		//拆分字符串
		String userData[] = content.split("-");
		String username = userData[1];
		String password = userData[2];
		//从网页爬取ID
		String studentID = CheckedUserUsernamePasswordAndClimbStudentID.CheckedUserUsernamePasswordAndClimbStudentID(username,password);
		if (studentID == null){			//如果返回null则账号密码错误
			message = "用户名或密码错误，请重新绑定！";
		}else {
			//向数据库插入信息
			database.insertUser(username,password);
			database.insertUserMessage(fromUserName,username,studentID);
			//返回信息为绑定成功
			message = "恭喜你绑定成功！赶快发送“成绩”查询考试成绩吧！";
		}
		return message;
	}
}
