package org.centroxy.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.centroxy.generators.IdGenerators;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Data;

@Entity
@Data
public class Student {

	@Id
	@Column(name = "student_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_id_generator")
	@GenericGenerator(name = "student_id_generator", strategy = "org.centroxy.generators.IdGenerators", parameters = {
			@Parameter(name = IdGenerators.INCREMENT_PARAM, value = "1"),
			@Parameter(name = IdGenerators.VALUE_PREFIX_PARAMETER, value = "STD"),
			@Parameter(name = IdGenerators.NUMBER_FORMAT_PARAMETER, value = "%05d") })
	private String studentId;
	
	@Size(min = 3, max = 20)
	private String firstName;
	
	@Size(min = 3, max = 20)
	private String lastName;
	
	@NotBlank
	@Email(message = "Please enter a valid Email")
	private String email;
	private long mobileNo;
	private long aadharNo;
	
	@Column(unique =true)
	private String userName;
	private String password;
	
	@Transient
	private String captcha;
	@Transient
	private String hiddenCaptcha;
	@Transient
	private String realCaptcha;
}
