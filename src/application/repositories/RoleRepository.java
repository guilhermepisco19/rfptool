package application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import application.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
}
