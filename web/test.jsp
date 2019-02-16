<%--
  Created by IntelliJ IDEA.
  User: X
  Date: 2019/2/16
  Time: 19:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
index.jsp
<%@page import="java.io.*"%>
<%@page import="java.net.*" %>
<%@page import="org.json.*" %>

<%
    final String appId = "wx849d9ec6361711f8";
    final String appSecret = "43c48b279635a856ac869e6cf758ee6f";  //自己的APPIP  和APPSECRET

%>
<%
    class TestGetPost{
        public String getAccess_token(){  // 获得ACCESS_TOKEN

            String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ appId + "&secret=" +appSecret;

            String accessToken = null;
            try {
                URL urlGet = new URL(url);
                HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();

                http.setRequestMethod("GET");      //必须是get方式请求
                http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                http.setDoOutput(true);
                http.setDoInput(true);
                System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//连接超时30秒
                System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //读取超时30秒
                http.connect();

                InputStream is =http.getInputStream();
                int size =is.available();
                byte[] jsonBytes =new byte[size];
                is.read(jsonBytes);
                String message=new String(jsonBytes,"UTF-8");

                JSONObject demoJson = new JSONObject(message);
                accessToken = demoJson.getString("access_token");

                System.out.println(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return accessToken;
        }
        public int createMenu() throws IOException {
            String user_define_menu ={
                    "button": [
            {
                "name": "个人信息",
                    "sub_button": [
                {
                    "type": "click",
                        "name": "成绩查询",
                        "key": "Score_search"
                },
                {
                    "type": "click",
                        "name": "考勤查询",
                        "key": "Attendance_query"
                },
                {
                    "type": "view",
                        "name": "个人信息查询",
                        "url": "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx849d9ec6361711f8&redirect_uri=http%3A%2F%2Flonmao.iok.la%2FwechatAutoResponder%2FPIM%2Fmain.jsp&response_type=code&scope=snsapi_base&state=123#wechat_redirect"
                }
            ]
            }
    ]
}
            String access_token= getAccess_token();
            String action = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+access_token;
            try {
                URL url = new URL(action);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();

                http.setRequestMethod("POST");
                http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                http.setDoOutput(true);
                http.setDoInput(true);
                System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//连接超时30秒
                System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //读取超时30秒
                http.connect();

                OutputStream os= http.getOutputStream();
                os.write(user_define_menu.getBytes("UTF-8"));//传入参数
                os.flush();
                os.close();

                InputStream is =http.getInputStream();
                int size =is.available();
                byte[] jsonBytes =new byte[size];
                is.read(jsonBytes);
                String message=new String(jsonBytes,"UTF-8");
                System.out.println(message);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return 0;
        }
    }%>
<%
    TestGetPost tgp = new TestGetPost();

    tgp.createMenu();
%>
index.jsp