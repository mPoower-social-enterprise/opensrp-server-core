/**
 * 
 */
package org.opensrp.repository.postgres;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;
import org.opensrp.domain.PractitionerLocation;
import org.opensrp.domain.postgres.PractitionerDetails;
import org.opensrp.repository.PractitionerDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author proshanto
 */
public class PractitionerDetailsRepositoryTest extends BaseRepositoryTest {
	
	@Autowired
	private PractitionerDetailsRepository practitionerDetailsRepository;
	
	@BeforeClass
	public static void bootStrap() {
		tableNames = Arrays.asList("team.practitioner", "team.practitioner_details", "core.location_tag", "core.location",
		    "core.location_metadata", "core.location_tag_map", "team.practitioner_group", "team.practitioner_catchment_area",
		    "core.imei");
	}
	
	@Override
	protected Set<String> getDatabaseScripts() {
		Set<String> scripts = new HashSet<>();
		scripts.add("practitioner_details.sql");
		scripts.add("imei.sql");
		return scripts;
	}
	
	@Test
	public void testFindPractitionerLocationsByChildGroup() {
		List<PractitionerLocation> practitionerLocations = practitionerDetailsRepository
		        .findPractitionerLocationsByChildGroup(1, 29, 7);
		assertNotNull(practitionerLocations);
		assertEquals(7, practitionerLocations.size());
	}
	
	@Test
	public void testShouldReturnEmptyFindPractitionerLocationsByChildGroup() {
		List<PractitionerLocation> practitionerLocations = practitionerDetailsRepository
		        .findPractitionerLocationsByChildGroup(1, 29, 0);
		assertEquals(0, practitionerLocations.size());
		
	}
	
	@Test
	public void testfindPractitionerDetailsByUsername() {
		PractitionerDetails practitionerDetails = practitionerDetailsRepository.findPractitionerDetailsByUsername("p1");
		assertNotNull(practitionerDetails);
		assertEquals("test", practitionerDetails.getFirstName());
		assertEquals("1", practitionerDetails.getPractitionerId().toString());
		assertEquals("01912773007", practitionerDetails.getMobile());
	}
	
	@Test
	public void testShouldReturnNullFindPractitionerDetailsByUsername() {
		PractitionerDetails practitionerDetails = practitionerDetailsRepository.findPractitionerDetailsByUsername(null);
		assertNull(practitionerDetails);
		
	}
	
	@Test
	public void testGetForceSyncStatus() {
		String getForceSyncStatus = practitionerDetailsRepository.getForceSyncStatus("p1");
		assertNotNull(getForceSyncStatus);
		assertEquals("yes", getForceSyncStatus);
		
	}
	
	@Test
	public void testShouldReturnEmptyGetForceSyncStatus() {
		String getForceSyncStatus = practitionerDetailsRepository.getForceSyncStatus("p22");
		assertEquals("", getForceSyncStatus);
		
	}
	
	@Test
	public void testshouldReturnEmptyGetForceSync() {
		String getForceSyncStatus = practitionerDetailsRepository.getForceSyncStatus("p2");
		assertNotNull(getForceSyncStatus);
		assertEquals("", getForceSyncStatus);
	}
	
	@Test
	public void testUpdateAppVersion() {
		int updateAppVersion = practitionerDetailsRepository.updateAppVersion("p1", "23");
		assertEquals(1, updateAppVersion);
		PractitionerDetails practitionerDetails = practitionerDetailsRepository.findPractitionerDetailsByUsername("p1");
		assertNotNull(practitionerDetails);
		assertEquals("23", practitionerDetails.getAppVersion());
	}
	
	@Test
	public void testShouldReturnTrueGetUserStatus() {
		Boolean getUserStatus = practitionerDetailsRepository.getUserStatus("p1");
		assertTrue(getUserStatus);
		
	}
	
	@Test
	public void testShouldReturnFalseGetUserStatus() {
		Boolean getUserStatus = practitionerDetailsRepository.getUserStatus("p2");
		assertFalse(getUserStatus);
	}
	
	@Test
	public void testShouldReturnTrueCheckUserMobileIMEI() {
		Boolean getUserStatus = practitionerDetailsRepository.checkUserMobileIMEI("imei1");
		assertTrue(getUserStatus);
		
	}
	
	@Test
	public void testShouldReturnFalseCheckUserMobileIMEI() {
		Boolean getUserStatus = practitionerDetailsRepository.checkUserMobileIMEI("imei3");
		assertFalse(getUserStatus);
		
	}
	
}
