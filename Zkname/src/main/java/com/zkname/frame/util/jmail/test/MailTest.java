package com.zkname.frame.util.jmail.test;

import javax.mail.MessagingException;

import com.zkname.frame.util.jmail.MailMessage;
import com.zkname.frame.util.jmail.MailSender;
import com.zkname.frame.util.jmail.MailSenderImpl;


public class MailTest {

	public static void main(String[] args) {
		MailSender mailSender = new MailSenderImpl();
		
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
			mailMessage
			.subject("hi，您有一封注册邮件！！！")
			.from("111", "sales@yuewuxian.com")
			.content("<p>hello</p><a href='http://www.baidu.com'>world</a>")
			.addTo("zhangkai@foxmail.com");
			mailSender.load().send(mailMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
