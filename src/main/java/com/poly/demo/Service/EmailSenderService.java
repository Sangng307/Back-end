package com.poly.demo.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.google.zxing.WriterException;
import com.poly.demo.Entity.Users;
import com.poly.demo.util.MessageHandler;
import com.poly.demo.util.QRCodeGenerator;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service

public class EmailSenderService {

	@Autowired
	private JavaMailSender mailSender;
	  @Autowired
	    private ApplicationContext context;
	@Autowired
	QRCodeGenerator qrCodeGenerator;
	@Autowired
	MessageHandler messageHandler;

	public void sendMailWithORcode(String toEmail, String subject, String body, @RequestBody Users users)
			throws WriterException, IOException {
		try {
			System.out.println("mailSender: " + mailSender);
			System.out.println("context: " + context);

			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			List<String> mess = messageHandler.createMessageList();

			helper.setFrom("congndps26169@fpt.edu.vn");
			helper.setTo(toEmail);
			helper.setSubject(subject);

			// Nội dung email với hình ảnh được nhúng
			String content = "<html><body><h1>Chào mừng bạn đến với ứng dụng của chúng tôi</h1>" + "<p>" + body + "</p>"
					+ "<img src='cid:image' /></body></html>"; // 'cid:image' là ID của hình ảnh

			helper.setText(content, true);

			// Nhúng QR vào email

			String data = mess.get(8) + users.getId();
			String imagePath = qrCodeGenerator.generateORcode(data);

			FileSystemResource file = new FileSystemResource(new File(imagePath));
			helper.addInline("image", file); // Thay imagePath bằng đường dẫn đến hình ảnh
			System.out.println(file);
			mailSender.send(message);

			System.out.println("Gửi email chứa hình ảnh thành công");
		} catch (MessagingException e) {
			System.out.println("Lỗi khi gửi email: " + e.getMessage());
		}

		System.out.println("Send Mail Success");
	}

	public void sendMail(String toEmail, String subject, String body)
			 {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			List<String> mess = messageHandler.createMessageList();

			helper.setFrom("congndps26169@fpt.edu.vn");
			helper.setTo(toEmail);
			helper.setSubject(subject);
			helper.setText(body);
			mailSender.send(message);

			System.out.println("Gửi email  thành công");
		} catch (MessagingException e) {
			System.out.println("Lỗi khi gửi email: " + e.getMessage());
		}

		System.out.println("Send Mail Success");
	}
	public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

}
