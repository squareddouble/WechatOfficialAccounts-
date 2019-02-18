package Util.UpdateMenu;
import java.io.*;
import java.net.*;

import org.json.*;
import Config.MessageConfig;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.BufferedReader;

public class UpdateMenu extends HttpServlet {

	@Override
	public void init() throws ServletException {
		try {
			super.init();
			System.out.println("================>[createMenu]Servlet自动加载启动开始.");
			//调用方法创建菜单
			createMenu();
			System.out.println("================>[createMenu]Servlet自动加载启动结束.");
		}catch (Exception e){
			e.printStackTrace();
		}

	}

	public String getAccess_token(){ // 获得ACCESS_TOKEN

        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&" +
				"appid=" + MessageConfig.appID +
				"&secret=" + MessageConfig.appsecret;

        String accessToken = null;
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();

            http.setRequestMethod("GET"); //必须是get方式请求
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
        String access_token= getAccess_token();
        String action = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+access_token;
        try {

			String path = this.getServletContext().getRealPath("/");
			String filePath = path + MessageConfig.CUSTOMIZE_MENUS;

			//读取json文件
			BufferedReader MenuJson_BufferReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"UTF-8"));
			StringBuffer user_define_menu = new StringBuffer();
			String str = null;
			//将Menu.json一行行写入user_define_menu
			while((str = MenuJson_BufferReader.readLine()) != null){
				user_define_menu.append(str);
			}

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
            os.write((new String(user_define_menu)).getBytes("UTF-8"));//传入参数
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
}


