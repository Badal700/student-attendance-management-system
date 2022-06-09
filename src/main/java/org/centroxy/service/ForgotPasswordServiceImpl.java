package org.centroxy.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.stream.Stream;

import org.centroxy.entities.Student;
import org.centroxy.repository.StudentRepository;
import org.centroxy.utils.EmailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForgotPasswordServiceImpl implements IForgotPasswordService {

	@Autowired
	private StudentRepository studentRepo;

	@Autowired
	private EmailUtils emailUtils;

	@Override
	public String forgotPassword(String email) {
		Student findByEmail = studentRepo.findByEmail(email);

		if (findByEmail == null) {
			return "No record found with given email";
		}

		boolean isSent = sendForgotPwdEmail(findByEmail);

		if (isSent) {
			return "Password sent to registered mail id";
		}
		return null;
	}

	public boolean sendForgotPwdEmail(Student student) {
		String to = student.getEmail();
		String subject = "Forgot Password";
		String bodyFileName = "FORGOT-PWD-EMAIL-BODY-TEMPLATE.txt";
		String body = readMailBody(bodyFileName, student);
		return emailUtils.sendEmail(subject, body, to);
	}

	public String readMailBody(String fileName, Student student) {
		String mailBody = null;
		StringBuffer buffer = new StringBuffer();
		Path path = Paths.get(fileName);
		try (Stream<String> stream = Files.lines(path)) {
			stream.forEach(line -> {
				buffer.append(line);
			});
			mailBody = buffer.toString();
			mailBody = mailBody.replace("{FIRSTNAME}", student.getUserName());
			mailBody = mailBody.replace("{PWD}", passwordDecoder(student.getPassword()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mailBody;
	}

	private String passwordDecoder(String password) {
		Decoder decoder = Base64.getDecoder();
		byte[] bytes = decoder.decode(password);
		String decodePassword = new String(bytes);
		return decodePassword;
	}

}
