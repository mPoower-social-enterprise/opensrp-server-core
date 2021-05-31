package org.opensrp.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opensrp.domain.postgres.MhealthEventMetadata;
import org.opensrp.domain.postgres.MhealthPractitionerLocation;
import org.opensrp.repository.MhealthClientsRepository;
import org.opensrp.repository.MhealthEventsRepository;
import org.opensrp.repository.postgres.MhealthBaseRepositoryTest;
import org.smartregister.domain.Address;
import org.smartregister.domain.Client;
import org.smartregister.domain.Event;
import org.smartregister.domain.Obs;
import org.springframework.beans.factory.annotation.Autowired;

public class MhealthEventServiceTest extends MhealthBaseRepositoryTest {
	
	private MhealthEventService mhealthEventService;
	
	@Autowired
	private MhealthEventsRepository mhealthEventsRepository;
	
	private MhealthClientService mhealthClientService;
	
	@Autowired
	private MhealthClientsRepository mhealthClientsRepository;
	
	@BeforeClass
	public static void bootStrap() {
		tableNames = Arrays.asList("core.event", "core.event_metadata", "core.client", "core.client_metadata");
	}
	
	@Before
	public void setUpPostgresRepository() {
		mhealthEventService = new MhealthEventService(mhealthEventsRepository);
		mhealthClientService = new MhealthClientService(mhealthClientsRepository);
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
		scripts.add("drop_column.sql");
		return scripts;
	}
	
	@Test
	public void testAddorUpdate() {
		
		String district = "234";
		String division = "233";
		String branch = "34";
		String postfix = "";
		String username = "";
		MhealthPractitionerLocation location = new MhealthPractitionerLocation();
		location.setBranch(branch);
		location.setDistrict(district);
		location.setDivision(division);
		location.setPostFix(postfix);
		Event event = generateEvent();
		mhealthEventService.addorUpdateEvent(event, username, location);
		
		Event savedEvent = mhealthEventService.findByFormSubmissionId("form_submission_id", postfix);
		
		assertNotNull(savedEvent);
		assertEquals("435534534543", savedEvent.getBaseEntityId());
		assertEquals("Growth Monitoring", savedEvent.getEventType());
		assertEquals(1, savedEvent.getObs().size());
		assertEquals("3.5", savedEvent.getObs(null, "1730AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA").getValue());
		
		savedEvent.setEventType("Family Registration");
		savedEvent.setProviderId("testsku");
		mhealthEventService.addorUpdateEvent(savedEvent, username, location);
		
		Event updatedEvent = mhealthEventService.findByFormSubmissionId("form_submission_id", postfix);
		
		assertEquals("435534534543", updatedEvent.getBaseEntityId());
		assertEquals("Family Registration", updatedEvent.getEventType());
		assertEquals("testsku", updatedEvent.getProviderId());
		assertEquals(1, updatedEvent.getObs().size());
		
	}
	
	@Test
	public void testFindByVillageIds() {
		String district = "234";
		String division = "233";
		String branch = "34";
		String postfix = "";
		String username = "";
		addClient();
		Event event = generateEvent();
		MhealthPractitionerLocation location = new MhealthPractitionerLocation();
		location.setBranch(branch);
		location.setDistrict(district);
		location.setDivision(division);
		location.setPostFix(postfix);
		mhealthEventService.addorUpdateEvent(event, username, location);
		List<Long> villageIds = new ArrayList<>();
		villageIds.add(2345l);
		List<Event> events = mhealthEventService.findByVillageIds("", villageIds, 0, 1, postfix);
		assertEquals(1, events.size());
		assertEquals("435534534543", events.get(0).getBaseEntityId());
		assertEquals("Growth Monitoring", events.get(0).getEventType());
		assertEquals(1, events.get(0).getObs().size());
		
	}
	
	@Test
	public void testFindByFormSubmissionId() {
		String district = "234";
		String division = "233";
		String branch = "34";
		String postfix = "";
		String username = "";
		Event event = generateEvent();
		MhealthPractitionerLocation location = new MhealthPractitionerLocation();
		location.setBranch(branch);
		location.setDistrict(district);
		location.setDivision(division);
		location.setPostFix(postfix);
		mhealthEventService.addorUpdateEvent(event, username, location);
		Event findEvent = mhealthEventService.findByFormSubmissionId("form_submission_id", postfix);
		assertNotNull(findEvent);
		assertEquals("435534534543", findEvent.getBaseEntityId());
		assertEquals("Growth Monitoring", findEvent.getEventType());
		assertEquals(1, findEvent.getObs().size());
		
	}
	
	@Test
	public void testFindByProvider() {
		String district = "234";
		String division = "233";
		String branch = "34";
		String postfix = "";
		String username = "";
		Event event = generateEvent();
		MhealthPractitionerLocation location = new MhealthPractitionerLocation();
		location.setBranch(branch);
		location.setDistrict(district);
		location.setDivision(division);
		location.setPostFix(postfix);
		mhealthEventService.addorUpdateEvent(event, username, location);
		List<Event> events = mhealthEventService.findByProvider(0l, "testsk", 1, postfix);
		assertEquals(1, events.size());
		assertEquals("435534534543", events.get(0).getBaseEntityId());
		assertEquals("Growth Monitoring", events.get(0).getEventType());
		assertEquals(1, events.get(0).getObs().size());
		
	}
	
	@Test
	public void shouldTestFindByVillageIds() {
		addClient();
		String district = "234";
		String division = "233";
		String branch = "34";
		String postfix = "";
		String username = "";
		Event event = generateEvent();
		MhealthPractitionerLocation location = new MhealthPractitionerLocation();
		location.setBranch(branch);
		location.setDistrict(district);
		location.setDivision(division);
		location.setPostFix(postfix);
		mhealthEventService.addorUpdateEvent(event, username, location);
		
		List<Long> villageIds = new ArrayList<>();
		villageIds.add(2345l);
		List<Event> events = mhealthEventService.findByVillageIds("testsk", villageIds, 0, 1, postfix);
		assertEquals(1, events.size());
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void shouldTestGet() {
		mhealthEventsRepository.get("", "");
	}
	
	@Test
	public void shouldTestFindEventsByBaseEntityId() {
		String district = "234";
		String division = "233";
		String branch = "34";
		String postfix = "";
		String username = "";
		Event event = generateEvent();
		MhealthPractitionerLocation location = new MhealthPractitionerLocation();
		location.setBranch(branch);
		location.setDistrict(district);
		location.setDivision(division);
		location.setPostFix(postfix);
		mhealthEventService.addorUpdateEvent(event, username, location);
		
		List<Event> events = mhealthEventService.findEventsByBaseEntityId(event.getBaseEntityId(), postfix);
		assertEquals("435534534543", events.get(0).getBaseEntityId());
		assertEquals("Growth Monitoring", events.get(0).getEventType());
		assertEquals(1, events.get(0).getObs().size());
	}
	
	@Test
	public void shouldReturnEmptyTestFindEventsByBaseEntityId() {
		String postfix = "";
		List<Event> events = mhealthEventService.findEventsByBaseEntityId("dff", postfix);
		assertEquals(0, events.size());
	}
	
	@Test
	public void shouldTestFindFirstEventMetadata() {
		String district = "234";
		String division = "233";
		String branch = "34";
		String postfix = "";
		String username = "";
		Event event = generateEvent();
		MhealthPractitionerLocation location = new MhealthPractitionerLocation();
		location.setBranch(branch);
		location.setDistrict(district);
		location.setDivision(division);
		location.setPostFix(postfix);
		mhealthEventService.addorUpdateEvent(event, username, location);
		MhealthEventMetadata metadata = mhealthEventService.findFirstEventMetadata(event.getBaseEntityId(), postfix);
		assertEquals("234", metadata.getDistrict());
		assertEquals("233", metadata.getDivision());
		assertEquals("testsk", metadata.getProviderId());
	}
	
	@Test
	public void shouldReturnNullTestFindFirstEventMetadata() {
		String postfix = "";
		MhealthEventMetadata metadata = mhealthEventService.findFirstEventMetadata("rtrtrtr", postfix);
		assertNull(metadata);
	}
	
	private Event generateEvent() {
		Obs obs = new Obs("concept", "decimal", "1730AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", null, "3.5", null, "weight");
		Event event = new Event().withBaseEntityId("435534534543").withEventType("Growth Monitoring")
		        .withFormSubmissionId("form_submission_id").withEventDate(new DateTime()).withObs(obs);
		event.setProviderId("testsk");
		event.setEntityType("Growth Monitoring");
		event.setLocationId("1234567");
		event.setTeam("hnpp");
		event.setTeamId("hnpp id");
		event.setId("123456789");
		
		return event;
	}
	
	public void addClient() {
		Client client = new Client("435534534543").withBirthdate(new DateTime("2017-03-31"), true).withGender("Male")
		        .withFirstName("xobili").withLastName("mbangwa");
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
		mhealthClientService.addOrUpdate(client, location);
	}
}
