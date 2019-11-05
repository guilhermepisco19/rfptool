package application.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import application.domain.Request;
import application.repositories.RequestRepository;
@Service
public class RequestService {

	@Autowired
	private RequestRepository repo;
	
	public void insertRequest(Request request) {
		repo.saveAll(Arrays.asList(request));
	}
	
	public List<Request> getRequests(){
		return repo.findAll();
	}
}
