package org.opensrp.service;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.opensrp.domain.postgres.TargetDetails;
import org.opensrp.repository.TargetDetailsRepository;
import org.opensrp.repository.postgres.BaseRepositoryTest;
import org.springframework.beans.factory.annotation.Autowired;

public class TargetDetailsServiceTest extends BaseRepositoryTest {
	
	private TargetDetailsService targetDetailsService;
	
	@Autowired
	private TargetDetailsRepository targetDetailsRepository;
	
	@Override
	protected Set<String> getDatabaseScripts() {
		Set<String> scripts = new HashSet<>();
		scripts.add("practitioner_details.sql");
		scripts.add("target_details.sql");
		return scripts;
	}
	
	@Before
	public void setUpPostgresRepository() {
		targetDetailsService = new TargetDetailsService(targetDetailsRepository);
	}
	
	@Test
	public void testGetTargetDetailsByUsername() {
		List<TargetDetails> targetDetails = targetDetailsService.getTargetDetailsByUsername("p1", 0l);
		assertEquals(1, targetDetails.size());
		assertEquals("Women package", targetDetails.get(0).getTargetName());
		assertEquals("p1", targetDetails.get(0).getUsername());
		assertEquals(5, targetDetails.get(0).getTargetCount());
	}
	
	@Test
	public void testGetTargetDetailsByUsernameReturnEmpty() {
		List<TargetDetails> targetDetails = targetDetailsService.getTargetDetailsByUsername("p1234", 0l);
		assertEquals(0, targetDetails.size());
		
	}
}
