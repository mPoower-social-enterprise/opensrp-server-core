package org.opensrp.repository.postgres;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opensrp.domain.postgres.ClientMetadata;
import org.opensrp.repository.MhealthClientsRepository;
import org.smartregister.domain.Address;
import org.smartregister.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;

public class MhealthClientsRepositoryTest extends BaseRepositoryTest {
	
	@Autowired
	private MhealthClientsRepository mhealthClientsRepository;
	
	@BeforeClass
	public static void bootStrap() {
		tableNames = Arrays.asList("core.client", "core.client_metadata");
	}
	
	@Override
	protected Set<String> getDatabaseScripts() {
		return null;
	}
	
	@Test
	public void testShouldAdd() {
		Client client = addClient();
		String postfix = "";
		Client savedClient = mhealthClientsRepository.findByBaseEntityId(client.getBaseEntityId(), postfix);
		assertNotNull(savedClient);
		assertEquals("xobili", savedClient.getFirstName());
		assertEquals("233864-8", savedClient.getIdentifier("ZEIR_ID"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIIllegalArgumentExceptionTestDuplicateRecordAdd() {
		addClient();
		addClient();
		
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
	public void testShouldUpdate() {
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
		client.withIdentifier("ZEIR_ID", "233864-9").withAttribute("Home_Facility", "Linda");
		String district = "234";
		String postfix = "";
		String division = "233";
		String branch = "34";
		mhealthClientsRepository.add(client, postfix, district, division, branch);
		
		Client c = mhealthClientsRepository.findByBaseEntityId(client.getBaseEntityId(), postfix);
		c.setFirstName("update");
		mhealthClientsRepository.update(c, postfix, district, division, branch);
		Client updatedClient = mhealthClientsRepository.findByBaseEntityId(c.getBaseEntityId(), postfix);
		
		assertNotNull(updatedClient);
		assertEquals("update", updatedClient.getFirstName());
		assertEquals("233864-9", updatedClient.getIdentifier("ZEIR_ID"));
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIIllegalArgumentExceptionExceptionTestUpdate() {
		String district = "234";
		String postfix = "";
		String division = "233";
		String branch = "34";
		mhealthClientsRepository.update(null, postfix, district, division, branch);
	}
	
	@Test(expected = IllegalStateException.class)
	public void shouldThrowIllegalStateExceptionExceptionTestUpdate() {
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
		mhealthClientsRepository.update(client, postfix, district, division, branch);
	}
	
	@Test
	public void shouldTesFindByClientIdAndFindClientIdByBaseEntityId() {
		Client client = addClient();
		String postfix = "";
		Long id = mhealthClientsRepository.findClientIdByBaseEntityId(client.getBaseEntityId(), postfix);
		assertNotNull(id);
		ClientMetadata clientMetadata = mhealthClientsRepository.findByClientId(id, postfix);
		assertNotNull(clientMetadata);
		assertEquals(id, clientMetadata.getClientId());
	}
	
	@Test
	public void shouldTestFindClientByClientId() {
		Client client = addClient();
		String postfix = "";
		Long id = mhealthClientsRepository.findClientIdByBaseEntityId(client.getBaseEntityId(), postfix);
		Client finClient = mhealthClientsRepository.findClientByClientId(id, postfix);
		assertNotNull(finClient);
		assertEquals("233864-8", finClient.getIdentifier("ZEIR_ID"));
	}
	
	@Test
	public void shouldTestFindClientIdByBaseEntityId() {
		Client client = addClient();
		String postfix = "";
		Long id = mhealthClientsRepository.findClientIdByBaseEntityId(client.getBaseEntityId(), postfix);
		assertNotNull(id);
		
	}
	
	@Test
	public void shouldTestFindByBaseEntityIdss() {
		addClient();
		String postfix = "";
		List<String> baseEntitiIds = new ArrayList<>();
		baseEntitiIds.add("f67823b0-378e-4a35-93fc-bb00def74e2f");
		List<Client> clients = mhealthClientsRepository.findByBaseEntityIds(baseEntitiIds, postfix);
		assertNotNull(clients);
		assertEquals(1, clients.size());
		
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void shouldTestId() {
		mhealthClientsRepository.get("");
	}
	
	private Client addClient() {
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
		mhealthClientsRepository.add(client, postfix, district, division, branch);
		return client;
	}
}