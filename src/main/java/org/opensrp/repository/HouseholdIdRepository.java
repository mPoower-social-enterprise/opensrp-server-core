package org.opensrp.repository;

import java.util.List;

import org.opensrp.domain.postgres.HouseholdId;

public interface HouseholdIdRepository {
	
	Integer getMaxHouseholdIdByLocation(Integer locationId);
	
	List<String> getSeriesOfHouseholdId(Integer maxId);
	
	int insertHouseholdId(HouseholdId householdId);
	
}
