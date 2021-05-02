package org.opensrp.repository.postgres;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.opensrp.domain.postgres.MhealthPractitionerLocation;
import org.opensrp.repository.LocationTagRepository;
import org.opensrp.repository.PractitionerLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class PractitionerLocationRepositoryTest extends BaseRepositoryTest {
	
	@Autowired
	private LocationTagRepository locationTagRepository;
	
	@Autowired
	private PractitionerLocationRepository practitionerLocationRepository;
	
	@Override
	protected Set<String> getDatabaseScripts() {
		Set<String> scripts = new HashSet<>();
		scripts.add("practitioner_details.sql");
		return scripts;
	}
	
	@Test
	public void testGetDistrictAndUpazila() {
		
		org.opensrp.domain.postgres.LocationTag getLocationTag = locationTagRepository.getLocationTagByName("District");
		
		List<MhealthPractitionerLocation> practitionerLocations = practitionerLocationRepository
		        .getDistrictAndUpazila(getLocationTag.getId().intValue());
		
		assertNotNull(practitionerLocations);
		assertEquals(1, practitionerLocations.size());
		assertEquals("DHAKA NORTH CITY CORPORATION", practitionerLocations.get(0).getUpazila());
		
	}
	
	@Test
	public void testShouldReturnEmptyGetDistrictAndUpazila() {
		
		List<MhealthPractitionerLocation> practitionerLocations = practitionerLocationRepository.getDistrictAndUpazila(0);
		assertNotNull(practitionerLocations);
		assertEquals(0, practitionerLocations.size());
		
	}
	
	@Test
	public void testGetLocationByTagId() {
		
		org.opensrp.domain.postgres.LocationTag getLocationTag = locationTagRepository.getLocationTagByName("District");
		
		List<MhealthPractitionerLocation> practitionerLocations = practitionerLocationRepository
		        .getLocationByTagId(getLocationTag.getId().intValue());
		
		assertNotNull(practitionerLocations);
		assertEquals(1, practitionerLocations.size());
		
		assertEquals("DHAKA", practitionerLocations.get(0).getName());
	}
	
	@Test
	public void testShouldReturnEmptyGetLocationByTagId() {
		
		List<MhealthPractitionerLocation> practitionerLocations = practitionerLocationRepository.getLocationByTagId(0);
		
		assertEquals(0, practitionerLocations.size());
		
	}
	
	@Test
	public void testShouldReturnEmptyGetLocationByParentId() {
		
		List<MhealthPractitionerLocation> practitionerLocationsByParentId = practitionerLocationRepository
		        .getLocationByParentId(0);
		
		assertNotNull(practitionerLocationsByParentId);
		assertEquals(0, practitionerLocationsByParentId.size());
	}
	
	@Test
	public void testGetLocationByParentId() {
		
		org.opensrp.domain.postgres.LocationTag getLocationTag = locationTagRepository.getLocationTagByName("Country");
		
		List<MhealthPractitionerLocation> practitionerLocations = practitionerLocationRepository
		        .getLocationByTagId(getLocationTag.getId().intValue());
		
		List<MhealthPractitionerLocation> practitionerLocationsByParentId = practitionerLocationRepository
		        .getLocationByParentId(practitionerLocations.get(0).getId());
		assertNotNull(practitionerLocationsByParentId);
		assertEquals("DHAKA", practitionerLocationsByParentId.get(0).getName());
	}
	
	@Test
	public void testGetPractitionerVillageIds() {
		List<Integer> practitionerVillageIds = practitionerLocationRepository.getPractitionerVillageIds("p1");
		assertEquals(1, practitionerVillageIds.size());
	}
	
	@Test
	public void testGetPractitionerVillageIdsWithEmpty() {
		List<Integer> practitionerVillageIds = practitionerLocationRepository.getPractitionerVillageIds("p111");
		assertEquals(0, practitionerVillageIds.size());
	}
	
}
