package org.centroxy.service;

import org.centroxy.entities.Student;
import org.centroxy.model.StudentDTO;
import org.centroxy.repository.StudentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements IStudentService {

	@Autowired
	private StudentRepository studentRepo;

	@Override
	public Student saveStudent(StudentDTO studentDto) {
		Student student = new Student();

		BeanUtils.copyProperties(studentDto, student);

		Student savedStudent = studentRepo.save(student);

		if (savedStudent.getStudentId() != null) {
			return savedStudent;
		}

		return null;
	}

}
