package org.opensrp.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opensrp.domain.PractitionerLocation;
import org.opensrp.domain.postgres.PractitionerDetails;
import org.opensrp.repository.PractitionerDetailsRepository;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class PractitionerDetailsServiceTest {
	
	private PractionerDetailsService practionerDetailsService;
	
	private PractitionerDetailsRepository practitionerDetailsRepository;
	
	@Before
	public void setUp() {
		practitionerDetailsRepository = mock(PractitionerDetailsRepository.class);
		practionerDetailsService = new PractionerDetailsService();
		practionerDetailsService.setPractitionerDetailsRepository(practitionerDetailsRepository);
		
	}
	
	@Test
	public void testFindPractitionerDetailsByUsername() {
		when(practitionerDetailsRepository.findPractitionerDetailsByUsername("admin"))
		        .thenReturn(createPractitionerDetailsData());
		PractitionerDetails practitionerDetails = practionerDetailsService.findPractitionerDetailsByUsername("admin");
		verify(practitionerDetailsRepository).findPractitionerDetailsByUsername("admin");
		assertEquals("admin", practitionerDetails.getFirstName());
	}
	
	@Test
	public void testFindPractitionerLocationsByChildGroup() {
		when(practitionerDetailsRepository.findPractitionerLocationsByChildGroup(1, 29, 7))
		        .thenReturn(createPractitionerLocationData());
		
		List<PractitionerLocation> practitionerLocations = practionerDetailsService.findPractitionerLocationsByChildGroup(1,
		    29, 7);
		verify(practitionerDetailsRepository).findPractitionerLocationsByChildGroup(1, 29, 7);
		assertEquals(14, practitionerLocations.size());
	}
	
	@Test
	public void testConvertLocationTreeToJSON() {
		JSONArray practitionerLocationjson = practionerDetailsService
		        .convertLocationTreeToJSON(createPractitionerLocationData(), true, "Test SK");
		assertEquals(2, practitionerLocationjson.length());
		assertEquals("Test SK", practitionerLocationjson.optJSONObject(0).get("full_name"));
	}
	
	@Test
	public void testGetForceSyncStatus() {
		when(practitionerDetailsRepository.getForceSyncStatus("p1")).thenReturn("yes");
		String forceSyncStatus = practionerDetailsService.getForceSyncStatus("p1");
		verify(practitionerDetailsRepository).getForceSyncStatus("p1");
		assertEquals("yes", forceSyncStatus);
	}
	
	@Test
	public void testGetForceSyncStatusReturnEmpty() {
		when(practitionerDetailsRepository.getForceSyncStatus("p1")).thenReturn("");
		String forceSyncStatus = practionerDetailsService.getForceSyncStatus("p1");
		verify(practitionerDetailsRepository).getForceSyncStatus("p1");
		assertEquals("", forceSyncStatus);
	}
	
	@Test
	public void testUpdateAppVersion() {
		when(practitionerDetailsRepository.updateAppVersion("p1", "23")).thenReturn(1);
		int updateAppVersion = practionerDetailsService.updateAppVersion("p1", "23");
		verify(practitionerDetailsRepository).updateAppVersion("p1", "23");
		assertEquals(1, updateAppVersion);
	}
	
	@Test
	public void testShouldReturnTrueGetUserStatus() {
		when(practitionerDetailsRepository.getUserStatus("p1")).thenReturn(true);
		Boolean getUserStatus = practionerDetailsService.getUserStatus("p1");
		verify(practitionerDetailsRepository).getUserStatus("p1");
		assertTrue(getUserStatus);
		
	}
	
	@Test
	public void testShouldReturnFalseGetUserStatus() {
		when(practitionerDetailsRepository.getUserStatus("p1")).thenReturn(false);
		Boolean getUserStatus = practionerDetailsService.getUserStatus("p1");
		verify(practitionerDetailsRepository).getUserStatus("p1");
		assertFalse(getUserStatus);
		
	}
	
	@Test
	public void testShouldReturnTrueCheckUserMobileIMEI() {
		when(practitionerDetailsRepository.checkUserMobileIMEI("imei1")).thenReturn(true);
		Boolean getUserStatus = practionerDetailsService.checkUserMobileIMEI("imei1");
		verify(practitionerDetailsRepository).checkUserMobileIMEI("imei1");
		assertTrue(getUserStatus);
		
	}
	
	@Test
	public void testShouldReturnFalseCheckUserMobileIMEI() {
		when(practitionerDetailsRepository.checkUserMobileIMEI("imei1")).thenReturn(false);
		Boolean getUserStatus = practionerDetailsService.checkUserMobileIMEI("imei1");
		verify(practitionerDetailsRepository).checkUserMobileIMEI("imei1");
		assertFalse(getUserStatus);
		
	}
	
	private PractitionerDetails createPractitionerDetailsData() {
		PractitionerDetails practitionerDetails = new PractitionerDetails();
		practitionerDetails.setAppVersion("1.3.1");
		practitionerDetails.setCreatedDate(null);
		practitionerDetails.setCreator(1);
		practitionerDetails.setEmail("admin@gmail.com");
		practitionerDetails.setEnabled(true);
		practitionerDetails.setEnableSimPrint(true);
		practitionerDetails.setFirstName("admin");
		practitionerDetails.setGender("male");
		practitionerDetails.setId(1);
		practitionerDetails.setSsNo("1");
		return practitionerDetails;
		
	}
	
	private List<PractitionerLocation> createPractitionerLocationData() {
		PractitionerLocation pl2 = new PractitionerLocation();
		pl2.setCode("3735");
		pl2.setId(3735);
		pl2.setEnable(true);
		pl2.setFirstName("p2");
		pl2.setGroupName(null);
		pl2.setLastName("");
		pl2.setLeafLocationId(7);
		pl2.setLocationName("DHAKA");
		pl2.setLocationTagName("Division");
		pl2.setUsername("p2");
		
		PractitionerLocation pl1 = new PractitionerLocation();
		pl1.setCode("3734");
		pl1.setId(3734);
		pl1.setEnable(true);
		pl1.setFirstName("p2");
		pl1.setGroupName(null);
		pl1.setLastName("");
		pl1.setLeafLocationId(7);
		pl1.setLocationName("Bangladesh");
		pl1.setLocationTagName("Country");
		pl1.setUsername("p2");
		
		PractitionerLocation pl3 = new PractitionerLocation();
		pl3.setCode("3736");
		pl3.setId(3736);
		pl3.setEnable(true);
		pl3.setFirstName("p2");
		pl3.setGroupName(null);
		pl3.setLastName("");
		pl3.setLeafLocationId(7);
		pl3.setLocationName("DHAKA:9266");
		pl3.setLocationTagName("District");
		pl3.setUsername("p2");
		
		PractitionerLocation pl4 = new PractitionerLocation();
		pl4.setCode("3737");
		pl4.setId(3737);
		pl4.setEnable(true);
		pl4.setFirstName("p2");
		pl4.setGroupName(null);
		pl4.setLastName("");
		pl4.setLeafLocationId(7);
		pl4.setLocationName("DHAKA NORTH CITY CORPORATION:9267");
		pl4.setLocationTagName("City Corporation Upazila");
		pl4.setUsername("p2");
		
		PractitionerLocation pl5 = new PractitionerLocation();
		pl5.setCode("3739");
		pl5.setId(3739);
		pl5.setEnable(true);
		pl5.setFirstName("p2");
		pl5.setGroupName(null);
		pl5.setLastName("");
		pl5.setLeafLocationId(7);
		pl5.setLocationName("NOT POURASABHA:9268");
		pl5.setLocationTagName("Pourasabha");
		pl5.setUsername("p2");
		
		PractitionerLocation pl6 = new PractitionerLocation();
		pl6.setCode("3740");
		pl6.setId(3740);
		pl6.setEnable(true);
		pl6.setFirstName("p2");
		pl6.setGroupName(null);
		pl6.setLastName("");
		pl6.setLeafLocationId(7);
		pl6.setLocationName("WARD NO. 19 (PART):9269");
		pl6.setLocationTagName("Union Ward");
		pl6.setUsername("p2");
		PractitionerLocation pl7 = new PractitionerLocation();
		pl7.setCode("3741");
		pl7.setId(3741);
		pl7.setEnable(true);
		pl7.setFirstName("p2");
		pl7.setGroupName(null);
		pl7.setLastName("");
		pl7.setLeafLocationId(7);
		pl7.setLocationName("T&T COLONY-T&T Mosjid:9270");
		pl7.setLocationTagName("Village");
		pl7.setUsername("p2");
		List<PractitionerLocation> practitionerLocations = new ArrayList<>();
		
		practitionerLocations.add(pl2);
		practitionerLocations.add(pl1);
		practitionerLocations.add(pl3);
		practitionerLocations.add(pl4);
		practitionerLocations.add(pl5);
		practitionerLocations.add(pl6);
		practitionerLocations.add(pl7);
		
		PractitionerLocation pl8 = new PractitionerLocation();
		pl8.setCode("3734");
		pl8.setId(3734);
		pl8.setEnable(true);
		pl8.setFirstName("p3");
		pl8.setGroupName(null);
		pl8.setLastName("");
		pl8.setLeafLocationId(7);
		pl8.setLocationName("Bangladesh");
		pl8.setLocationTagName("Country");
		pl8.setUsername("p3");
		
		PractitionerLocation pl9 = new PractitionerLocation();
		pl9.setCode("3735");
		pl9.setId(3735);
		pl9.setEnable(true);
		pl9.setFirstName("p3");
		pl9.setGroupName(null);
		pl9.setLastName("");
		pl9.setLeafLocationId(7);
		pl9.setLocationName("DHAKA");
		pl9.setLocationTagName("Division");
		pl9.setUsername("p3");
		
		PractitionerLocation pl10 = new PractitionerLocation();
		pl10.setCode("3736");
		pl10.setId(3736);
		pl10.setEnable(true);
		pl10.setFirstName("p3");
		pl10.setGroupName(null);
		pl10.setLastName("");
		pl10.setLeafLocationId(7);
		pl10.setLocationName("DHAKA:9266");
		pl10.setLocationTagName("District");
		pl10.setUsername("p3");
		
		PractitionerLocation pl11 = new PractitionerLocation();
		pl11.setCode("3737");
		pl11.setId(3737);
		pl11.setEnable(true);
		pl11.setFirstName("p3");
		pl11.setGroupName(null);
		pl11.setLastName("");
		pl11.setLeafLocationId(7);
		pl11.setLocationName("DHAKA NORTH CITY CORPORATION:9267");
		pl11.setLocationTagName("City Corporation Upazila");
		pl11.setUsername("p3");
		
		PractitionerLocation pl12 = new PractitionerLocation();
		pl12.setCode("3739");
		pl12.setId(3739);
		pl12.setEnable(true);
		pl12.setFirstName("p3");
		pl12.setGroupName(null);
		pl12.setLastName("");
		pl12.setLeafLocationId(7);
		pl12.setLocationName("NOT POURASABHA:9268");
		pl12.setLocationTagName("Pourasabha");
		pl12.setUsername("p3");
		
		PractitionerLocation pl13 = new PractitionerLocation();
		pl13.setCode("3740");
		pl13.setId(3740);
		pl13.setEnable(true);
		pl13.setFirstName("p3");
		pl13.setGroupName(null);
		pl13.setLastName("");
		pl13.setLeafLocationId(7);
		pl13.setLocationName("WARD NO. 19 (PART):9269");
		pl13.setLocationTagName("Union Ward");
		pl13.setUsername("p3");
		
		PractitionerLocation pl14 = new PractitionerLocation();
		pl14.setCode("3741");
		pl14.setId(3741);
		pl14.setEnable(true);
		pl14.setFirstName("p3");
		pl14.setGroupName(null);
		pl14.setLastName("");
		pl14.setLeafLocationId(7);
		pl14.setLocationName("T&T COLONY-T&T Mosjid:9271");
		pl14.setLocationTagName("Village");
		pl14.setUsername("p3");
		practitionerLocations.add(pl8);
		practitionerLocations.add(pl9);
		practitionerLocations.add(pl10);
		practitionerLocations.add(pl11);
		practitionerLocations.add(pl12);
		practitionerLocations.add(pl13);
		practitionerLocations.add(pl14);
		return practitionerLocations;
		
	}
	
}