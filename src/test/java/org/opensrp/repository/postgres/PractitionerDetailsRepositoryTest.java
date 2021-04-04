/**
 * 
 */
package org.opensrp.repository.postgres;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.opensrp.repository.PractitionerDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author proshanto
 */
public class PractitionerDetailsRepositoryTest extends BaseRepositoryTest {
	
	@Autowired
	private PractitionerDetailsRepository practitionerDetailsRepository;
	
	@Override
	protected Set<String> getDatabaseScripts() {
		Set<String> scripts = new HashSet<>();
		scripts.add("practitioner_details.sql");
		return scripts;
	}
	
	@Test
	public void testFindPractitionerLocationsByChildGroup() {
		System.out.println(practitionerDetailsRepository.findPractitionerLocationsByChildGroup(1, 29));
	}
	
}
