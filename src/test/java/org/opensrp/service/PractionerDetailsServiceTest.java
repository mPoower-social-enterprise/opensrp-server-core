package org.opensrp.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opensrp.domain.postgres.PractitionerDetails;
import org.opensrp.repository.PractitionerDetailsRepository;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class PractionerDetailsServiceTest {
	
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
		        .thenReturn(initPractitionerDetailsData());
		PractitionerDetails practitionerDetails = practionerDetailsService.findPractitionerDetailsByUsername("admin");
		verify(practitionerDetailsRepository).findPractitionerDetailsByUsername("admin");
		assertEquals("admin", practitionerDetails.getFirstName());
	}
	
	private PractitionerDetails initPractitionerDetailsData() {
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
}
