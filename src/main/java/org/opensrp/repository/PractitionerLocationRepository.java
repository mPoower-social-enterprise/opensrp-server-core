package org.opensrp.repository;

import java.util.List;

import org.opensrp.domain.postgres.MhealthPractitionerLocation;

public interface PractitionerLocationRepository {
	
	public List<MhealthPractitionerLocation> getDistrictAndUpazila(Integer districtLocationTagId);
	
	List<MhealthPractitionerLocation> getLocationByTagId(Integer tagId);
	
	List<MhealthPractitionerLocation> getLocationByParentId(Integer parentId);
}
