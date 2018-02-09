package nhatnghe.bean;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class XMailer {
	@Autowired
	JavaMailSender mailer;
	
	public void send(String to, String subject, String body){
		String from = "testjavaeenn@gmail.com";
		this.send(from, to, null, null, subject, body, null);
	}
	
	public void send(String from, String to, String subject, String body){
		this.send(from, to, null, null, subject, body, null);
	}
	
	public void send(String from, String to, String cc, String bcc, String subject, String body, String attachs){
		try {
			// Viáº¿t thÆ°
			MimeMessage message = mailer.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
			helper.setFrom(from, from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body);
			
			if(cc != null && cc.length() > 0){
				cc = cc.replaceAll("[ ;,]+", ",");
				helper.setCc(cc);
			}
			
			if(bcc != null && bcc.length() > 0){
				bcc = bcc.replaceAll("[ ;,]+", ",");
				helper.setBcc(bcc);
			}
			
			if(attachs != null && attachs.length() > 0){
				attachs = attachs.replaceAll("[;,]+", ",");
				String[] filenames = attachs.split(",");
				for(String filename : filenames){
					String name = filename.substring(filename.lastIndexOf("\\"));
					helper.addAttachment(name, new File(filename));	
				}
			}
			// Bá»� vÃ o bÆ°u Ä‘iá»‡n
			mailer.send(message);
		} 
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
