package org.opensrp.repository.postgres.mapper.custom;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.opensrp.domain.PractitionerLocation;
import org.opensrp.repository.postgres.mapper.PractitionerDetailsMapper;

public interface CustomPractitionerDetailsMapper extends PractitionerDetailsMapper {
	
	List<PractitionerLocation> selectPractitionerLocationsByChildGroup(@Param("practitionerId") int practitionerId,
	                                                                   @Param("childGroupId") int childGroupId);
	
}
