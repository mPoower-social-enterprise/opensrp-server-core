package org.opensrp.repository.postgres.mapper.custom;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.opensrp.domain.PractitionerLocation;
import org.opensrp.domain.postgres.PractitionerDetails;
import org.opensrp.repository.postgres.mapper.PractitionerDetailsMapper;

public interface CustomPractitionerDetailsMapper extends PractitionerDetailsMapper {
	
	List<PractitionerLocation> selectPractitionerLocationsByChildGroup(@Param("practitionerId") int practitionerId,
	                                                                   @Param("childGroupId") int childGroupId,
	                                                                   @Param("assignedLocationTagId") int assignedLocationTagId);
	
	PractitionerDetails selectPractitionerDetailsByUsername(@Param("username") String username);
}