package org.centroxy.service;

import org.centroxy.entities.Student;
import org.centroxy.model.StudentDTO;

public interface IStudentService {

	public Student saveStudent(StudentDTO studentDto);

}
