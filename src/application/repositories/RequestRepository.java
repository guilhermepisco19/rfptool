package application.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import application.domain.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer>{
}
