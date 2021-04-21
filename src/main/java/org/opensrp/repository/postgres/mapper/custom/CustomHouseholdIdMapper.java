package org.opensrp.repository.postgres.mapper.custom;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.opensrp.domain.postgres.HouseholdId;

public interface CustomHouseholdIdMapper {
	
	Integer selectMaxHouseholdIdByLocation(@Param("locationId") Integer locationId);
	
	List<String> selectSeriesOfHouseholdId(@Param("maxId") Integer maxId, @Param("maxIdPlusOne") Integer maxIdPlusOne);
	
	int insertHouseholdId(HouseholdId householdId);
	
}
