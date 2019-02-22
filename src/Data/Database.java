package Data;

import Config.MessageConfig;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 *		Created by IntelliJ IDEA.
 *		User:龙猫
 *		Date: 2019/1/11
 *		Time: 20:53
 *       email: foxmaillucien@126.com
 *       Description:数据库类，提供向数据库操作的方法
 */
public class Database {
	private Connection connection = null;
	public Database() {			//打开链接
		try {
			InitialContext initialContext = new InitialContext();
			DataSource dataSource = (DataSource) initialContext.lookup("java:comp/env/jdbc/wechatdevelp");
			connection = dataSource.getConnection();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public String queryStudentId(String fromUserName){		//根据用户ID查询其对应的加密学生ID，不存在则返回null
		String studentId = null;
		try {
			//向数据库查询
			String sql = "select studentid from user_messages where fromUserName = ? and is_delete != 1";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1,fromUserName);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()){		//判断记录是否存在，存在则向上移动一行，将studentid赋值
				studentId = resultSet.getString(1);
			}else {
				return null;
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return studentId;
	}

	public String queryStudentGzcode(String fromUserName){		//根据用户ID查询其对应的学生gzcode，不存在则返回null
		String gzcode = null;
		try {
			//向数据库查询
			String sql = "select studentid,gzcode from user_messages where fromUserName = ? and is_delete != 1";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1,fromUserName);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()){		//判断记录是否存在，存在则向上移动一行，将studentid赋值
				gzcode = resultSet.getString(2);
			}else {
				return null;
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return gzcode;
	}

	public void insertUser(String username, String password){		//向users表插入验证正确的用户名和密码
		try {
			//插入前先查询是否存在该账号
			String sql_query = "select * from users where username = ?";
			PreparedStatement queryUsernameStatement = connection.prepareStatement(sql_query);
			queryUsernameStatement.setString(1,username);
			ResultSet queryUsernameResultSet = queryUsernameStatement.executeQuery();
			//如果存在，则更新密码
			if (queryUsernameResultSet.next()){
				String sql = "update users set password = ? where username = ?";
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1,password);
				preparedStatement.setString(2,username);
				preparedStatement.execute();
			}else {
				//如果不存在，则执行插入操作
				String sql_insert = "insert users values(?,?)";
				PreparedStatement preparedStatement = connection.prepareStatement(sql_insert);
				preparedStatement.setString(1,username);
				preparedStatement.setString(2,password);
				preparedStatement.executeUpdate();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public void insertUserMessage(String fromUserName, String username, String studentID, String gzcode){			//向user_messages表插入用户名，学生加密ID，公众号绑定ID
		try {
			//插入前先查询数据库中是否有记录
			String queryExistSql = "select * from user_messages where fromUserName = ?";
			PreparedStatement queryExistStatement = connection.prepareStatement(queryExistSql);
			queryExistStatement.setString(1,fromUserName);
			ResultSet resultSet = queryExistStatement.executeQuery();
			if (resultSet.next()){			//如果有，则更新绑定状态
				String sql = "update user_messages set username = ?,studentid = ?,gzcode = ?,is_delete = 0 where fromUserName = ?";
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, username);
				preparedStatement.setString(2, studentID);
				preparedStatement.setString(3, gzcode);
				preparedStatement.setString(4, fromUserName);
				preparedStatement.executeUpdate();
			}else {							//没有则执行插入
				String sql = "insert user_messages values(?,?,?,?,0)";
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, fromUserName);
				preparedStatement.setString(2, username);
				preparedStatement.setString(3, studentID);
				preparedStatement.setString(4, gzcode);
				preparedStatement.executeUpdate();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public void reverseIsDeleteState(String fromUserName){				//根据fromUserName扭转逻辑删除状态，既已删除->未删除，反之亦然
		try {
			//根据fromUserName向数据库查询删除状态
			String sql = "select is_delete from user_messages where fromUserName = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1,fromUserName);
			ResultSet resultSet = preparedStatement.executeQuery();
			//指针下移
			resultSet.next();

			int isDeleteState = resultSet.getInt(1);

			String updateSql = "update user_messages set is_delete = ? where fromUserName = ?";
			if (isDeleteState == 0){			//未删除状态，扭转状态
				isDeleteState = 1;
			}else {
				isDeleteState = 0;
			}
			//扭转状态后更新数据库；
			PreparedStatement preparedStatement1 = connection.prepareStatement(updateSql);
			preparedStatement1.setInt(1,isDeleteState);
			preparedStatement1.setString(2,fromUserName);
			preparedStatement1.executeUpdate();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	//向content_log表插入用户的操作信息日志，返回插入时间
	public String insertLog(String fromUserName, String receiveMessage){
		//获取时间
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");     //格式化
		StringBuffer date = new StringBuffer(simpleDateFormat.format(new Date()));      //取得当前时间戳
		try {
			//插入数据库
			String sql = "insert content_log(createTime,fromUserName,receive_message) values(?,?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1,new String(date));
			preparedStatement.setString(2,fromUserName);
			preparedStatement.setString(3,receiveMessage);
			preparedStatement.executeUpdate();
		}catch (Exception e){
			e.printStackTrace();
		}
		return new String(date);
	}

	//更新content_log，插入返回给公众号的信息，将接收到的信息和返回信息分开插入，以便出现BUG时可以查看触发情况以复现
	public void updateLog(String fromUserName, String data, String responseMessage){
		try {
			//插入数据库
			String sql = "update content_log set response_message = ? where fromUserName = ? and createTime = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, responseMessage);
			preparedStatement.setString(2, fromUserName);
			preparedStatement.setString(3, data);
			preparedStatement.executeUpdate();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public String deleteUser(String fromUserName){				//用户发送解绑请求时，逻辑删除用户信息
		//数据已处于逻辑删除状态或数据不存在时，返回提醒信息，正常时返回成功信息
		String message = "解绑成功！如需使用服务请再次绑定账号！<a href='https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx849d9ec6361711f8&redirect_uri=http://lonmao.iok.la/wechatAutoResponder/PIM/Register/login.jsp&response_type=code&scope=snsapi_base&state=123#wechat_redirect'>账号绑定</a>";
		try {
			//解绑请求时，逻辑删除user_messages表中信息，既当is_delete为0时是扭转is_delete状态
			String sql = "select is_delete from user_messages where fromUserName = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1,fromUserName);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()){			//如果存在该数据，则读取起逻辑删除状态
				int isDeleteState = resultSet.getInt(1);
				if (isDeleteState == 0){	//如果未删除，则执行逻辑删除操作
					reverseIsDeleteState(fromUserName);
				}else {						//如果已经是删除状态，则退出
					message = MessageConfig.UNLOGINMESSAGE;
				}
			}else {							//如果不在该数据，则退出并返回提醒
				message = MessageConfig.UNLOGINMESSAGE;
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return message;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public void close() {				//关闭连接
		try {
			connection.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
