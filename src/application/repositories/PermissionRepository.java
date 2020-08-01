package application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import application.domain.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer>{

	public Permission findByValue(String columnName);
}
