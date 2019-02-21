package Util.PIM.Register;

import Data.Database;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/*
 *		Created by IntelliJ IDEA.
 *		User:龙猫
 *		Date: 2019/2/15
 *		Time: 13:58
 *       email: foxmaillucien@126.com
 *       Description:检查login.jsp页面传输过来的账号密码是否正确
 */
public class CheckAccountPassword extends HttpServlet {
	//login.jsp传输过来的为post请求
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//接收login.jsp传输过来的账号密码
		String StudentID = request.getParameter("StudentID");
		String StudentPassword= request.getParameter("StudentPassword");
		//获取openid
		String openid = (String)request.getSession().getAttribute("openid");
		//数据库类
		Database database = new Database();

		//信息插入log表
		String data = database.insertLog(openid, "用户登录！账号为：" + StudentID + "，密码为：" + StudentPassword);

		//调用方法检查账号密码是否正确，正确则返回该学生的加密ID，不正确则返回null
		Map<String, String> map = CheckedUserUsernamePasswordAndClimbStudentID.CheckedUserUsernamePasswordAndClimbStudentID(StudentID, StudentPassword);
		//获取studentID和gzcode
		String studentEncryptionID = map.get("studentID");
		String gzcode = map.get("gzcode");

		//如果不正确，则重定向至错误页面
		if (studentEncryptionID == null){
			//数据库插入登录错误信息
			database.updateLog(openid, data, "登录验证失败！账号或密码错误（或教务系统异常）！跳转至页面login_fail.jsp；");
			//跳转至登录页面
			response.sendRedirect("/wechatAutoResponder/PIM/Register/login_fail.jsp");
		}else {		//检查通过
		//如果正确，则将学号ID写入数据库，并且重定向至原页面（需要在过滤器过滤时获取请求头信息中的来源页面，并写入session中）或首页
			//获取用户openid
			String fromUserName = (String)request.getSession().getAttribute("openid");
			//将学生加密ID写入session，作为已登录凭证
			request.getSession().setAttribute("studentEncryptionID", studentEncryptionID);
			//向数据库插入信息
			//插入用户信息
			database.insertUser(StudentID,StudentPassword);
			database.insertUserMessage(fromUserName, StudentID, studentEncryptionID, gzcode);

			//获取session中的Referer，如果存在则该请求中的Referer为登录页面，不符合跳转回原页面要求
			String Referer = (String)request.getSession().getAttribute("Referer");
			//拆分字符串，将请求头的页面拆分出来
//			String splitPath[] = Referer.split("/");
//			String Request_Path = splitPath[splitPath.length - 1];
			//如果不为空，则Referer为需要跳转的页面
			if (Referer != null){

			}else {
				//否则跳转回主页
				Referer = "http://lonmao.iok.la/wechatAutoResponder/PIM/main.jsp";
			}
			//插入正确的操作信息和跳转信息
			database.updateLog(openid, data, "登录验证成功！账号密码正确！跳转至页面" + Referer);
			//跳转回原页面
			response.sendRedirect(Referer);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
}
