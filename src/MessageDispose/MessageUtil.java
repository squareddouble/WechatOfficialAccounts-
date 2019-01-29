package MessageDispose;

/*
 *		Created by IntelliJ IDEA.
 *		User:龙猫
 *		Date: 2019/1/11
 *		Time: 14:57
 *       email: foxmaillucien@126.com
 *       Description:
 */
public class MessageUtil {
	/**
	 * 类型
	 */
	public static final String MESSAGE_TEXT = "text";//文本
	public static final String MESSAGE_NEWS = "news";
	public static final String MESSAGE_IMAGE = "image";
	public static final String MESSAGE_MUSIC = "music";
	public static final String MESSAGE_VOICE = "voice";
	public static final String MESSAGE_VIDEO = "video";
	public static final String MESSAGE_LINK = "link";
	public static final String MESSAGE_LOCATION = "location";
	public static final String MESSAGE_EVENT = "event";
	public static final String MESSAGE_SUBSCRIBE = "subscribe";		//关注
	public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";		//取关
	public static final String MESSAGE_CLICK = "CLICK";
	public static final String MESSAGE_VIEW = "VIEW";
	public static final String MESSAGE_SCANCODE = "scancode_push";



	public static String menuText() {
		StringBuffer sb = new StringBuffer();
		sb.append("欢迎您关注,请按照菜单提示进行操作:\n\n");
		sb.append("[1].显示短信验证码\n");
		sb.append("[2].显示个人信息\n");
		sb.append("回复 \"[0]\" 调出此菜单。");
		return sb.toString();
	}
}