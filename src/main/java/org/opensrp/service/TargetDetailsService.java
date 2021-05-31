package org.opensrp.service;

import java.util.List;

import org.opensrp.domain.postgres.TargetDetails;
import org.opensrp.repository.TargetDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TargetDetailsService {
	
	private TargetDetailsRepository targetDetailsRepository;
	
	@Autowired
	public TargetDetailsService(TargetDetailsRepository targetDetailsRepository) {
		this.targetDetailsRepository = targetDetailsRepository;
	}
	
	public List<TargetDetails> getTargetDetailsByUsername(String username, Long timestamp) {
		return targetDetailsRepository.getTargetDetailsByUsername(username, timestamp);
	}
}
