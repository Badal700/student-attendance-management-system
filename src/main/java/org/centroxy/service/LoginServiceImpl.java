package org.centroxy.service;

import java.util.Base64;
import java.util.Base64.Encoder;

import org.centroxy.entities.Student;
import org.centroxy.model.LoginRequest;
import org.centroxy.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements ILoginService {
	
	@Autowired
	private StudentRepository studentRepo;

	@Override
	public Student login(LoginRequest request) {
		Student student = studentRepo.findByUserNameAndPassword(request.getUserName(), encodePassword(request.getPassword()));
		
		if(student != null) {
			return student;
		}
		return null;
	}
	
	private String encodePassword(String password) {
		Encoder encoder = Base64.getEncoder();
		String encodeToString = encoder.encodeToString(password.getBytes());
		return encodeToString;
	}

}
