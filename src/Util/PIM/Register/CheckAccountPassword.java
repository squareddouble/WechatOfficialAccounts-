package Util.PIM.Register;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
		//调用方法检查账号密码是否正确，正确则返回该学生的加密ID，不正确则返回null
		String studentID = CheckedUserUsernamePasswordAndClimbStudentID.CheckedUserUsernamePasswordAndClimbStudentID(StudentID, StudentPassword);
		//如果不正确，则重定向至错误页面
		if (studentID == null){
			response.sendRedirect("login_fail.jsp");
		}else {
		//如果正确，则将学号ID写入数据库，并且重定向至原页面（需要在过滤器过滤时获取请求头信息中的来源页面，并写入session中）或首页

			response.sendRedirect("");
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
}
