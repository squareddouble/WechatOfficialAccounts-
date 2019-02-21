package Config;

import Data.Database;
import MessageDispose.*;
import Util.QueryAttendance.QueryAttendance;
import Util.QueryGarde.QueryGarde;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/*
 *		Created by IntelliJ IDEA.
 *		User:龙猫
 *		Date: 2019/1/11
 *		Time: 13:10
 *       email: foxmaillucien@126.com
 *       Description:接收微信传过来的信息，并判断信息内容输出
 */
public class receiveWechatContent extends HttpServlet {
	//GET方法用于验证和接入接口；
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		//微信加密签名
		String signature = request.getParameter("signature");
		//时间戳
		String timestamp = request.getParameter("timestamp");
		//随机数
		String nonce = request.getParameter("nonce");
		//随机字符串
		String echostr = request.getParameter("echostr");
		//检查微信签名，判断是否微信发出
		if(CheckUtil.checkSignature(signature, timestamp, nonce)){
//			return echostr;
 			out.println(echostr);
		}

//		System.out.println(signature);
	}

	//POST方法用于传输信息
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//获取输出对象
			PrintWriter out = response.getWriter();
			//将传来的XML参数解析为Map类型
			Map<String, String> map = new MessageFormat().xmlToMap(request);
			//数据库对象
			Database database = new Database();

			String fromUserName = map.get("FromUserName");//发送方账号（一个OpenID）
			String toUserName = map.get("ToUserName");//开发者公众号
			String msgType = map.get("MsgType");//发送的消息类型[比如 文字,图片,语音。。。]
			String content = map.get("Content");//发送的消息内容
			String message = null;		//输出信息
			String insertDatabaseMessage = null;		//未格式化的输出信息

			if(MessageUtil.MESSAGE_TEXT.equals(msgType)) {			//获取消息类型，判断是否为text

				if ("绑定-".equals(content)){				//如果信息字符串匹配，则执行语句块
					//要输出的信息
					insertDatabaseMessage = "收到信息";
					//封装信息
					message = MessageFormat.initText(toUserName, fromUserName, insertDatabaseMessage);
				}

				//返回信息给微信服务器
				out.print(message);

			}else if(MessageUtil.MESSAGE_EVENT.equals(msgType)) {		//获取消息类型，判断是否为event

				String eventType = map.get("Event");	//获取事件类型

				if (MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)) {		//关注
					//要输出的信息
					insertDatabaseMessage = "欢迎关注，首次关注请绑定账号！<a href='https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx849d9ec6361711f8&redirect_uri=http://lonmao.iok.la/wechatAutoResponder/PIM/Register/login.jsp&response_type=code&scope=snsapi_base&state=123#wechat_redirect'>账号绑定</a>";
					//封装信息
					message = MessageFormat.initText(toUserName, fromUserName, insertDatabaseMessage);
					//返回信息给微信服务器
					out.print(message);
				}else if (MessageUtil.MESSAGE_UNSUBSCRIBE.equals(eventType)){		//取消关注


				}else if (MessageUtil.MESSAGE_CLICK.equals(eventType)) {			//点击类型为click的自定义菜单按钮

					String eventKey = map.get("EventKey");	//获取事件KEY值，与自定义菜单接口中KEY值对应

					if (MessageUtil.SCORE_SEARCH.equals(eventKey)){		//当点击“成绩查询”按钮时
						//接收到的信息，用于插入日志数据库
						String receiveMessage = "“成绩查询”按钮点击事件";
						//将接收到的信息和用户openid插入数据库，返回创建的时间
						String createTime = database.insertLog(fromUserName, receiveMessage);

						//执行请求操作
						insertDatabaseMessage = QueryGarde.QueryPersonageGarde(fromUserName);
						message = MessageFormat.initText(toUserName, fromUserName, insertDatabaseMessage);
						//返回信息给微信服务器
						out.print(message);

						//将返回的信息插入数据库
						database.updateLog(fromUserName, createTime, insertDatabaseMessage);


					}else if(MessageUtil.ATTENDANCE_QUERY.equals(eventKey)){		//当点击“考勤查询”按钮时
						//接收到的信息，用于插入日志数据库
						String receiveMessage = "“考勤查询”按钮点击事件";
						//将接收到的信息和用户openid插入数据库，返回创建的时间
						String createTime = database.insertLog(fromUserName, receiveMessage);

						//执行请求操作
						insertDatabaseMessage = QueryAttendance.QueryAttendance(fromUserName);
						message = MessageFormat.initText(toUserName, fromUserName, insertDatabaseMessage);
						//返回信息给微信服务器
						out.print(message);

						//将返回的信息插入数据库
						database.updateLog(fromUserName, createTime, insertDatabaseMessage);

					}
				}else if (MessageUtil.MESSAGE_VIEW.equals(eventType)){				//点击类型为view的自定义菜单按钮

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {

		}
	}
}
