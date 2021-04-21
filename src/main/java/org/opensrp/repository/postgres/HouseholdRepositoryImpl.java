package org.opensrp.repository.postgres;

import java.util.List;

import org.opensrp.domain.postgres.HouseholdId;
import org.opensrp.repository.HouseholdIdRepository;
import org.opensrp.repository.postgres.mapper.custom.CustomHouseholdIdMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HouseholdRepositoryImpl implements HouseholdIdRepository {
	
	@Autowired
	private CustomHouseholdIdMapper customHouseholdIdMapper;
	
	@Override
	public Integer getMaxHouseholdIdByLocation(Integer locationId) {
		
		return customHouseholdIdMapper.selectMaxHouseholdIdByLocation(locationId);
	}
	
	@Override
	public List<String> getSeriesOfHouseholdId(Integer maxId) {
		return customHouseholdIdMapper.selectSeriesOfHouseholdId(maxId, maxId + 200);
	}
	
	@Override
	public int insertHouseholdId(HouseholdId householdId) {
		return customHouseholdIdMapper.insertHouseholdId(householdId);
	}
	
}
