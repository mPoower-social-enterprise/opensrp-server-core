package org.opensrp.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opensrp.domain.postgres.MhealthEventMetadata;
import org.opensrp.domain.postgres.MhealthMigration;
import org.opensrp.domain.postgres.MhealthPractitionerLocation;
import org.opensrp.repository.MhealthMigrationRepository;
import org.opensrp.repository.postgres.MhealthBaseRepositoryTest;
import org.smartregister.domain.Address;
import org.smartregister.domain.Client;
import org.smartregister.domain.Event;
import org.smartregister.domain.Obs;
import org.smartregister.utils.DateTimeTypeConverter;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class MhealthMigrationServiceTest extends MhealthBaseRepositoryTest {
	
	@Autowired
	private MhealthMigrationRepository mhealthMigrationRepository;
	
	@Autowired
	private MhealthEventService eventService;
	
	@Autowired
	private MhealthClientService clientService;
	
	private MhealthMigrationService mhealthMigrationService;
	
	private final static String memberPayload = "{\"clients\": \"[{\\\"birthdate\\\":\\\"1970-01-01T06:00:00.000Z\\\",\\\"birthdateApprox\\\":false,\\\"deathdateApprox\\\":false,\\\"firstName\\\":\\\"Rasheda\\\",\\\"gender\\\":\\\"M\\\",\\\"baseEntityId\\\":\\\"a6c836ea-800d-49ea-8565-0d7c58fdcb8c\\\",\\\"identifiers\\\":{\\\"opensrp_id\\\":\\\"608844006730700016010001\\\"},\\\"addresses\\\":[{\\\"addressType\\\":\\\"usual_residence\\\",\\\"addressFields\\\":{\\\"address1\\\":\\\"BHOLABAA\\\",\\\"address2\\\":\\\"RUPGANJA\\\",\\\"address3\\\":\\\"NOT POURASABHAA\\\",\\\"address8\\\":\\\"136961\\\"},\\\"countyDistrict\\\":\\\"NARAYANGANJA\\\",\\\"cityVillage\\\":\\\"TORAILA\\\",\\\"stateProvince\\\":\\\"DHAKAA\\\",\\\"country\\\":\\\"BANGLADESHA\\\"}],\\\"attributes\\\":{\\\"age\\\":\\\"35\\\",\\\"DOB_known\\\":\\\"no\\\",\\\"Blood_Group\\\":\\\"A+\\\",\\\"Mobile_Number\\\":\\\"0\\\",\\\"Marital_Status\\\":\\\"Married\\\",\\\"Relation_with_HOH\\\":\\\"Guest\\\",\\\"difficulty_seeing_hearing\\\":\\\"yes_little_difficulties\\\",\\\"difficulty_walking_up_down\\\":\\\"yes_little_difficulties\\\",\\\"trouble_remembering_concentrating\\\":\\\"no_difficulties\\\"},\\\"clientApplicationVersion\\\":31,\\\"clientApplicationVersionName\\\":\\\"1.3.6_DEV\\\",\\\"clientDatabaseVersion\\\":31,\\\"dateCreated\\\":\\\"2021-01-04T16:35:04.023Z\\\",\\\"type\\\":\\\"Client\\\",\\\"relationships\\\":{\\\"mother\\\":[\\\"\\\"],\\\"family\\\":[\\\"05b5acd9-d375-4892-aae3-2cefa17c7538\\\",\\\"05b5acd9-d375-4892-aae3-2cefa17c7538\\\"]}}]\"}";
	
	private final static String familyPayload = "{\"clients\": \"[{\\\"birthdate\\\":\\\"1970-01-01T06:00:00.000Z\\\",\\\"birthdateApprox\\\":false,\\\"deathdateApprox\\\":false,\\\"firstName\\\":\\\"Rashed\\\",\\\"lastName\\\":\\\"Family\\\",\\\"gender\\\":\\\"M\\\",\\\"baseEntityId\\\":\\\"0511acf9-6be8-4a98-b4f9-f8a5cfa704bb\\\",\\\"identifiers\\\":{\\\"opensrp_id\\\":\\\"50884400673070001601\\\"},\\\"addresses\\\":[{\\\"addressType\\\":\\\"usual_residence\\\",\\\"addressFields\\\":{\\\"address1\\\":\\\"BHOLABAQ\\\",\\\"address2\\\":\\\"RUPGANJq\\\",\\\"address3\\\":\\\"NOT POURASABHAq\\\",\\\"address8\\\":\\\"136961\\\"},\\\"countyDistrict\\\":\\\"NARAYANGANJq\\\",\\\"cityVillage\\\":\\\"Migrated Village\\\",\\\"stateProvince\\\":\\\"DHAKAq\\\",\\\"country\\\":\\\"BANGLADESHq\\\"}],\\\"attributes\\\":{\\\"Cluster\\\":\\\"1st_Cluster\\\",\\\"HH_Type\\\":\\\"BRAC VO\\\",\\\"SS_Name\\\":\\\"Forida(SS-1)\\\",\\\"module_id\\\":\\\"TRAINING\\\",\\\"serial_no\\\":\\\"H369\\\",\\\"village_id\\\":\\\"136962\\\",\\\"Has_Latrine\\\":\\\"No\\\",\\\"HOH_Phone_Number\\\":\\\"01471221551\\\",\\\"Number_of_HH_Member\\\":\\\"5\\\"},\\\"clientApplicationVersion\\\":31,\\\"clientApplicationVersionName\\\":\\\"1.3.6_DEV\\\",\\\"clientDatabaseVersion\\\":31,\\\"dateCreated\\\":\\\"2021-01-04T16:35:04.023Z\\\",\\\"type\\\":\\\"Client\\\",\\\"relationships\\\":{\\\"mother\\\":[\\\"\\\"],\\\"family_head\\\":[\\\"0511acf9-6be8-4a98-b4f9-f8a5cfa704bb\\\",\\\"0511acf9-6be8-4a98-b4f9-f8a5cfa704bb\\\"]}}]\"}";
	
	private static int increment = 1;
	
	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	        .registerTypeAdapter(DateTime.class, new DateTimeTypeConverter()).create();
	
	@BeforeClass
	public static void bootStrap() {
		tableNames = Arrays.asList("core.migration", "core.event", "core.event_metadata", "core.client",
		    "core.client_metadata");
	}
	
	@Before
	public void setUpPostgresService() {
		mhealthMigrationService = new MhealthMigrationService(mhealthMigrationRepository, clientService, eventService);
	}
	
	@Override
	protected Set<String> getDatabaseScripts() {
		Set<String> scripts = new HashSet<String>();
		scripts.add("add_column.sql");
		return scripts;
	}
	
	@Override
	protected Set<String> getDatabaseScriptsAfterExecute() {
		Set<String> scripts = new HashSet<String>();
		increment = increment + 1;
		
		if (increment == 37) {
			scripts.add("drop_column.sql");
			return scripts;
		}
		
		return scripts;
	}
	
	@Test
	public void testAddMigration() {
		MhealthMigration addMemberMigration = addMemberMigration();
		MhealthMigration addFamilyMigration = addFamilyMigration();
		int insertMember = mhealthMigrationService.addMigration(addMemberMigration);
		int insertFamily = mhealthMigrationService.addMigration(addFamilyMigration);
		assertEquals(1, insertMember);
		assertEquals(1, insertFamily);
		
	}
	
	@Test
	public void testGetMigratedList() {
		MhealthMigration addFamilyMigration = addFamilyMigration();
		addFamilyMigration.setStatus("ACCEPT");
		mhealthMigrationService.addMigration(addFamilyMigration);
		List<String> expectedMigratedList = mhealthMigrationService.getMigratedList("01313047271", "HH", 0l);
		List<String> actualMigratedList = new ArrayList<>();
		actualMigratedList.add("8a661876-111a-4976-9a39-2ba0e1e4d776");
		assertEquals(actualMigratedList, expectedMigratedList);
	}
	
	@Test
	public void testShouldReturnEmptyGetMigratedList() {
		MhealthMigration addFamilyMigration = addFamilyMigration();
		addFamilyMigration.setStatus("ACCEPT");
		mhealthMigrationService.addMigration(addFamilyMigration);
		List<String> expectedMigratedList = mhealthMigrationService.getMigratedList("013130472713", "HH", 0l);
		assertEquals(0, expectedMigratedList.size());
	}
	
	@Test
	public void testGetRejectedList() {
		MhealthMigration addFamilyMigration = addFamilyMigration();
		addFamilyMigration.setStatus("REJECT");
		mhealthMigrationService.addMigration(addFamilyMigration);
		List<String> expectedRejectedList = mhealthMigrationService.getRejectedList("01313047103", "HH", 0l);
		List<String> actualRejectedList = new ArrayList<>();
		actualRejectedList.add("8a661876-111a-4976-9a39-2ba0e1e4d776");
		assertEquals(actualRejectedList, expectedRejectedList);
	}
	
	@Test
	public void testShouldReturnEmptyGetRejectedList() {
		MhealthMigration addFamilyMigration = addFamilyMigration();
		addFamilyMigration.setStatus("REJECT");
		mhealthMigrationService.addMigration(addFamilyMigration);
		List<String> expectedRejectedList = mhealthMigrationService.getRejectedList("013130471063", "HH", 0l);
		assertEquals(0, expectedRejectedList.size());
	}
	
	@Test
	public void testFindMigrationById() {
		MhealthMigration addFamilyMigration = addFamilyMigration();
		mhealthMigrationService.addMigration(addFamilyMigration);
		MhealthMigration getMigrationByBaseEntityId = mhealthMigrationService
		        .findFirstMigrationBybaseEntityId("8a661876-111a-4976-9a39-2ba0e1e4d776");
		MhealthMigration getMigrationById = mhealthMigrationService.findMigrationById(getMigrationByBaseEntityId.getId());
		assertEquals("8a661876-111a-4976-9a39-2ba0e1e4d776", getMigrationById.getBaseEntityId());
		
	}
	
	@Test
	public void testFindMigrationByIdRelationId() {
		MhealthMigration addFamilyMigration = addMemberMigration();
		addFamilyMigration.setStatus("ACCEPT");
		mhealthMigrationService.addMigration(addFamilyMigration);
		List<MhealthMigration> findByRelationshipid = mhealthMigrationService
		        .findMigrationByIdRelationId("8a661876-111a-4976-9a39-2ba0e1e4d776", "ACCEPT");
		assertEquals(1, findByRelationshipid.size());
		assertEquals("8a661876-111a-4976-9a39-2ba0e1e4d776", findByRelationshipid.get(0).getRelationalIdIn());
	}
	
	@Test
	public void testUpdateMigration() {
		MhealthMigration addFamilyMigration = addFamilyMigration();
		mhealthMigrationService.addMigration(addFamilyMigration);
		assertEquals("PENDING", addFamilyMigration.getStatus());
		addFamilyMigration.setStatus("REJECT");
		int updated = mhealthMigrationService.updateMigration(addFamilyMigration, "8a661876-111a-4976-9a39-2ba0e1e4d776");
		assertEquals(1, updated);
		MhealthMigration getMigrationByBaseEntityId = mhealthMigrationService
		        .findFirstMigrationBybaseEntityId("8a661876-111a-4976-9a39-2ba0e1e4d776");
		assertEquals("REJECT", getMigrationByBaseEntityId.getStatus());
	}
	
	@Test
	public void testUpdateMigrationStatusById() {
		MhealthMigration addFamilyMigration = addFamilyMigration();
		mhealthMigrationService.addMigration(addFamilyMigration);
		assertEquals("PENDING", addFamilyMigration.getStatus());
		MhealthMigration getMigrationByBaseEntityId = mhealthMigrationService
		        .findFirstMigrationBybaseEntityId("8a661876-111a-4976-9a39-2ba0e1e4d776");
		int update = mhealthMigrationService.updateMigrationStatusById(getMigrationByBaseEntityId.getId(), "REJECT");
		assertEquals(1, update);
		MhealthMigration afterUpdated = mhealthMigrationService
		        .findFirstMigrationBybaseEntityId("8a661876-111a-4976-9a39-2ba0e1e4d776");
		assertEquals("REJECT", afterUpdated.getStatus());
	}
	
	@Test
	public void testUpdateMigrationStatusByRelationalId() {
		MhealthMigration addMemberMigration = addMemberMigration();
		MhealthMigration addFamilyMigration = addFamilyMigration();
		mhealthMigrationService.addMigration(addMemberMigration);
		mhealthMigrationService.addMigration(addFamilyMigration);
		
		int update = mhealthMigrationService.updateMigrationStatusByRelationalId("8a661876-111a-4976-9a39-2ba0e1e4d776",
		    "ACCEPT");
		MhealthMigration afterUpdated = mhealthMigrationService
		        .findFirstMigrationBybaseEntityId("f3f3c8b3-3566-4509-b5dc-b058d4313380");
		assertEquals(1, update);
		assertEquals("ACCEPT", afterUpdated.getStatus());
	}
	
	@Test
	public void testFindFirstMigrationBybaseEntityId() {
		MhealthMigration addMemberMigration = addMemberMigration();
		MhealthMigration addMemberMigration1 = addMemberMigration();
		addMemberMigration1.setSKOut("01313047272");
		addMemberMigration1.setSKIn("01313047104");
		mhealthMigrationService.addMigration(addMemberMigration);
		mhealthMigrationService.addMigration(addMemberMigration1);
		
		MhealthMigration firstMigration = mhealthMigrationService
		        .findFirstMigrationBybaseEntityId("f3f3c8b3-3566-4509-b5dc-b058d4313380");
		assertEquals("01313047271", firstMigration.getSKOut());
	}
	
	@Test
	public void testShouldReturnNUllFindFirstMigrationBybaseEntityId() {
		MhealthMigration addMemberMigration = addMemberMigration();
		MhealthMigration addMemberMigration1 = addMemberMigration();
		addMemberMigration1.setSKOut("01313047272");
		addMemberMigration1.setSKIn("01313047104");
		mhealthMigrationService.addMigration(addMemberMigration);
		mhealthMigrationService.addMigration(addMemberMigration1);
		
		MhealthMigration firstMigration = mhealthMigrationService
		        .findFirstMigrationBybaseEntityId("f3f3c8b3-3566-4509-b5dc-b058d4313381");
		assertNull(firstMigration);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testMigrateMember() {
		addMember();
		addMemberEvent();
		addFamily1();
		addFamilyEvent1();
		addFamily2();
		addFamilyEvent2();
		JSONObject syncData = new JSONObject(memberPayload);
		
		ArrayList<Client> clients = new ArrayList<Client>();
		if (syncData.has("clients")) {
			
			clients = (ArrayList<Client>) gson.fromJson(syncData.getString("clients"),
			    new TypeToken<ArrayList<Client>>() {}.getType());
			
		}
		
		MhealthPractitionerLocation inUserLocation = new MhealthPractitionerLocation();
		inUserLocation.setBranch("122");
		inUserLocation.setDistrict("10425");
		inUserLocation.setDivision("2345");
		inUserLocation.setPostFix("");
		inUserLocation.setUsername("testsk1");
		MhealthPractitionerLocation outUserLocation = new MhealthPractitionerLocation();
		outUserLocation.setBranch("34");
		outUserLocation.setDistrict("10432");
		outUserLocation.setDivision("23455");
		outUserLocation.setPostFix("");
		
		Client inclient = clients.get(0);
		String baseEntityId = inclient.getBaseEntityId();
		MhealthEventMetadata mhealthEventMetadata = eventService.findFirstEventMetadata(baseEntityId,
		    outUserLocation.getPostFix());
		MhealthMigration existingMigration = mhealthMigrationRepository.findFirstMigrationBybaseEntityId(baseEntityId);
		String outProvider = "";
		if (existingMigration != null) {
			outProvider = existingMigration.getSKIn();
		} else {
			outProvider = mhealthEventMetadata.getProviderId();
		}
		
		outUserLocation.setUsername(outProvider);
		mhealthMigrationService.migrate(inclient, syncData, inUserLocation, outUserLocation, "Member");
		
		MhealthMigration migration = mhealthMigrationService
		        .findFirstMigrationBybaseEntityId("a6c836ea-800d-49ea-8565-0d7c58fdcb8c");
		
		assertEquals("testsk1", migration.getSKIn());
		assertEquals("testsk", migration.getSKOut());
		
		assertEquals("NARAYANGANJA", migration.getDistrictIn());
		assertEquals("NARAYANGANJ", migration.getDistrictOut());
		
		assertEquals("608844006730700016010001", migration.getMemberIDIn());
		assertEquals("508844006730700016010001", migration.getMemberIDOut());
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testMigrateFamily() {
		addMember();
		addMemberEvent();
		addFamily1();
		addFamilyEvent1();
		addFamily2();
		addFamilyEvent2();
		JSONObject syncData = new JSONObject(familyPayload);
		
		ArrayList<Client> clients = new ArrayList<Client>();
		if (syncData.has("clients")) {
			
			clients = (ArrayList<Client>) gson.fromJson(syncData.getString("clients"),
			    new TypeToken<ArrayList<Client>>() {}.getType());
			
		}
		
		MhealthPractitionerLocation inUserLocation = new MhealthPractitionerLocation();
		inUserLocation.setBranch("56");
		inUserLocation.setDistrict("10432");
		inUserLocation.setDivision("2345");
		inUserLocation.setPostFix("");
		inUserLocation.setUsername("testsk1");
		MhealthPractitionerLocation outUserLocation = new MhealthPractitionerLocation();
		outUserLocation.setBranch("34");
		outUserLocation.setDistrict("10425");
		outUserLocation.setDivision("234555");
		outUserLocation.setPostFix("");
		
		Client inclient = clients.get(0);
		String baseEntityId = inclient.getBaseEntityId();
		MhealthEventMetadata mhealthEventMetadata = eventService.findFirstEventMetadata(baseEntityId,
		    outUserLocation.getPostFix());
		MhealthMigration existingMigration = mhealthMigrationRepository.findFirstMigrationBybaseEntityId(baseEntityId);
		String outProvider = "";
		if (existingMigration != null) {
			outProvider = existingMigration.getSKIn();
		} else {
			outProvider = mhealthEventMetadata.getProviderId();
		}
		
		outUserLocation.setUsername(outProvider);
		mhealthMigrationService.migrate(inclient, syncData, inUserLocation, outUserLocation, "HH");
		MhealthMigration migratedFamily = mhealthMigrationService
		        .findFirstMigrationBybaseEntityId("0511acf9-6be8-4a98-b4f9-f8a5cfa704bb");
		
		assertEquals("testsk1", migratedFamily.getSKIn());
		assertEquals("testsk", migratedFamily.getSKOut());
		assertEquals("NARAYANGANJq", migratedFamily.getDistrictIn());
		assertEquals("NARAYANGANJ", migratedFamily.getDistrictOut());
		assertEquals("50884400673070001601", migratedFamily.getMemberIDIn());
		assertEquals("50884400673070001601", migratedFamily.getMemberIDOut());
		assertEquals("56", migratedFamily.getBranchIDIn());
		assertEquals("34", migratedFamily.getBranchIDOut());
		
		MhealthMigration migratedMember = mhealthMigrationService
		        .findFirstMigrationBybaseEntityId("a6c836ea-800d-49ea-8565-0d7c58fdcb8c");
		
		assertEquals("testsk1", migratedMember.getSKIn());
		assertEquals("testsk", migratedMember.getSKOut());
		assertEquals("NARAYANGANJq", migratedMember.getDistrictIn());
		assertEquals("NARAYANGANJ", migratedMember.getDistrictOut());
		assertEquals("508844006730700016010001", migratedMember.getMemberIDIn());
		assertEquals("508844006730700016010001", migratedMember.getMemberIDOut());
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testRejectMemberMigration() {
		addMember();
		addMemberEvent();
		addFamily1();
		addFamilyEvent1();
		addFamily2();
		addFamilyEvent2();
		JSONObject syncData = new JSONObject(memberPayload);
		
		ArrayList<Client> clients = new ArrayList<Client>();
		if (syncData.has("clients")) {
			
			clients = (ArrayList<Client>) gson.fromJson(syncData.getString("clients"),
			    new TypeToken<ArrayList<Client>>() {}.getType());
			
		}
		
		MhealthPractitionerLocation inUserLocation = new MhealthPractitionerLocation();
		inUserLocation.setBranch("122");
		inUserLocation.setDistrict("10425");
		inUserLocation.setDivision("2345");
		inUserLocation.setPostFix("");
		inUserLocation.setUsername("testsk1");
		MhealthPractitionerLocation outUserLocation = new MhealthPractitionerLocation();
		outUserLocation.setBranch("34");
		outUserLocation.setDistrict("10432");
		outUserLocation.setDivision("23455");
		outUserLocation.setPostFix("");
		
		Client inclient = clients.get(0);
		String baseEntityId = inclient.getBaseEntityId();
		MhealthEventMetadata mhealthEventMetadata = eventService.findFirstEventMetadata(baseEntityId,
		    outUserLocation.getPostFix());
		MhealthMigration existingMigration = mhealthMigrationRepository.findFirstMigrationBybaseEntityId(baseEntityId);
		String outProvider = "";
		if (existingMigration != null) {
			outProvider = existingMigration.getSKIn();
		} else {
			outProvider = mhealthEventMetadata.getProviderId();
		}
		
		outUserLocation.setUsername(outProvider);
		mhealthMigrationService.migrate(inclient, syncData, inUserLocation, outUserLocation, "Member");
		
		MhealthMigration migration = mhealthMigrationService
		        .findFirstMigrationBybaseEntityId("a6c836ea-800d-49ea-8565-0d7c58fdcb8c");
		
		mhealthMigrationService.acceptOrRejectMigration(migration.getId(), "", "Member", "REJECT");
		MhealthMigration rejectedMigration = mhealthMigrationService
		        .findFirstMigrationBybaseEntityId("a6c836ea-800d-49ea-8565-0d7c58fdcb8c");
		assertEquals("REJECT", rejectedMigration.getStatus());
		Client c = clientService.findByBaseEntityId(baseEntityId, "");
		Map<String, List<String>> relationships = c.getRelationships();
		String relationalId = "";
		if (relationships.containsKey("family")) {
			relationalId = relationships.get("family").get(0);
		} else if (relationships.containsKey("family_head")) {
			relationalId = relationships.get("family_head").get(0);
		}
		Address address = c.getAddress("usual_residence");
		assertEquals("NARAYANGANJ", address.getCountyDistrict());
		assertEquals("DHAKA", address.getStateProvince());
		assertEquals("TORAIL", address.getCityVillage());
		assertEquals("0511acf9-6be8-4a98-b4f9-f8a5cfa704bb", relationalId);
		assertEquals("Rasheda", c.getFirstName());
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testAcceptMemberMigration() {
		addMember();
		addMemberEvent();
		addFamily1();
		addFamilyEvent1();
		addFamily2();
		addFamilyEvent2();
		JSONObject syncData = new JSONObject(memberPayload);
		
		ArrayList<Client> clients = new ArrayList<Client>();
		if (syncData.has("clients")) {
			
			clients = (ArrayList<Client>) gson.fromJson(syncData.getString("clients"),
			    new TypeToken<ArrayList<Client>>() {}.getType());
			
		}
		
		MhealthPractitionerLocation inUserLocation = new MhealthPractitionerLocation();
		inUserLocation.setBranch("122");
		inUserLocation.setDistrict("10425");
		inUserLocation.setDivision("2345");
		inUserLocation.setPostFix("");
		inUserLocation.setUsername("testsk1");
		MhealthPractitionerLocation outUserLocation = new MhealthPractitionerLocation();
		outUserLocation.setBranch("34");
		outUserLocation.setDistrict("10432");
		outUserLocation.setDivision("23455");
		outUserLocation.setPostFix("");
		
		Client inclient = clients.get(0);
		String baseEntityId = inclient.getBaseEntityId();
		MhealthEventMetadata mhealthEventMetadata = eventService.findFirstEventMetadata(baseEntityId,
		    outUserLocation.getPostFix());
		MhealthMigration existingMigration = mhealthMigrationRepository.findFirstMigrationBybaseEntityId(baseEntityId);
		String outProvider = "";
		if (existingMigration != null) {
			outProvider = existingMigration.getSKIn();
		} else {
			outProvider = mhealthEventMetadata.getProviderId();
		}
		
		outUserLocation.setUsername(outProvider);
		mhealthMigrationService.migrate(inclient, syncData, inUserLocation, outUserLocation, "Member");
		
		MhealthMigration migration = mhealthMigrationService
		        .findFirstMigrationBybaseEntityId("a6c836ea-800d-49ea-8565-0d7c58fdcb8c");
		
		mhealthMigrationService.acceptOrRejectMigration(migration.getId(), "", "Member", "ACCEPT");
		MhealthMigration rejectedMigration = mhealthMigrationService
		        .findFirstMigrationBybaseEntityId("a6c836ea-800d-49ea-8565-0d7c58fdcb8c");
		assertEquals("ACCEPT", rejectedMigration.getStatus());
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testRejectFamilyMigration() {
		addMember();
		addMemberEvent();
		addFamily1();
		addFamilyEvent1();
		addFamily2();
		addFamilyEvent2();
		JSONObject syncData = new JSONObject(familyPayload);
		
		ArrayList<Client> clients = new ArrayList<Client>();
		if (syncData.has("clients")) {
			
			clients = (ArrayList<Client>) gson.fromJson(syncData.getString("clients"),
			    new TypeToken<ArrayList<Client>>() {}.getType());
			
		}
		
		MhealthPractitionerLocation inUserLocation = new MhealthPractitionerLocation();
		inUserLocation.setBranch("56");
		inUserLocation.setDistrict("10432");
		inUserLocation.setDivision("2345");
		inUserLocation.setPostFix("");
		inUserLocation.setUsername("testsk1");
		MhealthPractitionerLocation outUserLocation = new MhealthPractitionerLocation();
		outUserLocation.setBranch("34");
		outUserLocation.setDistrict("10425");
		outUserLocation.setDivision("234555");
		outUserLocation.setPostFix("");
		
		Client inclient = clients.get(0);
		String baseEntityId = inclient.getBaseEntityId();
		MhealthEventMetadata mhealthEventMetadata = eventService.findFirstEventMetadata(baseEntityId,
		    outUserLocation.getPostFix());
		MhealthMigration existingMigration = mhealthMigrationRepository.findFirstMigrationBybaseEntityId(baseEntityId);
		String outProvider = "";
		if (existingMigration != null) {
			outProvider = existingMigration.getSKIn();
		} else {
			outProvider = mhealthEventMetadata.getProviderId();
		}
		
		outUserLocation.setUsername(outProvider);
		mhealthMigrationService.migrate(inclient, syncData, inUserLocation, outUserLocation, "HH");
		MhealthMigration migratedFamily = mhealthMigrationService
		        .findFirstMigrationBybaseEntityId("0511acf9-6be8-4a98-b4f9-f8a5cfa704bb");
		
		mhealthMigrationService.acceptOrRejectMigration(migratedFamily.getId(), migratedFamily.getBaseEntityId(), "HH",
		    "REJECT");
		// for household
		Client household = clientService.findByBaseEntityId(baseEntityId, "");
		Map<String, List<String>> householdRelationships = household.getRelationships();
		String householdRelationalId = "";
		if (householdRelationships.containsKey("family")) {
			householdRelationalId = householdRelationships.get("family").get(0);
		} else if (householdRelationships.containsKey("family_head")) {
			householdRelationalId = householdRelationships.get("family_head").get(0);
		}
		MhealthMigration rejectedHouseholdMigration = mhealthMigrationService
		        .findFirstMigrationBybaseEntityId(household.getBaseEntityId());
		assertEquals("REJECT", rejectedHouseholdMigration.getStatus());
		Address address = household.getAddress("usual_residence");
		assertEquals("NARAYANGANJ", address.getCountyDistrict());
		assertEquals("DHAKA", address.getStateProvince());
		assertEquals("TORAIL", address.getCityVillage());
		assertEquals("0511acf9-6be8-4a98-b4f9-f8a5cfa704bb", householdRelationalId);
		assertEquals("Rashed", household.getFirstName());
		
		//for household member 
		List<Client> members = clientService.findByRelationshipId(baseEntityId, "");
		Client member = members.get(0);
		MhealthMigration rejectedMigration = mhealthMigrationService
		        .findFirstMigrationBybaseEntityId("a6c836ea-800d-49ea-8565-0d7c58fdcb8c");
		assertEquals("REJECT", rejectedMigration.getStatus());
		
		Map<String, List<String>> relationships = member.getRelationships();
		String relationalId = "";
		if (relationships.containsKey("family")) {
			relationalId = relationships.get("family").get(0);
		} else if (relationships.containsKey("family_head")) {
			relationalId = relationships.get("family_head").get(0);
		}
		Address householdAddress = member.getAddress("usual_residence");
		assertEquals("NARAYANGANJ", householdAddress.getCountyDistrict());
		assertEquals("DHAKA", householdAddress.getStateProvince());
		assertEquals("TORAIL", householdAddress.getCityVillage());
		assertEquals("0511acf9-6be8-4a98-b4f9-f8a5cfa704bb", relationalId);
		assertEquals("Rasheda", member.getFirstName());
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testAcceptFamilyMigration() {
		addMember();
		addMemberEvent();
		addFamily1();
		addFamilyEvent1();
		addFamily2();
		addFamilyEvent2();
		JSONObject syncData = new JSONObject(familyPayload);
		
		ArrayList<Client> clients = new ArrayList<Client>();
		if (syncData.has("clients")) {
			
			clients = (ArrayList<Client>) gson.fromJson(syncData.getString("clients"),
			    new TypeToken<ArrayList<Client>>() {}.getType());
			
		}
		
		MhealthPractitionerLocation inUserLocation = new MhealthPractitionerLocation();
		inUserLocation.setBranch("56");
		inUserLocation.setDistrict("10432");
		inUserLocation.setDivision("2345");
		inUserLocation.setPostFix("");
		inUserLocation.setUsername("testsk1");
		MhealthPractitionerLocation outUserLocation = new MhealthPractitionerLocation();
		outUserLocation.setBranch("34");
		outUserLocation.setDistrict("10425");
		outUserLocation.setDivision("234555");
		outUserLocation.setPostFix("");
		
		Client inclient = clients.get(0);
		String baseEntityId = inclient.getBaseEntityId();
		MhealthEventMetadata mhealthEventMetadata = eventService.findFirstEventMetadata(baseEntityId,
		    outUserLocation.getPostFix());
		MhealthMigration existingMigration = mhealthMigrationRepository.findFirstMigrationBybaseEntityId(baseEntityId);
		String outProvider = "";
		if (existingMigration != null) {
			outProvider = existingMigration.getSKIn();
		} else {
			outProvider = mhealthEventMetadata.getProviderId();
		}
		
		outUserLocation.setUsername(outProvider);
		mhealthMigrationService.migrate(inclient, syncData, inUserLocation, outUserLocation, "HH");
		MhealthMigration migratedFamily = mhealthMigrationService
		        .findFirstMigrationBybaseEntityId("0511acf9-6be8-4a98-b4f9-f8a5cfa704bb");
		
		mhealthMigrationService.acceptOrRejectMigration(migratedFamily.getId(), migratedFamily.getBaseEntityId(), "HH",
		    "ACCEPT");
		// for household
		
		MhealthMigration rejectedHouseholdMigration = mhealthMigrationService
		        .findFirstMigrationBybaseEntityId(migratedFamily.getBaseEntityId());
		assertEquals("ACCEPT", rejectedHouseholdMigration.getStatus());
		
		//for household member 
		
		MhealthMigration rejectedMigration = mhealthMigrationService
		        .findFirstMigrationBybaseEntityId("a6c836ea-800d-49ea-8565-0d7c58fdcb8c");
		assertEquals("ACCEPT", rejectedMigration.getStatus());
		
	}
	
	public MhealthMigration addMemberMigration() {
		MhealthMigration mhealthMigration = new MhealthMigration();
		mhealthMigration.setTimestamp(1612686348398l);
		mhealthMigration.setDivisionIdOut("9266");
		mhealthMigration.setDivisionIdIn("9266");
		mhealthMigration.setMotherId("");
		mhealthMigration.setDistrictIdOut("_88279");
		mhealthMigration.setDistrictIdIn("_9267");
		mhealthMigration.setBaseEntityId("f3f3c8b3-3566-4509-b5dc-b058d4313380");
		mhealthMigration.setVillageOut("CHANDANA(BOUBAZAR)-13");
		mhealthMigration.setVillageIn("KARAIL-SHAHEB ALIR BARI (KHAMAR BARI)");
		mhealthMigration.setVillageIDOut("134874");
		mhealthMigration.setVillageIDIn("9288");
		mhealthMigration.setUpazilaOut("GAZIPUR CITY CORPORATION");
		mhealthMigration.setUpazilaIn("DHAKA NORTH CITY CORPORATION");
		mhealthMigration.setUnionOut("WARD NO. 17");
		mhealthMigration.setUnionIn("WARD NO. 19 (PART)");
		mhealthMigration.setStatus("PENDING");
		mhealthMigration.setRelationalIdOut("8a661876-111a-4976-9a39-2ba0e1e4d776");
		mhealthMigration.setRelationalIdIn("8a661876-111a-4976-9a39-2ba0e1e4d776");
		mhealthMigration.setRelationWithHHOut("Father");
		mhealthMigration.setRelationWithHHIn("Father");
		mhealthMigration.setPourasavaOut("NOT POURASABHA");
		mhealthMigration.setPourasavaIn("NOT POURASABHA");
		mhealthMigration.setNumberOfMemberOut("6");
		mhealthMigration.setNumberOfMemberIn("5");
		mhealthMigration.setMigrationDate(new DateTime().toDate());
		mhealthMigration.setMemberType("Member");
		mhealthMigration.setMemberName("Baby");
		mhealthMigration.setMemberIDOut("30333300017030131320104");
		mhealthMigration.setMemberIDIn("30333300017030131320104");
		mhealthMigration.setIsMember("no");
		mhealthMigration.setDob(new DateTime().toDate());
		mhealthMigration.setDivisionOut("DHAKA");
		mhealthMigration.setDivisionIn("DHAKA");
		mhealthMigration.setDistrictOut("GAZIPUR");
		mhealthMigration.setDistrictIn("DHAKA");
		mhealthMigration.setBranchIDIn("8");
		mhealthMigration.setSKOut("01313047271");
		mhealthMigration.setSKIn("01313047103");
		mhealthMigration.setHHNameOut("Kitta");
		mhealthMigration.setHHNameIn("Kitta");
		mhealthMigration.setHHContactOut("01585785805");
		mhealthMigration.setHHContactIn("01585785805");
		mhealthMigration.setBranchIDOut("2982");
		return mhealthMigration;
	}
	
	public MhealthMigration addFamilyMigration() {
		MhealthMigration mhealthMigration = new MhealthMigration();
		mhealthMigration.setTimestamp(1612686348398l);
		mhealthMigration.setDivisionIdOut("9266");
		mhealthMigration.setDivisionIdIn("9266");
		mhealthMigration.setMotherId("");
		mhealthMigration.setDistrictIdOut("_88279");
		mhealthMigration.setDistrictIdIn("_9267");
		mhealthMigration.setBaseEntityId("8a661876-111a-4976-9a39-2ba0e1e4d776");
		mhealthMigration.setVillageOut("CHANDANA(BOUBAZAR)-13");
		mhealthMigration.setVillageIn("KARAIL-SHAHEB ALIR BARI (KHAMAR BARI)");
		mhealthMigration.setVillageIDOut("134874");
		mhealthMigration.setVillageIDIn("9288");
		mhealthMigration.setUpazilaOut("GAZIPUR CITY CORPORATION");
		mhealthMigration.setUpazilaIn("DHAKA NORTH CITY CORPORATION");
		mhealthMigration.setUnionOut("WARD NO. 17");
		mhealthMigration.setUnionIn("WARD NO. 19 (PART)");
		mhealthMigration.setStatus("PENDING");
		mhealthMigration.setRelationalIdOut("8a661876-111a-4976-9a39-2ba0e1e4d776");
		mhealthMigration.setRelationalIdIn("8a661876-111a-4976-9a39-2ba0e1e4d776");
		mhealthMigration.setRelationWithHHOut("Father");
		mhealthMigration.setRelationWithHHIn("Father");
		mhealthMigration.setPourasavaOut("NOT POURASABHA");
		mhealthMigration.setPourasavaIn("NOT POURASABHA");
		mhealthMigration.setNumberOfMemberOut("6");
		mhealthMigration.setNumberOfMemberIn("5");
		mhealthMigration.setMigrationDate(new DateTime().toDate());
		mhealthMigration.setMemberType("HH");
		mhealthMigration.setMemberName("Baby");
		mhealthMigration.setMemberIDOut("30333300017030131320104");
		mhealthMigration.setMemberIDIn("30333300017030131320104");
		mhealthMigration.setIsMember("no");
		mhealthMigration.setDob(new DateTime().toDate());
		mhealthMigration.setDivisionOut("DHAKA");
		mhealthMigration.setDivisionIn("DHAKA");
		mhealthMigration.setDistrictOut("GAZIPUR");
		mhealthMigration.setDistrictIn("DHAKA");
		mhealthMigration.setBranchIDIn("8");
		mhealthMigration.setSKOut("01313047271");
		mhealthMigration.setSKIn("01313047103");
		mhealthMigration.setHHNameOut("Kitta");
		mhealthMigration.setHHNameIn("Kitta");
		mhealthMigration.setHHContactOut("01585785805");
		mhealthMigration.setHHContactIn("01585785805");
		mhealthMigration.setBranchIDOut("2982");
		return mhealthMigration;
	}
	
	public void addMember() {
		Client client = new Client("a6c836ea-800d-49ea-8565-0d7c58fdcb8c").withBirthdate(new DateTime("2017-03-31"), true)
		        .withGender("Male").withFirstName("Rasheda").withLastName("");
		List<Address> addresses = new ArrayList<Address>();
		Address address = new Address();
		address.setCountry("BBANGLADESH");
		address.setAddressType("usual_residence");
		address.setCityVillage("TORAIL");
		address.setStateProvince("DHAKA");
		address.setCountyDistrict("NARAYANGANJ");
		
		Map<String, String> addressFields = new HashMap<>();
		addressFields.put("address1", "BHOLABA");
		addressFields.put("address2", "RUPGANJ");
		addressFields.put("address3", "NOT POURASABHA");
		addressFields.put("address8", "136962");
		address.setAddressFields(addressFields);
		addresses.add(address);
		Map<String, List<String>> relationships = new HashMap<>();
		List<String> relationalIds = new ArrayList<>();
		relationalIds.add("0511acf9-6be8-4a98-b4f9-f8a5cfa704bb");
		List<String> mother = new ArrayList<>();
		mother.add("0511acf9-6be8-4a98-b4f9-f8a5cfa704bC");
		relationships.put("family", relationalIds);
		relationships.put("mother", mother);
		client.setAddresses(addresses);
		client.setRelationships(relationships);
		client.setServerVersion(0);
		client.withIdentifier("opensrp_id", "508844006730700016010001").withAttribute("Home_Facility", "Linda")
		        .withAttribute("Marital_Status", "Married")
		        .withAttribute("difficulty_seeing_hearing", "yes_little_difficulties")
		        .withAttribute("difficulty_walking_up_down", "yes_little_difficulties").withAttribute("Blood_Group", "A+")
		        .withAttribute("Relation_with_HOH", "Son").withAttribute("age", "24");
		String district = "10432";
		String postfix = "";
		String division = "1";
		String branch = "34";
		MhealthPractitionerLocation location = new MhealthPractitionerLocation();
		location.setBranch(branch);
		location.setDistrict(district);
		location.setDivision(division);
		location.setPostFix(postfix);
		clientService.addOrUpdate(client, location);
	}
	
	private Event addMemberEvent() {
		Obs obs = new Obs("concept", "decimal", "1730AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", null, "3.5", null, "weight");
		Event event = new Event().withBaseEntityId("a6c836ea-800d-49ea-8565-0d7c58fdcb8c")
		        .withEventType("Family Member Registration").withFormSubmissionId("a6c836ea-800d-49ea-8565-0d7c58fdcb8d")
		        .withEventDate(new DateTime()).withObs(obs);
		event.setProviderId("testsk");
		event.setEntityType("Family Member Registration");
		event.setEntityType("ec_family_member");
		event.setLocationId("1234567");
		event.setTeam("hnpp");
		event.setTeamId("hnpp id");
		
		String district = "10432";
		String postfix = "";
		String division = "1";
		String branch = "34";
		MhealthPractitionerLocation location = new MhealthPractitionerLocation();
		location.setBranch(branch);
		location.setDistrict(district);
		location.setDivision(division);
		location.setPostFix(postfix);
		eventService.addorUpdateEvent(event, "testsk", location);
		return event;
	}
	
	public void addFamily1() {
		Client client = new Client("0511acf9-6be8-4a98-b4f9-f8a5cfa704bb").withBirthdate(new DateTime("2017-03-31"), true)
		        .withGender("Male").withFirstName("Rashed").withLastName("Family");
		List<Address> addresses = new ArrayList<Address>();
		Address address = new Address();
		address.setCountry("BBANGLADESH");
		address.setAddressType("usual_residence");
		address.setCityVillage("TORAIL");
		address.setStateProvince("DHAKA");
		address.setCountyDistrict("NARAYANGANJ");
		
		Map<String, String> addressFields = new HashMap<>();
		addressFields.put("address1", "BHOLABA");
		addressFields.put("address2", "RUPGANJ");
		addressFields.put("address3", "NOT POURASABHA");
		addressFields.put("address8", "136962");
		address.setAddressFields(addressFields);
		addresses.add(address);
		Map<String, List<String>> relationships = new HashMap<>();
		List<String> relationalIds = new ArrayList<>();
		relationalIds.add("0511acf9-6be8-4a98-b4f9-f8a5cfa704bb");
		relationships.put("family_head", relationalIds);
		client.setAddresses(addresses);
		client.setRelationships(relationships);
		client.setServerVersion(0);
		client.withIdentifier("opensrp_id", "50884400673070001601").withAttribute("Home_Facility", "Linda")
		        .withAttribute("Cluster", "2nd_Cluster").withAttribute("SS_Name", "Maloti(SS-3)")
		        .withAttribute("serial_no", "H369").withAttribute("hh_occupation", "technician")
		        .withAttribute("HOH_Phone_Number", "01471221551").withAttribute("hh_roof_material", "tin");
		String district = "10432";
		String postfix = "";
		String division = "1";
		String branch = "34";
		MhealthPractitionerLocation location = new MhealthPractitionerLocation();
		location.setBranch(branch);
		location.setDistrict(district);
		location.setDivision(division);
		location.setPostFix(postfix);
		clientService.addOrUpdate(client, location);
	}
	
	private Event addFamilyEvent1() {
		Obs obs = new Obs("concept", "decimal", "1730AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", null, "3.5", null, "weight");
		Event event = new Event().withBaseEntityId("0511acf9-6be8-4a98-b4f9-f8a5cfa704bb")
		        .withEventType("Family Registration").withFormSubmissionId("0511acf9-6be8-4a98-b4f9-f8a5cfa704bc")
		        .withEventDate(new DateTime()).withObs(obs);
		event.setProviderId("testsk");
		event.setEntityType("Family Registration");
		event.setEntityType("ec_family");
		event.setLocationId("1234567");
		event.setTeam("hnpp");
		event.setTeamId("hnpp id");
		
		String district = "10432";
		String postfix = "";
		String division = "1";
		String branch = "34";
		MhealthPractitionerLocation location = new MhealthPractitionerLocation();
		location.setBranch(branch);
		location.setDistrict(district);
		location.setDivision(division);
		location.setPostFix(postfix);
		eventService.addorUpdateEvent(event, "testsk", location);
		return event;
	}
	
	public void addFamily2() {
		Client client = new Client("05b5acd9-d375-4892-aae3-2cefa17c7538").withBirthdate(new DateTime("2017-03-31"), true)
		        .withGender("Male").withFirstName("Migrated Rashed").withLastName("Family");
		List<Address> addresses = new ArrayList<Address>();
		Address address = new Address();
		address.setCountry("BBANGLADESH");
		address.setAddressType("usual_residence");
		address.setCityVillage("Migrated village");
		address.setStateProvince("DHAKA");
		address.setCountyDistrict("NARAYANGANJ");
		
		Map<String, String> addressFields = new HashMap<>();
		addressFields.put("address1", "BHOLABA");
		addressFields.put("address2", "RUPGANJ");
		addressFields.put("address3", "NOT POURASABHA");
		addressFields.put("address8", "136960");
		address.setAddressFields(addressFields);
		addresses.add(address);
		Map<String, List<String>> relationships = new HashMap<>();
		List<String> relationalIds = new ArrayList<>();
		relationalIds.add("05b5acd9-d375-4892-aae3-2cefa17c7538");
		relationships.put("family_head", relationalIds);
		client.setAddresses(addresses);
		client.setRelationships(relationships);
		client.setServerVersion(0);
		client.withIdentifier("opensrp_id", "50884400673070001602").withAttribute("Home_Facility", "Linda")
		        .withAttribute("Cluster", "2nd_Cluster").withAttribute("SS_Name", "Forida(SS-1)")
		        .withAttribute("serial_no", "H369").withAttribute("hh_occupation", "technician")
		        .withAttribute("HOH_Phone_Number", "01771221551").withAttribute("hh_roof_material", "tin");
		String district = "10425";
		String postfix = "";
		String division = "1";
		String branch = "36";
		MhealthPractitionerLocation location = new MhealthPractitionerLocation();
		location.setBranch(branch);
		location.setDistrict(district);
		location.setDivision(division);
		location.setPostFix(postfix);
		clientService.addOrUpdate(client, location);
		
	}
	
	private Event addFamilyEvent2() {
		Obs obs = new Obs("concept", "decimal", "1730AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", null, "3.5", null, "weight");
		Event event = new Event().withBaseEntityId("05b5acd9-d375-4892-aae3-2cefa17c7538")
		        .withEventType("Family Registration").withFormSubmissionId("05b5acd9-d375-4892-aae3-2cefa17c7537")
		        .withEventDate(new DateTime()).withObs(obs);
		event.setProviderId("testsk1");
		event.setEntityType("Family Registration");
		event.setEntityType("ec_family");
		event.setLocationId("1234567");
		event.setTeam("hnpp");
		event.setTeamId("hnpp id");
		
		String district = "10425";
		String postfix = "";
		String division = "1";
		String branch = "36";
		MhealthPractitionerLocation location = new MhealthPractitionerLocation();
		location.setBranch(branch);
		location.setDistrict(district);
		location.setDivision(division);
		location.setPostFix(postfix);
		eventService.addorUpdateEvent(event, "testsk1", location);
		
		return event;
	}
}
