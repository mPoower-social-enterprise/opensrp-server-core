package org.opensrp.repository.postgres;

import java.util.List;

import org.opensrp.domain.PractitionerLocation;
import org.opensrp.domain.postgres.PractitionerDetails;
import org.opensrp.repository.PractitionerDetailsRepository;
import org.opensrp.repository.postgres.mapper.custom.CustomPractitionerDetailsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PractitionerDetailsRepositoryImpl implements PractitionerDetailsRepository {
	
	@Autowired
	private CustomPractitionerDetailsMapper customPractitionerDetailsMapper;
	
	@Override
	public List<PractitionerLocation> findPractitionerLocationsByChildGroup(int practitionerId, int childGroupId,
	                                                                        int assignedLocationTagId) {
		
		return customPractitionerDetailsMapper.selectPractitionerLocationsByChildGroup(practitionerId, childGroupId,
		    assignedLocationTagId);
	}
	
	@Override
	public PractitionerDetails findPractitionerDetailsByUsername(String username) {
		
		return customPractitionerDetailsMapper.selectPractitionerDetailsByUsername(username);
	}
	
	@Override
	public String getForceSyncStatus(String username) {
		
		return customPractitionerDetailsMapper.selectForceSyncStatus(username);
	}
	
	@Override
	public int updateAppVersion(String username, String version) {
		return customPractitionerDetailsMapper.updateAppVersion(username, version);
		
	}
	
	@Override
	public Boolean getUserStatus(String username) {
		return customPractitionerDetailsMapper.selectUserStatus(username);
	}
	
	@Override
	public Boolean checkUserMobileIMEI(String imeiNumber) {
		
		return customPractitionerDetailsMapper.selectUserMobileIMEI(imeiNumber);
	}
	
}
