package org.opensrp.repository;

import java.util.List;

import org.opensrp.domain.PractitionerLocation;
import org.opensrp.domain.postgres.PractitionerDetails;

public interface PractitionerDetailsRepository {
	
	List<PractitionerLocation> findPractitionerLocationsByChildGroup(int practitionerId, int childGroupId,
	                                                                 int assignedLocationTagId);
	
	PractitionerDetails findPractitionerDetailsByUsername(String username);
	
	String getIsResync(String username);
	
	void updateAppVersion(String username, String version);
	
	Boolean getUserStatus(String username);
	
	List<Integer> getVillageIdsByUsername(String userId, int childRoleId, int locationTagId);
	
	Boolean checkUserMobileIMEI(String imeiNumber);
	
}
