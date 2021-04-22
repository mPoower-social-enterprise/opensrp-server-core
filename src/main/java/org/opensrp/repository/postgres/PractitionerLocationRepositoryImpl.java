package org.opensrp.repository.postgres;

import java.util.List;

import org.opensrp.domain.postgres.MhealthPractitionerLocation;
import org.opensrp.repository.PractitionerLocationRepository;
import org.opensrp.repository.postgres.mapper.custom.CustomPractitionerLocationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PractitionerLocationRepositoryImpl implements PractitionerLocationRepository {
	
	@Autowired
	private CustomPractitionerLocationMapper customPractitionerLocationMapper;
	
	@Override
	public List<MhealthPractitionerLocation> getDistrictAndUpazila(Integer parentLocationTag) {
		
		return customPractitionerLocationMapper.selectDistrictAndUpazila(parentLocationTag);
	}
	
	@Override
	public List<MhealthPractitionerLocation> getLocationByTagId(Integer tagId) {
		
		return customPractitionerLocationMapper.selectDistrictAndUpazila(tagId);
	}
	
	@Override
	public List<MhealthPractitionerLocation> getLocationByParentId(Integer parentId) {
		
		return customPractitionerLocationMapper.selectDistrictAndUpazila(parentId);
	}
	
}
