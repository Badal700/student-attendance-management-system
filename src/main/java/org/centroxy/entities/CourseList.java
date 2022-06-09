package org.centroxy.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class CourseList {

	@Id
	private String studentId;
	private String english;
	private String mathematics;
	private String physics;
	private String chemistry;
	private String biology;
	private String it;

}
