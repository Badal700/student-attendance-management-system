package org.centroxy.repository;

import org.centroxy.entities.CourseList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourselistRepository extends JpaRepository<CourseList, String> {

}
