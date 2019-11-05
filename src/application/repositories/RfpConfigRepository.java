package application.repositories;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import application.domain.RfpConfig;

@Repository
public interface RfpConfigRepository extends JpaRepository<RfpConfig, Integer>{
	
	public List<RfpConfig> findByType(String type);
}
