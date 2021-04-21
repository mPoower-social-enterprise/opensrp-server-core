package org.opensrp.repository;

import java.util.List;

import org.opensrp.domain.postgres.HouseholdId;

public interface HouseholdIdRepository {
	
	Integer getMaxHouseholdIdByLocation(Integer locationId);
	
	Integer getMaxGuestHouseholdIdByLocation(Integer locationId);
	
	List<String> getSeriesOfHouseholdId(Integer maxId);
	
	List<String> getSeriesOfGuestHouseholdId(Integer maxId);
	
	int insertHouseholdId(HouseholdId householdId);
	
	int insertGuestHouseholdId(HouseholdId householdId);
	
}
