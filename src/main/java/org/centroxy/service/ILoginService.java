package org.centroxy.service;

import org.centroxy.entities.Student;
import org.centroxy.model.LoginRequest;

public interface ILoginService {
	
	public Student login(LoginRequest request);

}
