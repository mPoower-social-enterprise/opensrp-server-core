package org.opensrp.repository;

import java.util.List;

import org.opensrp.domain.PractitionerLocation;

public interface PractitionerDetailsRepository {
	
	List<PractitionerLocation> findPractitionerLocationsByChildGroup(int practitionerId, int childGroupId,
	                                                                 int assignedLocationTagId);
}
