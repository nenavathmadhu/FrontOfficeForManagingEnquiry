package in.madhu.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.madhu.entity.EnqStatusEntity;

public interface EnqStatusRepo extends JpaRepository<EnqStatusEntity, Integer>{

	@Query("select distinct(EnqStatus) from EnqStatusEntity")
	List<String> getStatusNames();

	

}
 