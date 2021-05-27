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
import org.opensrp.domain.postgres.MhealthPractitionerLocation;
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
		MhealthPractitionerLocation location = new MhealthPractitionerLocation();
		location.setBranch(branch);
		location.setDistrict(district);
		location.setDivision(division);
		location.setPostFix(postfix);
		mhealthClientsRepository.add(null, location);
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
		MhealthPractitionerLocation location = new MhealthPractitionerLocation();
		location.setBranch(branch);
		location.setDistrict(district);
		location.setDivision(division);
		location.setPostFix(postfix);
		mhealthClientsRepository.add(client, location);
		
		Client c = mhealthClientsRepository.findByBaseEntityId(client.getBaseEntityId(), postfix);
		c.setFirstName("update");
		mhealthClientsRepository.update(c, location);
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
		MhealthPractitionerLocation location = new MhealthPractitionerLocation();
		location.setBranch(branch);
		location.setDistrict(district);
		location.setDivision(division);
		location.setPostFix(postfix);
		mhealthClientsRepository.update(null, location);
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
		MhealthPractitionerLocation location = new MhealthPractitionerLocation();
		location.setBranch(branch);
		location.setDistrict(district);
		location.setDivision(division);
		location.setPostFix(postfix);
		mhealthClientsRepository.update(client, location);
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
	public void shouldTestFindByBaseEntityIds() {
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
	
	@Test
	public void shouldTesFindByRelationshipId() {
		addClient();
		String postfix = "";
		List<Client> clients = mhealthClientsRepository.findByRelationshipId("23333", postfix);
		assertEquals(1, clients.size());
		assertEquals("233864-8", clients.get(0).getIdentifier("ZEIR_ID"));
	}
	
	@Test
	public void shouldReturnEmptyTesFindByRelationshipId() {
		String postfix = "";
		List<Client> clients = mhealthClientsRepository.findByRelationshipId("2333f3", postfix);
		assertEquals(0, clients.size());
	}
	
	@Test
	public void shouldGetMemberSearchClientForMigration() {
		addClient();
		String postfix = "";
		List<Client> clients = mhealthClientsRepository.searchClientForMigration(2345, "Male", 1, 7, "Member", postfix);
		Client client = clients.get(0);
		assertEquals(1, clients.size());
		assertEquals("xobili", client.getFirstName());
	}
	
	@Test
	public void shouldReturnEmptyGetMemberSearchClientForMigration() {
		addClient();
		String postfix = "";
		List<Client> clients = mhealthClientsRepository.searchClientForMigration(2345, "Male", 22, 34, "Member", postfix);
		assertEquals(0, clients.size());
	}
	
	@Test
	public void shouldGetHouseholdSearchClientForMigration() {
		addFamily();
		String postfix = "";
		List<Client> clients = mhealthClientsRepository.searchClientForMigration(2345, "", -1, -1, "HH", postfix);
		Client client = clients.get(0);
		assertEquals(1, clients.size());
		assertEquals("Max family", client.getFirstName());
		assertEquals("Family", client.getLastName());
	}
	
	@Test
	public void shouldReturnEmptyGetHouseholdSearchClientForMigration() {
		addFamily();
		String postfix = "";
		List<Client> clients = mhealthClientsRepository.searchClientForMigration(22220, "", -1, -1, "HH", postfix);
		assertEquals(0, clients.size());
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldReturnIllegalArgumentExceptionForGetHouseholdSearchClientForMigration() {
		addFamily();
		String postfix = "";
		mhealthClientsRepository.searchClientForMigration(0, "", -1, -1, "HH", postfix);
	}
	
	private Client addClient() {
		Client client = new Client("f67823b0-378e-4a35-93fc-bb00def74e2f").withBirthdate(new DateTime("2017-03-31"), true)
		        .withGender("Male").withFirstName("xobili").withLastName(null);
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
		MhealthPractitionerLocation location = new MhealthPractitionerLocation();
		location.setBranch(branch);
		location.setDistrict(district);
		location.setDivision(division);
		location.setPostFix(postfix);
		mhealthClientsRepository.add(client, location);
		return client;
	}
	
	private Client addFamily() {
		Client client = new Client("f67823b0-378e-4a35-93fc-bb00def74e2f").withBirthdate(new DateTime("2017-03-31"), true)
		        .withGender("Male").withFirstName("Max family").withLastName("Family");
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
		MhealthPractitionerLocation location = new MhealthPractitionerLocation();
		location.setBranch(branch);
		location.setDistrict(district);
		location.setDivision(division);
		location.setPostFix(postfix);
		mhealthClientsRepository.add(client, location);
		return client;
	}
}
