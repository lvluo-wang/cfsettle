/*
 * 源程序名称: MailUtils.java 
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: UPG业务服务平台(UBSP)
 * 模块名称：发送邮件工具类
 * 
 */

package com.upg.ucars.util;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.log4j.Logger;

public class MailUtils {
	private static Logger logger = Logger.getLogger(MailUtils.class);
	private static String charSet = "utf-8";
	
	
	public static enum MailPriorityLevel{
		
		HIGHEST(1),HIGH(2),NORMAL(3),LOW(4),LOWEST(5);
		
		private int level = 3;
		
		private MailPriorityLevel(int level){
			this.level = level;
		}
		
		
		public int getLevel(){
			return level;
		}
		
	};
	

	public static void setCharSet(String charSet) {
		MailUtils.charSet = charSet;
	}

	public static boolean send(String smtpServer, String from, String username,
			String password, String address, String subject, String content) {
		return send(smtpServer, from, username, password, address, subject,
				content, null, null);
	}

	public static boolean send(String smtpServer, String from, String username,
			String password, String address, String subject, String content,
			String[] attachs) {
		return send(smtpServer, from, username, password, address, subject,
				content, attachs, null);
	}
	
	public static boolean send(String smtpServer, String from, String username,
			String password, String address, String subject, String content,
			String[] attachs, String mimeType,MailPriorityLevel level) {
		if (mimeType == null) {
			mimeType = "text/plain; charset=" + charSet;
		} else {
			mimeType = mimeType + "; charset=" + charSet;
		}

		Properties props = new Properties();
		props.put("mail.smtp.host", smtpServer);
		props.put("mail.smtp.auth", "true");

		Session session = Session.getInstance(props, new MyAuthenricator(
				username, password));
		MimeMessage message = new MimeMessage(session);
		try {
			if (level != null) {
				message.addHeader("X-Priority", String.valueOf(level.getLevel()));
			}
			message.setSentDate(new Date());
			message.setFrom(new InternetAddress(from));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(
					address));
			message.setSubject(subject);

			if ((attachs != null) && (attachs.length != 0)) {
				MimeMultipart mimeMultipart = new MimeMultipart();
				MimeBodyPart mimeBodyPart = new MimeBodyPart();
				mimeBodyPart.setContent(content, mimeType);
				mimeMultipart.addBodyPart(mimeBodyPart);

				for (int i = 0; i < attachs.length; ++i) {
					MimeBodyPart attachMBP = new MimeBodyPart();
					String[] addressAndTitle = attachs[i].split(";");
					FileDataSource fds = new FileDataSource(addressAndTitle[0]);
					attachMBP.setDataHandler(new DataHandler(fds));
					attachMBP.setFileName(MimeUtility.encodeWord(
							addressAndTitle[1], charSet, null));
					mimeMultipart.addBodyPart(attachMBP);
				}
				message.setContent(mimeMultipart);
			} else {
				message.setContent(content, mimeType);
			}
			Transport.send(message);
		} catch (Exception e) {
			logger.error("Could not send mail", e);
			return false;
		}

		return true;
	}
	
	
	public static boolean send(String smtpServer, String from, String username,
			String password, String address, String subject, String content,
			String[] attachs, String mimeType) {
		return send(smtpServer, from, username, password, address, subject, content, attachs, mimeType, null);
	}

	static class MyAuthenricator extends Authenticator {
		String username = null;
		String password = "";

		public MyAuthenricator(String username, String password) {
			this.username = username;
			this.password = password;
		}

		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
		}
	}

	public static void main(String[] args) {
		System.out.println(send("mail.upg.cn", "ubsp@upg.cn", "ubsp",
				"123#abc", "chenzhongyi@upg.cn", "test", "欢迎您"));
	}
}
