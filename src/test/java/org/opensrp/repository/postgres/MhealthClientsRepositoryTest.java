package org.opensrp.repository.postgres;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;
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
		Set<String> scripts = new HashSet<String>();
		scripts.add("client.sql");
		return scripts;
	}
	
	@Test
	public void testAdd() {
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
		String postfix = "_" + district;
		String division = "233";
		String branch = "34";
		mhealthClientsRepository.add(client, postfix, district, division, branch);
	}
	
}
