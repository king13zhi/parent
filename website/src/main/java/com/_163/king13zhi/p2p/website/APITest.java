package com._163.king13zhi.p2p.website;

import org.springframework.util.StreamUtils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by kingdan on 2018/1/21.
 */

/**
 * 基本都是看相关的api然后根据它们暴露的ip(应该是你在哪里的注册账号),将需要发送的邮件发送到它们,然后它们将信息进行发送.
 */

public class APITest {
	public static void main(String[] args) throws Exception {
		//复制一个地址
		URL url = new URL("https://way.jd.com/turing/turing");
		//打开浏览器,在地址栏输入地址
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		//设置请求的方式
		conn.setRequestMethod("POST");
		//是否需要输出内容
		conn.setDoOutput(true);
		//给地址栏添加参数信息
		StringBuilder param = new StringBuilder(50);
		//info=今晚吃什么&loc=上海市&userid=222&appkey=xxx
		param.append("info=").append("今晚吃什么").append("&loc=").append("广州天河区")
				.append("&userid=").append("222").append("&appkey=").append("e50b3303a2fa65774b440c0f084a82b9");
		//输入数据
		conn.getOutputStream().write(param.toString().getBytes("utf-8"));
		//按下回车键
		conn.connect();
		//获取到服务器响应的内容
		String responseStr = StreamUtils.copyToString(conn.getInputStream(), Charset.forName("utf-8"));
		System.out.println(responseStr);
	}
}





























