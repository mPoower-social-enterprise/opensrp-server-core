package org.opensrp.repository.postgres.mapper.custom;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.opensrp.domain.postgres.MhealthPractitionerLocation;

public interface CustomPractitionerLocationMapper {
	
	List<MhealthPractitionerLocation> selectDistrictAndUpazila(@Param("districtLocationTagId") Integer districtLocationTagId);
	
	List<MhealthPractitionerLocation> selectLocationByTagId(Integer tagId);
	
	List<MhealthPractitionerLocation> selectLocationByParentId(Integer parentId);
	
	List<Integer> selectPractitionerVillageIds(String username);
}
