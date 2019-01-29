package MessageDispose;

/*
 *		Created by IntelliJ IDEA.
 *		User:龙猫
 *		Date: 2019/1/11
 *		Time: 14:56
 *       email: foxmaillucien@126.com
 *       Description:
 */
public class TextMessage extends BaseMessage{

	private String Content;
	private String MsgId;

	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getMsgId() {
		return MsgId;
	}
	public void setMsgId(String msgId) {
		MsgId = msgId;
	}
}
