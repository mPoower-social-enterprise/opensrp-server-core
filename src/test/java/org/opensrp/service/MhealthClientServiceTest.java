package org.opensrp.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opensrp.repository.MhealthClientsRepository;
import org.opensrp.repository.postgres.BaseRepositoryTest;
import org.opensrp.repository.postgres.MhealthClientsRepositoryImpl;
import org.smartregister.domain.Address;
import org.smartregister.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;

public class MhealthClientServiceTest extends BaseRepositoryTest {
	
	private MhealthClientService mhealthClientService;
	
	@Autowired
	private MhealthClientsRepository mhealthClientsRepository;
	
	@BeforeClass
	public static void bootStrap() {
		tableNames = Arrays.asList("core.client", "core.client_metadata");
	}
	
	@Before
	public void setUpPostgresRepository() {
		mhealthClientService = new MhealthClientService(mhealthClientsRepository);
	}
	
	@Override
	protected Set<String> getDatabaseScripts() {
		return null;
	}
	
	@Test
	public void testAddorUpdate() {
		Client client = new Client("f67823b0-378e-4a35-93fc-bb00def74e2f").withBirthdate(new DateTime("2017-03-31"), true)
		        .withGender("Male").withFirstName("xobili").withLastName("mbangwa");
		List<Address> addresses = new ArrayList<Address>();
		Address address = new Address();
		address.setCountry("BBANGLADESH");
		address.setAddressType("usual_residence");
		address.setCityVillage("VILLAGE");
		address.setStateProvince("DIVISION");
		address.setCountyDistrict("DISTRICT");
		
		Map<String, String> addressFields = new HashMap<>();
		addressFields.put("address1", "wARD");
		addressFields.put("address2", "UPAZILA");
		addressFields.put("address3", "POURASAVA");
		addressFields.put("address8", "2345");
		address.setAddressFields(addressFields);
		addresses.add(address);
		Map<String, List<String>> relationships = new HashMap<>();
		List<String> relationalIds = new ArrayList<>();
		relationalIds.add("23333");
		relationships.put("family", relationalIds);
		client.setAddresses(addresses);
		client.setRelationships(relationships);
		client.setServerVersion(0);
		client.withIdentifier("ZEIR_ID", "233864-8").withAttribute("Home_Facility", "Linda");
		String district = "234";
		String postfix = "";
		String division = "233";
		String branch = "34";
		mhealthClientService.addOrUpdate(client, district, division, branch);
		
		Client savedClient = mhealthClientService.findByBaseEntityId("f67823b0-378e-4a35-93fc-bb00def74e2f", postfix);
		
		assertNotNull(savedClient);
		assertEquals("xobili", savedClient.getFirstName());
		assertEquals("233864-8", savedClient.getIdentifier("ZEIR_ID"));
		
		//test adding existing client is updated
		DateTime timeBeforeUpdate = new DateTime();
		savedClient.withMiddleName("Rustus");
		mhealthClientService.addOrUpdate(savedClient, district, division, branch);
		
		Client updatedClient = mhealthClientService.findByBaseEntityId("f67823b0-378e-4a35-93fc-bb00def74e2f", postfix);
		assertEquals("Rustus", updatedClient.getMiddleName());
		assertEquals(MhealthClientsRepositoryImpl.REVISION_PREFIX + 2, updatedClient.getRevision());
		assertTrue(timeBeforeUpdate.isBefore(updatedClient.getDateEdited()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testShouldReturnIllegalArgumentExceptionForNUllClientAddorUpdate() {
		String district = "234";
		
		String division = "233";
		String branch = "34";
		mhealthClientService.addOrUpdate(null, district, division, branch);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testShouldReturnIllegalArgumentExceptionForEmptyBaseEntityIdClientAddorUpdate() {
		String district = "234";
		Client client = new Client(null).withBirthdate(new DateTime("2017-03-31"), true).withGender("Male")
		        .withFirstName("xobili").withLastName("mbangwa");
		String division = "233";
		String branch = "34";
		mhealthClientService.addOrUpdate(client, district, division, branch);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIIllegalArgumentExceptionTestAdd() {
		String district = "234";
		String postfix = "";
		String division = "233";
		String branch = "34";
		mhealthClientsRepository.add(null, postfix, district, division, branch);
	}
	
	@Test
	public void testFindByBaseEntityId() {
		addClient();
		String postfix = "";
		Client c = mhealthClientService.findByBaseEntityId("f67823b0-378e-4a35-93fc-bb00def74e2f", postfix);
		assertNotNull(c);
		assertEquals("xobili", c.getFirstName());
		assertEquals("233864-8", c.getIdentifier("ZEIR_ID"));
	}
	
	@Test
	public void testFindClientByClientId() {
		addClient();
		String postfix = "";
		Long clientId = mhealthClientService.findClientIdByBaseEntityId("f67823b0-378e-4a35-93fc-bb00def74e2f", postfix);
		Client c = mhealthClientService.findClientByClientId(clientId, postfix);
		assertNotNull(c);
		assertEquals("xobili", c.getFirstName());
		assertEquals("233864-8", c.getIdentifier("ZEIR_ID"));
	}
	
	@Test
	public void testFindByBaseEntityIds() {
		addClient();
		String postfix = "";
		List<String> baseEntitiIds = new ArrayList<>();
		baseEntitiIds.add("f67823b0-378e-4a35-93fc-bb00def74e2f");
		List<Client> c = mhealthClientService.findByBaseEntityIds(baseEntitiIds, postfix);
		assertNotNull(c);
		assertEquals(1, c.size());
	}
	
	public void addClient() {
		Client client = new Client("f67823b0-378e-4a35-93fc-bb00def74e2f").withBirthdate(new DateTime("2017-03-31"), true)
		        .withGender("Male").withFirstName("xobili").withLastName("mbangwa");
		List<Address> addresses = new ArrayList<Address>();
		Address address = new Address();
		address.setCountry("BBANGLADESH");
		address.setAddressType("usual_residence");
		address.setCityVillage("VILLAGE");
		address.setStateProvince("DIVISION");
		address.setCountyDistrict("DISTRICT");
		
		Map<String, String> addressFields = new HashMap<>();
		addressFields.put("address1", "wARD");
		addressFields.put("address2", "UPAZILA");
		addressFields.put("address3", "POURASAVA");
		addressFields.put("address8", "2345");
		address.setAddressFields(addressFields);
		addresses.add(address);
		Map<String, List<String>> relationships = new HashMap<>();
		List<String> relationalIds = new ArrayList<>();
		relationalIds.add("23333");
		relationships.put("family", relationalIds);
		client.setAddresses(addresses);
		client.setRelationships(relationships);
		client.setServerVersion(0);
		client.withIdentifier("ZEIR_ID", "233864-8").withAttribute("Home_Facility", "Linda");
		String district = "234";
		String postfix = "";
		String division = "233";
		String branch = "34";
		mhealthClientService.addOrUpdate(client, district, division, branch);
	}
	
}
