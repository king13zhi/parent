package com._163.king13zhi.p2p.base.service.impl;

import com._163.king13zhi.p2p.base.service.IVerifyCodeService;
import com._163.king13zhi.p2p.base.util.BidConst;
import com._163.king13zhi.p2p.base.util.DateUtil;
import com._163.king13zhi.p2p.base.util.UserContext;
import com._163.king13zhi.p2p.base.vo.VerifyCodeVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.UUID;

/**
 * Created by kingdan on 2018/1/21.
 */
@Service
@Transactional
public class VerifyCodeServiceImpl implements IVerifyCodeService {
	@Value("$msg.messageHost")
	private String messageHost;
	@Value("$msg.username")
	private String username;
	@Value("$msg.password")
	private String password;
	@Value("$msg.apiKey")
	private String apiKey;

	@Override
	public void sendVerifyCode(String phoneNumber) {
		//判断用户之前是否有发送短信验证码,上一次发送的事件和当前时间的间隔是否大于设定值
		VerifyCodeVo vo = UserContext.getVerifyCodeVo();
		if (vo == null || DateUtil.getBetweenTime(vo.getSendTime(), new Date()) > BidConst.MESSAGE_INTERVAL_TIME) {
			//1.生成一个验证码
			String verifyCode = UUID.randomUUID().toString().substring(0, 4); //前闭后开
			//2.拼接发送短信的内容
			StringBuilder msg = new StringBuilder(50);
			msg.append("这是你的验证码:").append(verifyCode).append(",有效时间为").append(BidConst.MESSAGE_VALID_TIME).append("分钟,请尽快进行验证");
			//3.执行短信的发送,模拟短信的发送
			try {
				sendRealMessage(phoneNumber, msg.toString());
				vo = new VerifyCodeVo();
				vo.setPhoneNumber(phoneNumber);
				vo.setVerifyCode(verifyCode);
				vo.setSendTime(new Date());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			//把对象放入到session中
			UserContext.setVerifyCodeVo(vo);
		} else {
			throw new RuntimeException("操作太频繁了,请稍后重试!");
		}
	}

	@Override
	public boolean validate(String phoneNumber, String verifyCode) {
		VerifyCodeVo verifyCodeVo = UserContext.getVerifyCodeVo();
		if (verifyCode != null //之前发送过验证码
				&& verifyCodeVo.getVerifyCode().equals(verifyCode)//验证码和之前产生的一致(不区分大小写)
				&& verifyCodeVo.getPhoneNumber().equals(phoneNumber) //输入的手机号码是 之前发送短信的手机号码
				&& DateUtil.getBetweenTime(verifyCodeVo.getSendTime(), new Date()) <= BidConst.MESSAGE_VALID_TIME * 60//短信在有效期内
				) {
			return true;
		}
		return false;
	}

	//模拟发送短信功能
	private void sendMessage(String phoneNumber, String content) throws Exception {
		//复制一个地址
		URL url = new URL(messageHost);
		//打开浏览器,在地址栏输入地址
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		//设置请求的方式
		conn.setRequestMethod("POST");
		//是否需要输出内容
		conn.setDoOutput(true);
		//给地址栏添加参数信息
		StringBuilder param = new StringBuilder(50);
		//info=今晚吃什么&loc=上海市&userid=222&appkey=xxx
		param.append("username=").append(username)
				.append("&password=").append(password)
				.append("&apiKey=").append(apiKey)
				.append("&phoneNumber=").append(phoneNumber)
				.append("&content=").append(content);
		//输入数据
		conn.getOutputStream().write(param.toString().getBytes("utf-8"));
		//按下回车键
		conn.connect();
		//获取到服务器响应的内容
		String responseStr = StreamUtils.copyToString(conn.getInputStream(), Charset.forName("utf-8"));
		if (!"success".equals(responseStr)) {
			throw new RuntimeException("短信发送异常,请联系管理员");
		}
	}

	//真实发送短信功能
	private void sendRealMessage(String phoneNumber, String content) throws Exception {
		//复制一个地址
		URL url = new URL("http://utf8.api.smschinese.cn/");
		//打开浏览器,在地址栏输入地址
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		//设置请求的方式
		conn.setRequestMethod("POST");
		//是否需要输出内容
		conn.setDoOutput(true);
		//给地址栏添加参数信息
		StringBuilder param = new StringBuilder(50);
		//info=今晚吃什么&loc=上海市&userid=222&appkey=xxx
		param.append("Uid=").append("kingwanglian")
				.append("&Key=").append("8316ab0567a1ecd379d4")
				.append("&smsMob=").append(phoneNumber)
				.append("&smsText=").append(content);
		//输入数据
		conn.getOutputStream().write(param.toString().getBytes("utf-8"));
		//按下回车键
		conn.connect();
		//获取到服务器响应的内容
		String responseStr = StreamUtils.copyToString(conn.getInputStream(), Charset.forName("utf-8"));
		int responseCode = Integer.parseInt(responseStr);

		if (responseCode < 0) {
			throw new RuntimeException("短信发送异常,请联系管理员");
		}
	}

}
































