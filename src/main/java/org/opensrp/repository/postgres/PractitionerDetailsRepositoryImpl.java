package org.opensrp.repository.postgres;

import java.util.List;

import org.opensrp.domain.PractitionerLocation;
import org.opensrp.repository.PractitionerDetailsRepository;
import org.opensrp.repository.postgres.mapper.custom.CustomPractitionerDetailsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PractitionerDetailsRepositoryImpl implements PractitionerDetailsRepository {
	
	@Autowired
	private CustomPractitionerDetailsMapper customPractitionerDetailsMapper;
	
	@Override
	public List<PractitionerLocation> findPractitionerLocationsByChildGroup(int practitionerId, int childGroupId) {
		
		return customPractitionerDetailsMapper.selectPractitionerLocationsByChildGroup(practitionerId, childGroupId);
	}
	
}
