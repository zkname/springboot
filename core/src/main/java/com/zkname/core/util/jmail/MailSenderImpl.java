package com.zkname.core.util.jmail;

import java.util.Properties;
import java.util.concurrent.Executors;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.zkname.core.util.ParamType;
import com.zkname.core.util.conf.MailProperties;


public class MailSenderImpl implements MailSender {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MailSenderImpl.class);
	
	private String host;
	
	private String username;
	
	private String password;
	
	private Properties props;
	
	private boolean debug;
	
	public MailSenderImpl() {
		this(false);
	}
	
	public MailSenderImpl(boolean debug) {
		this.debug = debug;
		this.props = new Properties();
		this.props.setProperty("mail.transport.protocol", "smtp");
		this.props.setProperty("mail.smtp.auth", "true");
		this.props.setProperty("mail.smtp.starttls.enable", "true");
	}
	
	@Override
	public MailSender protocol(String protocol) {
		Assert.notEmpty(new Object[]{protocol}, "protocol not is empty!");
		this.props.setProperty("mail.transport.protocol", protocol);  
		return this;
	}
	
	@Override
	public void send(MailMessage... mailMessages) throws MessagingException {
		Assert.notEmpty(new Object[]{mailMessages}, "mailMessages not is empty!");
		for(MailMessage mailMessage : mailMessages){
			send(mailMessage);
		}
	}
	
	@Override
	public void send(MailMessage mailMessage) throws MessagingException {
		
		Assert.notNull(new Object[]{mailMessage}, "mailMessage not is null!");
		
		Session session = Session.getInstance(props);
		
		// 启动JavaMail调试功能，可以返回与SMTP服务器交互的命令信息  
        // 可以从控制台中看一下服务器的响应信息  
        session.setDebug(debug);
        
		MimeMessage mimeMessage = new MimeMessage(session);
		MailMessageHelper.copy(mimeMessage, mailMessage);
		
		LOGGER.debug("send mail...");
		
		// 由 Session 对象获得 Transport 对象  
        Transport transport = session.getTransport();
        // 发送用户名、密码连接到指定的 smtp 服务器  
        transport.connect(host, username, password);  
 
        transport.sendMessage(mimeMessage, mimeMessage.getRecipients(Message.RecipientType.TO));  
        transport.close();
        
        LOGGER.debug("send success...");
        
	}
	
	@Override
	public void send(final MailMessage mailMessage, boolean isAsync) throws MessagingException {
		
		if(isAsync){
			Runnable task = new Runnable() {
				@Override
				public void run() {
					try {
						send(mailMessage);
					} catch (MessagingException e) {
						e.printStackTrace();
					}
				}
			};
			Executors.newSingleThreadExecutor().submit(task);
		} else {
			send(mailMessage);
		}
		
	}
	
	@Override
	public MailSender username(String username) {
		Assert.notEmpty(new Object[]{username}, "username not is empty!");
		this.username = username;
		return this;
	}

	@Override
	public MailSender password(String password) {
		Assert.notEmpty(new Object[]{password}, "password not is empty!");
		this.password = password;
		return this;
	}
	
	@Override
	public MailSender auth(boolean auth) {
		this.props.put("mail.smtp.auth", auth); 
		return this;
	}
	
	@Override
	public MailSender debug(boolean debug) {
		this.debug = debug;
		return this;
	}
	
	@Override
	public MailSender host(String host) {
		Assert.notEmpty(new Object[]{host}, "host not is empty!");
		this.host = host;
		return this;
	}
	
	@Override
	public MailSender port(int port) {
		this.props.put("mail.smtp.port", port);
		return this;
	}
	
	@Override
	public MailSender load() {
		String user = MailProperties.getInstance().getProperty("mail.user");
		String pass = MailProperties.getInstance().getProperty("mail.pass");
		String host = MailProperties.getInstance().getProperty("mail.smtp.host");
		Integer port = ParamType.getint(MailProperties.getInstance().getProperty("mail.smtp.port"));
		if(StringUtils.isNotBlank(user)){
			this.username(user);
		}
		if(StringUtils.isNotBlank(pass)){
			this.password(pass);
		}
		if(StringUtils.isNotBlank(host)){
			this.host(host);
		}
		if(null != port && port > 0){
			this.port(port);
		}
		return this;
	}
	
}
