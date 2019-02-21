package Config;

/*
 *		Created by IntelliJ IDEA1.
 *		User:龙猫
 *		Date: 2019/1/29
 *		Time: 21:03
 *       email: foxmaillucien@126.com
 *       Description:
 */
public interface MessageConfig {
	String token = "wechatReceiveTonken";//与配置界面的ToKen一致
//	appID和appsecret每个开发账号均不同，请修改为自己账号所对应的变量
	String appID = "wx849d9ec6361711f8";
	String appsecret = "43c48b279635a856ac869e6cf758ee6f";
	//服务器前缀，请改为自己的测试服务器
	String servletURL = "http://lonmao.iok.la/wechatAutoResponder";
	//自定义菜单json文件的文件名，路径为项目根目录下的Json目录，不能修改，否则会抛出（FileNotFoundException）异常
	String CUSTOMIZE_MENUS = "Json" + System.getProperty ("file.separator") + "Menu.json";

	//执行查询成绩和考勤操作时验证未绑定账号返回信息
	String UNLOGINMESSAGE = "你尚未绑定账号，请先绑定账号！<a href='https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx849d9ec6361711f8&redirect_uri=http://lonmao.iok.la/wechatAutoResponder/PIM/Register/login.jsp&response_type=code&scope=snsapi_base&state=123#wechat_redirect'>账号绑定</a>";
}
