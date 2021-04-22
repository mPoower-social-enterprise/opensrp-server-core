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
	
	// query will be changed
	String selectIsResync(String username);
	
	// query will be changed
	void updateAppVersion(@Param("username") String username, @Param("version") String version);
	
	// query will be changed
	Boolean selectUserStatus(@Param("username") String username);
	
	// query will be changed , used one query instead of two query
	List<Integer> selectVillageIdsByUserId(@Param("userId") String userId, @Param("childRoleId") int childRoleId,
	                                       @Param("locationTagId") int locationTagId);
	
	Boolean selectkUserMobileIMEI(@Param("imeiNumber") String imeiNumber);
	
}
