package org.centroxy.controller;

import java.util.Base64;
import java.util.Base64.Encoder;

import org.centroxy.entities.CourseList;
import org.centroxy.entities.Student;
import org.centroxy.model.LoginRequest;
import org.centroxy.model.StudentDTO;
import org.centroxy.model.StudentRequest;
import org.centroxy.repository.CourselistRepository;
import org.centroxy.service.IForgotPasswordService;
import org.centroxy.service.ILoginService;
import org.centroxy.service.IStudentService;
import org.centroxy.utils.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.apiclub.captcha.Captcha;

@Controller
public class StudentController {

	@Autowired
	private IStudentService studentService;

	@Autowired
	private ILoginService loginService;

	@Autowired
	private IForgotPasswordService forgotPwdService;

	@Autowired
	private CourselistRepository courseListRepo;

	@GetMapping("/registration")
	public String loadForm(Model model) {
		model.addAttribute("student", new StudentDTO());
		return "registrationPage";
	}

	@PostMapping("/save")
	public ModelAndView saveStudent(@ModelAttribute StudentDTO studentDto) {
		String doHashing = encodePassword(studentDto.getPassword());
		studentDto.setPassword(doHashing);
		Student savedStudent = studentService.saveStudent(studentDto);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("successfullPage");
		modelAndView.addObject("in", savedStudent);
		return modelAndView;
	}

	private String encodePassword(String password) {
		Encoder encoder = Base64.getEncoder();
		String encodeToString = encoder.encodeToString(password.getBytes());
		return encodeToString;
	}

	@GetMapping("/login")
	public String loadLoginForm(Model model) {
		LoginRequest loginRequest = new LoginRequest();
		getCaptcha(loginRequest);
		model.addAttribute("loginRequest", loginRequest);
		return "loginPage";
	}

	@PostMapping("/dashboard")
	public ModelAndView loginAccount(@ModelAttribute LoginRequest request) {
		Student student = loginService.login(request);

		if (student == null) {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("invalidLogin");
			return modelAndView;
		}
		if (request.getCaptcha().equals(request.getHiddenCaptcha())) {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("dashboardPage");
			modelAndView.addObject("student", student);
			return modelAndView;
		} else {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("invalidCaptcha");
			return modelAndView;
		}
	}

	@GetMapping("/forgotpwd")
	public String loadForgotPwd(Model model) {
		model.addAttribute("pwdrequest", new StudentRequest());
		return "forgotpwd";
	}

	@PostMapping("/forgotpwdMsg")
	public ModelAndView forgotPwd(@ModelAttribute StudentRequest request, Model model) {
		String forgotPassword = forgotPwdService.forgotPassword(request.getEmail());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forgotPassword-data");
		modelAndView.addObject("pwd", forgotPassword);
		model.addAttribute("pwd", forgotPassword);
		return modelAndView;
	}

	private void getCaptcha(LoginRequest request) {
		Captcha captcha = CaptchaUtil.createCaptcha(240, 70);
		request.setHiddenCaptcha(captcha.getAnswer());
		request.setCaptcha(""); // value entered by the User
		request.setRealCaptcha(CaptchaUtil.encodeCaptcha(captcha));
	}

	@GetMapping("/studentcourse/{studentId}")
	public String submit(@PathVariable String studentId, Model model) {
		try {
			CourseList courseList = courseListRepo.findById(studentId).get();

			model.addAttribute("studentId", courseList.getStudentId());
			model.addAttribute("english", courseList.getEnglish());
			model.addAttribute("mathematics", courseList.getMathematics());
			model.addAttribute("physics", courseList.getPhysics());
			model.addAttribute("chemistry", courseList.getChemistry());
			model.addAttribute("biology", courseList.getBiology());
			model.addAttribute("IT", courseList.getIt());
		} catch (Exception e) {

		}
		return "studentCourse";
	}

	@PostMapping("/courses/{studentId}")
	public String addCourses(@PathVariable String studentId, @ModelAttribute CourseList courseList) {
		courseList.setStudentId(studentId);
		courseListRepo.save(courseList);

		return "redirect:/studentcourse/" + studentId;

	}

	@GetMapping("/")
	public String showHome() {
		return "Home";
	}

	@GetMapping("/contactus")
	public String contactus() {
		return "contactUs";
	}

	@GetMapping("/aboutus")
	public String aboutUs() {
		return "aboutUs";
	}
}
