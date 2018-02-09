package nhatnghe.bean;

import org.springframework.stereotype.Component;

@Component()
public class Mailer {
	public void send() {
		System.out.println("Gửi email !");
	}
}
