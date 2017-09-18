package com.zkname.credit.card.util.email;

import javax.mail.MessagingException;

import com.zkname.core.util.jmail.MailMessage;
import com.zkname.core.util.jmail.MailSender;
import com.zkname.core.util.jmail.MailSenderImpl;


public class MailUtil {

	public static void sendResetPassword(String email,String url){
		try {
			MailSender mailSender = new MailSenderImpl();
			MailMessage mailMessage = new MailMessage();
			mailSender.getProps().setProperty("mail.smtp.ssl.enable", "true");
			mailMessage.subject("找回您的账户密码.").from("system", "system@zkname.com").addTo(email);
			
			
			String emailContent = "请勿回复本邮件.点击下面的链接<br/><a href=" + url
                    + " target='_BLANK'>点击我重置密码</a>"
                    + "<br/>tips:本邮件超过30分钟,链接将会失效，需要重新申请";
			mailMessage.content(emailContent);
			mailSender.load().send(mailMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		MailSender mailSender = new MailSenderImpl(true);
		MailMessage mailMessage = new MailMessage();
		
		/*try {
			mailMessage
			.subject("hi，您有一封注册邮件！！！")
			.from("jelly_8090@163.com")
			.content("<p>hello</p><a href='http://www.baidu.com'>world</a>")
			.addTo("921293209@qq.com")
			.addFile("/Users/Anne/Documents/3849072.jpg", "/Users/Anne/Documents/vps.md");
			
			mailSender.debug(true).host("smtp.163.com").username("jelly_8090@163.com").password("********");
			mailSender.send(mailMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
		}*/
		
		try {
			mailSender.getProps().setProperty("mail.smtp.ssl.enable", "true");
			mailMessage
			.subject("hi，您有一封注册邮件！！！")
			.from("system", "system@zkname.com")
			.content("<p>hello</p><a href='http://www.baidu.com'>world</a>")
			.addTo("zhangkai@foxmail.com");
			mailSender.load().send(mailMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
