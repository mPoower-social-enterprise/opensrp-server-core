package org.opensrp.repository;

import java.util.List;

import org.opensrp.domain.PractitionerLocation;
import org.opensrp.domain.postgres.PractitionerDetails;

public interface PractitionerDetailsRepository {
	
	List<PractitionerLocation> findPractitionerLocationsByChildGroup(int practitionerId, int childGroupId,
	                                                                 int assignedLocationTagId);
	
	PractitionerDetails findPractitionerDetailsByUsername(String username);
	
	String getForceSyncStatus(String username);
	
	int updateAppVersion(String username, String version);
	
	Boolean getUserStatus(String username);
	
	Boolean checkUserMobileIMEI(String imeiNumber);
	
}
