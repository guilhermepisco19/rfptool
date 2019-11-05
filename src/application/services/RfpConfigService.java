package application.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;


import application.domain.RfpConfig;
import application.repositories.RfpConfigRepository;

@Service
public class RfpConfigService {

	@Autowired
	private RfpConfigRepository repo;
	
	public List<RfpConfig> getRfpConfigByType(String type) {
		
		List<RfpConfig> obj = repo.findByType(type);
		
		return obj;
		
	}
}
