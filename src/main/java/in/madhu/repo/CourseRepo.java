package in.madhu.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.madhu.entity.CourseEntity;

public interface CourseRepo  extends JpaRepository<CourseEntity, Integer>{
	
	@Query("select distinct(CourseName) from CourseEntity")
	List<String> getCourses();
}

