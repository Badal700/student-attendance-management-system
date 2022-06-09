package org.centroxy.model;

import org.springframework.data.annotation.Transient;

import lombok.Data;

@Data
public class LoginRequest {

	private String userName;
	private String password;

	@Transient
	private String captcha;

	@Transient
	private String hiddenCaptcha;

	@Transient
	private String realCaptcha;

}
