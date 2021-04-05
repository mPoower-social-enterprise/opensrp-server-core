/**
 * 
 */
package org.opensrp.repository.postgres;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;
import org.opensrp.domain.PractitionerLocation;
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
		    "core.location_metadata", "core.location_tag_map", "team.practitioner_group",
		    "team.practitioner_catchment_area");
	}
	
	@Override
	protected Set<String> getDatabaseScripts() {
		Set<String> scripts = new HashSet<>();
		scripts.add("practitioner_details.sql");
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
	
}
