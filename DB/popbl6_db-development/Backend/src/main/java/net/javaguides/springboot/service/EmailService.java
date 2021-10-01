package net.javaguides.springboot.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailService {
	@Autowired
	private JavaMailSender sender;

	/**
	 * Send email with the faq content
	 * 
	 * @param body
	 * @param mail
	 */
	public void sendMail(String body, String mail) {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		try {
			helper.setTo("cantinamu@gmail.com");
			helper.setText(body);
			helper.setSubject("Question from " + mail);
		} catch (MessagingException e) {
		}
		sender.send(message);
	}
}
