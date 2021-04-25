package org.opensrp.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opensrp.domain.postgres.MhealthPractitionerLocation;
import org.opensrp.repository.LocationRepository;
import org.opensrp.repository.LocationTagRepository;
import org.opensrp.repository.PractitionerLocationRepository;
import org.opensrp.repository.postgres.BaseRepositoryTest;
import org.smartregister.domain.Geometry;
import org.smartregister.domain.Geometry.GeometryType;
import org.smartregister.domain.LocationProperty;
import org.smartregister.domain.LocationProperty.PropertyStatus;
import org.smartregister.domain.LocationTag;
import org.smartregister.domain.PhysicalLocation;
import org.springframework.beans.factory.annotation.Autowired;

public class PractitionerLocationServiceTest extends BaseRepositoryTest {
	
	private PractitionerLocationService practitionerLocationService;
	
	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private LocationTagRepository locationTagRepository;
	
	@Autowired
	private PractitionerLocationRepository practitionerLocationRepository;
	
	@BeforeClass
	public static void bootStrap() {
		tableNames = Arrays.asList("core.location", "core.location_metadata", "core.location_tag", "core.location_tag_map");
	}
	
	@Before
	public void setUpPostgresRepository() {
		practitionerLocationService = new PractitionerLocationService(practitionerLocationRepository);
	}
	
	@Override
	protected Set<String> getDatabaseScripts() {
		return null;
	}
	
	@Test
	public void testGetDistrictAndUpazila() {
		
		addLocation();
		org.opensrp.domain.postgres.LocationTag getLocationTag = locationTagRepository.getLocationTagByName("District");
		
		JSONArray getDistrictAndUpazila = practitionerLocationService
		        .getDistrictAndUpazila(getLocationTag.getId().intValue());
		
		String concateDistrictName = "";
		for (int i = 0; i < getDistrictAndUpazila.length(); i++) {
			concateDistrictName += getDistrictAndUpazila.getJSONObject(i).get("name") + "_";
		}
		assertNotNull(getDistrictAndUpazila);
		assertEquals(2, getDistrictAndUpazila.length());
		assertEquals("Gazipur_Rajbari_", concateDistrictName);
	}
	
	@Test
	public void testShouldReturnEmptyGetDistrictAndUpazila() {
		JSONArray getDistrictAndUpazila = practitionerLocationService.getDistrictAndUpazila(0);
		assertNotNull(getDistrictAndUpazila);
		assertEquals(0, getDistrictAndUpazila.length());
		
	}
	
	@Test
	public void testGetLocationByTagId() {
		
		addLocation();
		org.opensrp.domain.postgres.LocationTag getLocationTag = locationTagRepository.getLocationTagByName("District");
		
		List<MhealthPractitionerLocation> practitionerLocations = practitionerLocationService
		        .getLocationByTagId(getLocationTag.getId().intValue());
		
		assertNotNull(practitionerLocations);
		assertEquals(2, practitionerLocations.size());
		String concateDistrictName = "";
		for (MhealthPractitionerLocation mhealthPractitionerLocation : practitionerLocations) {
			concateDistrictName += mhealthPractitionerLocation.getName() + "_";
		}
		assertEquals("Gazipur_Rajbari_", concateDistrictName);
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
		
		addLocation();
		org.opensrp.domain.postgres.LocationTag getLocationTag = locationTagRepository.getLocationTagByName("Country");
		
		List<MhealthPractitionerLocation> practitionerLocations = practitionerLocationService
		        .getLocationByTagId(getLocationTag.getId().intValue());
		
		List<MhealthPractitionerLocation> practitionerLocationsByParentId = practitionerLocationService
		        .getLocationByParentId(practitionerLocations.get(0).getId());
		
		assertNotNull(practitionerLocationsByParentId);
		assertEquals("Dhaka", practitionerLocationsByParentId.get(0).getName());
	}
	
	private void addLocation() {
		locationRepository.add(createCountry());
		locationRepository.add(createDivision());
		locationRepository.add(createDistrict());
		locationRepository.add(createDistrict1());
		locationRepository.add(createUpazila());
		locationRepository.add(createUpazila1());
		locationRepository.add(createUpazila2());
	}
	
	private PhysicalLocation createCountry() {
		PhysicalLocation physicalLocation = new PhysicalLocation();
		physicalLocation.setId("223232");
		physicalLocation.setType("Feature");
		physicalLocation.setJurisdiction(true);
		Geometry geometry = new Geometry();
		geometry.setType(GeometryType.MULTI_POLYGON);
		physicalLocation.setGeometry(geometry);
		locationTagRepository.add(initTestLocationTagCountry());
		org.opensrp.domain.postgres.LocationTag getLocationTag = locationTagRepository.getLocationTagByName("Country");
		LocationTag locationTag = new LocationTag();
		locationTag.setId(getLocationTag.getId());
		locationTag.setActive(true);
		locationTag.setName("Country");
		Set<LocationTag> locationTags = new HashSet<>();
		locationTags.add(locationTag);
		physicalLocation.setLocationTags(locationTags);
		LocationProperty properties = new LocationProperty();
		Map<String, String> customProperties = new HashMap<>();
		
		customProperties.put("code", "1");
		properties.setCustomProperties(customProperties);
		properties.setParentId("");
		properties.setStatus(PropertyStatus.ACTIVE);
		properties.setUid(UUID.randomUUID().toString());
		properties.setName("bangladesh");
		physicalLocation.setProperties(properties);
		
		return physicalLocation;
		
	}
	
	private PhysicalLocation createDivision() {
		PhysicalLocation physicalLocation = new PhysicalLocation();
		physicalLocation.setId("2232321");
		physicalLocation.setType("Feature");
		physicalLocation.setJurisdiction(true);
		Geometry geometry = new Geometry();
		geometry.setType(GeometryType.MULTI_POLYGON);
		physicalLocation.setGeometry(geometry);
		
		locationTagRepository.add(initTestLocationTagDivision());
		org.opensrp.domain.postgres.LocationTag getLocationTag = locationTagRepository.getLocationTagByName("Division");
		
		LocationTag locationTag = new LocationTag();
		locationTag.setId(getLocationTag.getId());
		locationTag.setActive(true);
		locationTag.setName(getLocationTag.getName());
		Set<LocationTag> locationTags = new HashSet<>();
		locationTags.add(locationTag);
		physicalLocation.setLocationTags(locationTags);
		
		LocationProperty properties = new LocationProperty();
		Map<String, String> customProperties = new HashMap<>();
		
		customProperties.put("code", "2");
		properties.setCustomProperties(customProperties);
		properties.setParentId("223232");
		properties.setStatus(PropertyStatus.ACTIVE);
		properties.setUid(UUID.randomUUID().toString());
		properties.setName("Dhaka");
		physicalLocation.setProperties(properties);
		
		return physicalLocation;
		
	}
	
	private PhysicalLocation createDistrict() {
		PhysicalLocation physicalLocation = new PhysicalLocation();
		physicalLocation.setId("22323211");
		physicalLocation.setType("Feature");
		physicalLocation.setJurisdiction(true);
		Geometry geometry = new Geometry();
		geometry.setType(GeometryType.MULTI_POLYGON);
		physicalLocation.setGeometry(geometry);
		
		locationTagRepository.add(initTestLocationTagDistrict());
		org.opensrp.domain.postgres.LocationTag getLocationTag = locationTagRepository.getLocationTagByName("District");
		
		LocationTag locationTag = new LocationTag();
		locationTag.setId(getLocationTag.getId());
		locationTag.setActive(true);
		locationTag.setName(getLocationTag.getName());
		Set<LocationTag> locationTags = new HashSet<>();
		locationTags.add(locationTag);
		physicalLocation.setLocationTags(locationTags);
		
		LocationProperty properties = new LocationProperty();
		Map<String, String> customProperties = new HashMap<>();
		
		customProperties.put("code", "3");
		properties.setCustomProperties(customProperties);
		properties.setParentId("2232321");
		properties.setStatus(PropertyStatus.ACTIVE);
		properties.setUid(UUID.randomUUID().toString());
		properties.setName("Gazipur");
		physicalLocation.setProperties(properties);
		
		return physicalLocation;
		
	}
	
	private PhysicalLocation createDistrict1() {
		PhysicalLocation physicalLocation = new PhysicalLocation();
		physicalLocation.setId("223232112");
		physicalLocation.setType("Feature");
		physicalLocation.setJurisdiction(true);
		Geometry geometry = new Geometry();
		geometry.setType(GeometryType.MULTI_POLYGON);
		physicalLocation.setGeometry(geometry);
		
		org.opensrp.domain.postgres.LocationTag getLocationTag = locationTagRepository.getLocationTagByName("District");
		
		LocationTag locationTag = new LocationTag();
		locationTag.setId(getLocationTag.getId());
		locationTag.setActive(true);
		locationTag.setName(getLocationTag.getName());
		Set<LocationTag> locationTags = new HashSet<>();
		locationTags.add(locationTag);
		physicalLocation.setLocationTags(locationTags);
		
		LocationProperty properties = new LocationProperty();
		Map<String, String> customProperties = new HashMap<>();
		
		customProperties.put("code", "33");
		properties.setCustomProperties(customProperties);
		properties.setParentId("2232321");
		properties.setStatus(PropertyStatus.ACTIVE);
		properties.setUid(UUID.randomUUID().toString());
		properties.setName("Rajbari");
		physicalLocation.setProperties(properties);
		
		return physicalLocation;
		
	}
	
	private PhysicalLocation createUpazila2() {
		PhysicalLocation physicalLocation = new PhysicalLocation();
		physicalLocation.setId("2232321113");
		physicalLocation.setType("Feature");
		physicalLocation.setJurisdiction(true);
		Geometry geometry = new Geometry();
		geometry.setType(GeometryType.MULTI_POLYGON);
		physicalLocation.setGeometry(geometry);
		
		org.opensrp.domain.postgres.LocationTag getLocationTag = locationTagRepository
		        .getLocationTagByName("City Corporation Upazila");
		
		LocationTag locationTag = new LocationTag();
		locationTag.setId(getLocationTag.getId());
		locationTag.setActive(true);
		locationTag.setName(getLocationTag.getName());
		Set<LocationTag> locationTags = new HashSet<>();
		locationTags.add(locationTag);
		physicalLocation.setLocationTags(locationTags);
		
		LocationProperty properties = new LocationProperty();
		Map<String, String> customProperties = new HashMap<>();
		
		customProperties.put("code", "40");
		properties.setCustomProperties(customProperties);
		properties.setParentId("223232112");
		properties.setStatus(PropertyStatus.ACTIVE);
		properties.setUid(UUID.randomUUID().toString());
		properties.setName("Baliakandi");
		physicalLocation.setProperties(properties);
		
		return physicalLocation;
		
	}
	
	private PhysicalLocation createUpazila() {
		PhysicalLocation physicalLocation = new PhysicalLocation();
		physicalLocation.setId("223232111");
		physicalLocation.setType("Feature");
		physicalLocation.setJurisdiction(true);
		Geometry geometry = new Geometry();
		geometry.setType(GeometryType.MULTI_POLYGON);
		physicalLocation.setGeometry(geometry);
		
		locationTagRepository.add(initTestLocationTagCityCorporationUpazila());
		org.opensrp.domain.postgres.LocationTag getLocationTag = locationTagRepository
		        .getLocationTagByName("City Corporation Upazila");
		
		LocationTag locationTag = new LocationTag();
		locationTag.setId(getLocationTag.getId());
		locationTag.setActive(true);
		locationTag.setName(getLocationTag.getName());
		Set<LocationTag> locationTags = new HashSet<>();
		locationTags.add(locationTag);
		physicalLocation.setLocationTags(locationTags);
		
		LocationProperty properties = new LocationProperty();
		Map<String, String> customProperties = new HashMap<>();
		
		customProperties.put("code", "4");
		properties.setCustomProperties(customProperties);
		properties.setParentId("22323211");
		properties.setStatus(PropertyStatus.ACTIVE);
		properties.setUid(UUID.randomUUID().toString());
		properties.setName("Kapasia");
		physicalLocation.setProperties(properties);
		
		return physicalLocation;
		
	}
	
	private PhysicalLocation createUpazila1() {
		PhysicalLocation physicalLocation = new PhysicalLocation();
		physicalLocation.setId("2232321111");
		physicalLocation.setType("Feature");
		physicalLocation.setJurisdiction(true);
		Geometry geometry = new Geometry();
		geometry.setType(GeometryType.MULTI_POLYGON);
		physicalLocation.setGeometry(geometry);
		
		org.opensrp.domain.postgres.LocationTag getLocationTag = locationTagRepository
		        .getLocationTagByName("City Corporation Upazila");
		
		LocationTag locationTag = new LocationTag();
		locationTag.setId(getLocationTag.getId());
		locationTag.setActive(true);
		locationTag.setName(getLocationTag.getName());
		Set<LocationTag> locationTags = new HashSet<>();
		locationTags.add(locationTag);
		physicalLocation.setLocationTags(locationTags);
		
		LocationProperty properties = new LocationProperty();
		Map<String, String> customProperties = new HashMap<>();
		
		customProperties.put("code", "5");
		properties.setCustomProperties(customProperties);
		properties.setParentId("22323211");
		properties.setStatus(PropertyStatus.ACTIVE);
		properties.setUid(UUID.randomUUID().toString());
		properties.setName("Kaliakair");
		physicalLocation.setProperties(properties);
		
		return physicalLocation;
		
	}
	
	private LocationTag initTestLocationTagCountry() {
		LocationTag locationTag = new LocationTag();
		locationTag.setName("Country");
		locationTag.setDescription("0");
		locationTag.setActive(true);
		
		return locationTag;
	}
	
	private LocationTag initTestLocationTagDivision() {
		LocationTag locationTag = new LocationTag();
		locationTag.setName("Division");
		locationTag.setDescription("1");
		locationTag.setActive(true);
		
		return locationTag;
	}
	
	private LocationTag initTestLocationTagDistrict() {
		LocationTag locationTag = new LocationTag();
		locationTag.setName("District");
		locationTag.setDescription("2");
		locationTag.setActive(true);
		
		return locationTag;
	}
	
	private LocationTag initTestLocationTagCityCorporationUpazila() {
		LocationTag locationTag = new LocationTag();
		locationTag.setName("City Corporation Upazila");
		locationTag.setDescription("3");
		locationTag.setActive(true);
		
		return locationTag;
	}
	
}
