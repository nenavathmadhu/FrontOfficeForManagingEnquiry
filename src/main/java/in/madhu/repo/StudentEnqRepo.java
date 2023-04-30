package in.madhu.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.madhu.entity.StudentEnqEntity;


public interface StudentEnqRepo extends JpaRepository<StudentEnqEntity,Integer>{

}
