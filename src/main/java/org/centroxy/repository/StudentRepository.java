package org.centroxy.repository;

import org.centroxy.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
	
	public Student findByUserNameAndPassword(String userName,String password);
	
	public Student findByEmail(String email);

}
