package org.opensrp.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;
import org.opensrp.domain.postgres.MhealthPractitionerLocation;
import org.opensrp.repository.LocationTagRepository;
import org.opensrp.repository.PractitionerLocationRepository;
import org.opensrp.repository.postgres.BaseRepositoryTest;
import org.springframework.beans.factory.annotation.Autowired;

public class PractitionerLocationServiceTest extends BaseRepositoryTest {
	
	private PractitionerLocationService practitionerLocationService;
	
	@Autowired
	private LocationTagRepository locationTagRepository;
	
	@Autowired
	private PractitionerLocationRepository practitionerLocationRepository;
	
	@Before
	public void setUpPostgresRepository() {
		practitionerLocationService = new PractitionerLocationService(practitionerLocationRepository);
	}
	
	@Override
	protected Set<String> getDatabaseScripts() {
		Set<String> scripts = new HashSet<>();
		scripts.add("practitioner_details.sql");
		return scripts;
	}
	
	@Test
	public void testGetDistrictAndUpazila() {
		
		org.opensrp.domain.postgres.LocationTag getLocationTag = locationTagRepository.getLocationTagByName("District");
		
		JSONArray getDistrictAndUpazila = practitionerLocationService
		        .getDistrictAndUpazila(getLocationTag.getId().intValue());
		assertNotNull(getDistrictAndUpazila);
		assertEquals(1, getDistrictAndUpazila.length());
		assertEquals("[{\"name\":\"DHAKA\",\"upazilas\":[\"DHAKA NORTH CITY CORPORATION\"]}]",
		    getDistrictAndUpazila.toString());
	}
	
	@Test
	public void testShouldReturnEmptyGetDistrictAndUpazila() {
		JSONArray getDistrictAndUpazila = practitionerLocationService.getDistrictAndUpazila(0);
		assertNotNull(getDistrictAndUpazila);
		assertEquals(0, getDistrictAndUpazila.length());
		
	}
	
	@Test
	public void testGetLocationByTagId() {
		
		org.opensrp.domain.postgres.LocationTag getLocationTag = locationTagRepository.getLocationTagByName("District");
		
		List<MhealthPractitionerLocation> practitionerLocations = practitionerLocationService
		        .getLocationByTagId(getLocationTag.getId().intValue());
		assertNotNull(practitionerLocations);
		assertEquals(1, practitionerLocations.size());
		assertEquals("DHAKA", practitionerLocations.get(0).getName());
	}
	
	@Test
	public void testShouldReturnEmptyGetLocationByTagId() {
		
		List<MhealthPractitionerLocation> practitionerLocations = practitionerLocationService.getLocationByTagId(0);
		
		assertEquals(0, practitionerLocations.size());
		
	}
	
	@Test
	public void testShouldReturnEmptyGetLocationByParentId() {
		
		List<MhealthPractitionerLocation> practitionerLocationsByParentId = practitionerLocationService
		        .getLocationByParentId(0);
		
		assertNotNull(practitionerLocationsByParentId);
		assertEquals(0, practitionerLocationsByParentId.size());
	}
	
	@Test
	public void testGetLocationByParentId() {
		
		org.opensrp.domain.postgres.LocationTag getLocationTag = locationTagRepository.getLocationTagByName("Country");
		
		List<MhealthPractitionerLocation> practitionerLocations = practitionerLocationService
		        .getLocationByTagId(getLocationTag.getId().intValue());
		
		List<MhealthPractitionerLocation> practitionerLocationsByParentId = practitionerLocationService
		        .getLocationByParentId(practitionerLocations.get(0).getId());
		
		assertNotNull(practitionerLocationsByParentId);
		assertEquals("DHAKA", practitionerLocationsByParentId.get(0).getName());
	}
	
	@Test
	public void testGetPractitionerVillageIds() {
		List<Integer> practitionerVillageIds = practitionerLocationService.getPractitionerVillageIds("p1");
		assertEquals(1, practitionerVillageIds.size());
	}
	
	@Test
	public void testGetPractitionerVillageIdsWithEmpty() {
		List<Integer> practitionerVillageIds = practitionerLocationService.getPractitionerVillageIds("p111");
		assertEquals(0, practitionerVillageIds.size());
	}
	
	@Test
	public void testGetPractitionerDivisionDistrictBranch() {
		MhealthPractitionerLocation mhealthPractitionerLocation = practitionerLocationService
		        .getPractitionerDivisionDistrictBranch("p1");
		
		assertNotNull(mhealthPractitionerLocation);
		assertEquals("_321", mhealthPractitionerLocation.getPostFix());
		assertEquals("2", mhealthPractitionerLocation.getBranch());
	}
	
	@Test
	public void testShouldReturnNullGetPractitionerDivisionDistrictBranch() {
		MhealthPractitionerLocation mhealthPractitionerLocation = practitionerLocationService
		        .getPractitionerDivisionDistrictBranch("p123");
		assertNull(mhealthPractitionerLocation);
		
	}
	
}
