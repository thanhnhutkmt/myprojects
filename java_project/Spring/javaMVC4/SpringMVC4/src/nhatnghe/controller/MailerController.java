package nhatnghe.controller;

import java.io.File;

import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import nhatnghe.bean.MailInfo;
import nhatnghe.bean.XMailer;

@Controller
@RequestMapping("mailer")
public class MailerController {
	@Autowired
	JavaMailSender mailer;
	
	@Autowired
	XMailer xmailer;
	
	@Autowired
	ServletContext app;
	
	@RequestMapping("index")
	public String index(ModelMap model) {
		model.addAttribute("mail", new MailInfo());
		return "mailer";
	}	
	
	@RequestMapping(value="send")
	public String send(ModelMap model, MailInfo mail, @RequestParam("file") MultipartFile file) {
		model.addAttribute("mail", new MailInfo());
	
		try {
			// Viết thư
			MimeMessage message = mailer.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");	
			helper.setFrom(mail.getFrom(), mail.getFrom());
			helper.setTo(mail.getTo());
			helper.setSubject(mail.getSubject());
			helper.setText(mail.getBody());
			
			// Đính kèm file
			String filename = file.getOriginalFilename();
			String path = app.getRealPath("/images/"+filename);
			file.transferTo(new File(path));
			helper.addAttachment(filename, new File(path));
			
//			mailer.send(message);
			xmailer.send(mail.getFrom(), mail.getTo(), null, null, mail.getSubject(), mail.getBody(), path);
			model.addAttribute("thongbao", "Gửi thành công!");
		} catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("thongbao", "Gửi bị lỗi!");
		}			
		return "mailer";
	}
}
