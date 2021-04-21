package org.opensrp.repository.postgres;

import java.util.List;

import org.opensrp.domain.postgres.TargetDetails;
import org.opensrp.repository.TargetDetailsRepository;
import org.opensrp.repository.postgres.mapper.custom.CustomTargetDetailsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TargetDetailsRepositoryImpl implements TargetDetailsRepository {
	
	@Autowired
	private CustomTargetDetailsMapper customTargetDetailsMapper;
	
	@Override
	public List<TargetDetails> getTargetDetailsByUsername(String username, Long timestamp) {
		return customTargetDetailsMapper.selectTargetDetailsByUsername(username, timestamp);
	}
	
}
