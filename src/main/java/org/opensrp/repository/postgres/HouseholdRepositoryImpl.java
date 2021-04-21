package org.opensrp.repository.postgres;

import java.util.List;

import org.opensrp.domain.postgres.HouseholdId;
import org.opensrp.repository.HouseholdIdRepository;
import org.opensrp.repository.postgres.mapper.custom.CustomHouseholdIdMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HouseholdRepositoryImpl implements HouseholdIdRepository {
	
	private Integer SERIES_LIMIT = 200;
	
	@Autowired
	private CustomHouseholdIdMapper customHouseholdIdMapper;
	
	@Override
	public Integer getMaxHouseholdIdByLocation(Integer locationId) {
		
		return customHouseholdIdMapper.selectMaxHouseholdIdByLocation(locationId);
	}
	
	@Override
	public List<String> getSeriesOfHouseholdId(Integer maxId) {
		return customHouseholdIdMapper.selectSeriesOfHouseholdId(maxId, maxId + SERIES_LIMIT);
	}
	
	@Override
	public int insertHouseholdId(HouseholdId householdId) {
		return customHouseholdIdMapper.insertHouseholdId(householdId);
	}
	
	@Override
	public Integer getMaxGuestHouseholdIdByLocation(Integer locationId) {
		
		return customHouseholdIdMapper.selectMaxGuestHouseholdIdByLocation(locationId);
	}
	
	@Override
	public List<String> getSeriesOfGuestHouseholdId(Integer maxId) {
		
		return customHouseholdIdMapper.selectSeriesOfGuestHouseholdId(maxId, maxId + SERIES_LIMIT);
	}
	
	@Override
	public int insertGuestHouseholdId(HouseholdId householdId) {
		
		return customHouseholdIdMapper.insertGuestHouseholdId(householdId);
	}
	
}
