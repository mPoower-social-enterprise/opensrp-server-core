package org.opensrp.repository.postgres;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.opensrp.domain.postgres.TargetDetails;
import org.opensrp.repository.TargetDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class TargetDetailsRepositoryTest extends BaseRepositoryTest {
	
	@Autowired
	private TargetDetailsRepository targetDetailsRepository;
	
	@Override
	protected Set<String> getDatabaseScripts() {
		Set<String> scripts = new HashSet<>();
		scripts.add("practitioner_details.sql");
		scripts.add("target_details.sql");
		return scripts;
	}
	
	@Test
	public void testGetTargetDetailsByUsername() {
		List<TargetDetails> targetDetails = targetDetailsRepository.getTargetDetailsByUsername("p1", 0l);
		assertEquals(1, targetDetails.size());
		assertEquals("Women package", targetDetails.get(0).getTargetName());
		assertEquals("p1", targetDetails.get(0).getUsername());
		assertEquals(5, targetDetails.get(0).getTargetCount());
	}
	
	@Test
	public void testShouldReturnEmptyGetTargetDetailsByUsername() {
		List<TargetDetails> targetDetails = targetDetailsRepository.getTargetDetailsByUsername("p1234", 0l);
		assertEquals(0, targetDetails.size());
		
	}
	
}
