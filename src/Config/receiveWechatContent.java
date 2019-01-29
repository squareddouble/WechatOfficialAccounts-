package Config;

import MessageDispose.*;

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

			String fromUserName = map.get("FromUserName");//粉丝号
			String toUserName = map.get("ToUserName");//公众号
			String msgType = map.get("MsgType");//发送的消息类型[比如 文字,图片,语音。。。]
			String content = map.get("Content");//发送的消息内容
			String message = null;		//输出信息
			String insertDatabaseMessage = null;		//未格式化的输出信息

			//判断发送的类型是文本
			if(MessageUtil.MESSAGE_TEXT.equals(msgType)) {

				if ("绑定-".equals(content)){				//如果信息字符串匹配，则执行语句块
					//要输出的信息
					insertDatabaseMessage = "收到信息";
					//封装信息
					message = MessageFormat.initText(toUserName, fromUserName, insertDatabaseMessage);
				}

				//返回信息给微信服务器
				out.print(message);

			}else if(MessageUtil.MESSAGE_EVENT.equals(msgType)) {	//验证是关注/取消事件
				String eventType = map.get("Event");	//获取是关注还是取消
				//关注
				if (MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)) {
					//要输出的信息
					insertDatabaseMessage = "欢迎关注";
					//封装信息
					message = MessageFormat.initText(toUserName, fromUserName, insertDatabaseMessage);
					//返回信息给微信服务器
					out.print(message);
				}else if (MessageUtil.MESSAGE_UNSUBSCRIBE.equals(eventType)){
					//取消关注
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {

		}
	}
}
