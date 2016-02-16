package com.app.music.common.mail;

import java.util.Properties;

public class MailSenderInfo {
	// 发送邮件的服务器的IP和端口
	public String mailServerHost = "smtp.xxx.com";
	public String mailServerPort = "25";
	// 邮件发送者的地址
	public String fromAddress = "AAAA@xxx.com";
	// 邮件接收者的地址(多个用“,”隔开)
	public String toAddress = "BBBB@xxx.com";
	// 登陆邮件发送服务器的用户名和密码
	public String userName = "AAAA@xxx.com";
	public String password = "******";
	// 是否需要身份验证
	public boolean validate = true;
	// 邮件主题
	public String subject;
	// 邮件的文本内容
	public String content;
	// 邮件附件的文件名
	public String[] attachFileNames;
	
	/**
	 * 获得邮件会话属性
	 */
	public Properties getProperties() {
		Properties p = new Properties();
		p.put("mail.smtp.host", this.mailServerHost);
		p.put("mail.smtp.port", this.mailServerPort);
		p.put("mail.smtp.auth", validate ? "true" : "false");
		return p;
	}
	
}
