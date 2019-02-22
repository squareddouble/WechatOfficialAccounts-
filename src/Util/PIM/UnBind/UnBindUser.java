package Util.PIM.UnBind;

import Data.Database;

/*
 *		Created by IntelliJ IDEA.
 *		User:龙猫
 *		Date: 2019/2/22
 *		Time: 12:22
 *       email: foxmaillucien@126.com
 *       Description:
 */
public class UnBindUser {
	public static String UnBindUser(String openid){
		//数据库类
		Database database = new Database();
		String message = database.deleteUser(openid);
		//正常时候返回null，如若用户未绑定则返回提醒信息
		return message;
	}
}
