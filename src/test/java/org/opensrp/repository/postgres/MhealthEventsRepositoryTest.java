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
import org.opensrp.repository.MhealthClientsRepository;
import org.opensrp.repository.MhealthEventsRepository;
import org.smartregister.domain.Address;
import org.smartregister.domain.Client;
import org.smartregister.domain.Event;
import org.smartregister.domain.Obs;
import org.springframework.beans.factory.annotation.Autowired;

public class MhealthEventsRepositoryTest extends BaseRepositoryTest {
	
	@Autowired
	private MhealthEventsRepository mhealthEventsRepository;
	
	@Autowired
	private MhealthClientsRepository mhealthClientsRepository;
	
	@BeforeClass
	public static void bootStrap() {
		tableNames = Arrays.asList("core.event", "core.event_metadata", "core.client", "core.client_metadata");
	}
	
	@Override
	protected Set<String> getDatabaseScripts() {
		return null;
	}
	
	@Test
	public void shouldTestAdd() {
		Event event = addEvent();
		String postfix = "";
		Event findEvent = mhealthEventsRepository.findByBaseEntityId(event.getBaseEntityId(), postfix);
		assertEquals("435534534543", findEvent.getBaseEntityId());
		assertEquals("Growth Monitoring", findEvent.getEventType());
		assertEquals(1, findEvent.getObs().size());
		assertEquals("3.5", findEvent.getObs(null, "1730AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA").getValue());
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIIllegalArgumentExceptionTestDuplicateRecordAdd() {
		addEvent();
		addEvent();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIIllegalArgumentExceptionTestAdd() {
		Event event = null;
		String district = "234";
		String division = "233";
		String branch = "34";
		String postfix = "";
		mhealthEventsRepository.add(event, postfix, district, division, branch);
	}
	
	@Test(expected = IllegalStateException.class)
	public void shouldThrowIIllegalStateExceptionExceptionTestUpdate() {
		Event event = null;
		String district = "234";
		String division = "233";
		String branch = "34";
		String postfix = "";
		mhealthEventsRepository.update(event, postfix, district, division, branch);
	}
	
	@Test(expected = IllegalStateException.class)
	public void shouldThrowIIllegalStateExceptionExceptionTestUpdateWithNotExists() {
		Obs obs = new Obs("concept", "decimal", "1730AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", null, "3.5", null, "weight");
		Event event = new Event().withBaseEntityId("435534534543").withEventType("Growth Monitoring")
		        .withFormSubmissionId("form_submission_id").withEventDate(new DateTime()).withObs(obs);
		event.setProviderId("testsk");
		event.setEntityType("Growth Monitoring");
		event.setLocationId("1234567");
		event.setTeam("hnpp");
		event.setTeamId("hnpp id");
		event.setId("123456789");
		String district = "234";
		String division = "233";
		String branch = "34";
		String postfix = "";
		mhealthEventsRepository.update(event, postfix, district, division, branch);
	}
	
	@Test
	public void shouldTestUpdate() {
		Event event = addEvent();
		String postfix = "";
		String district = "234";
		String division = "233";
		String branch = "34";
		Event e = mhealthEventsRepository.findByBaseEntityId(event.getBaseEntityId(), postfix);
		e.setEventType("Family Registration");
		e.setProviderId("testsku");
		mhealthEventsRepository.update(e, postfix, district, division, branch);
		Event findEvent = mhealthEventsRepository.findByBaseEntityId(event.getBaseEntityId(), postfix);
		assertEquals("435534534543", findEvent.getBaseEntityId());
		assertEquals("Family Registration", findEvent.getEventType());
		assertEquals("testsku", findEvent.getProviderId());
		assertEquals(1, findEvent.getObs().size());
		assertEquals("3.5", findEvent.getObs(null, "1730AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA").getValue());
		
	}
	
	@Test
	public void shouldTestFindByFormSubmissionId() {
		Event event = addEvent();
		String postfix = "";
		Event findEvent = mhealthEventsRepository.findByFormSubmissionId(event.getFormSubmissionId(), postfix);
		assertEquals("435534534543", findEvent.getBaseEntityId());
		assertEquals("Growth Monitoring", findEvent.getEventType());
		assertEquals(1, findEvent.getObs().size());
	}
	
	@Test
	public void shouldTestFindByBaseEntityId() {
		Event event = addEvent();
		String postfix = "";
		Event findEvent = mhealthEventsRepository.findByBaseEntityId(event.getBaseEntityId(), postfix);
		assertEquals("435534534543", findEvent.getBaseEntityId());
		assertEquals("Growth Monitoring", findEvent.getEventType());
		assertEquals(1, findEvent.getObs().size());
	}
	
	@Test
	public void shouldTestFindEventByEventId() {
		Event event = addEvent();
		String postfix = "";
		Long eventId = mhealthEventsRepository.findEventIdByFormSubmissionId(event.getFormSubmissionId(), postfix);
		Event e = mhealthEventsRepository.findEventByEventId(eventId, postfix);
		assertNotNull(e);
		assertEquals("435534534543", e.getBaseEntityId());
		assertEquals("Growth Monitoring", e.getEventType());
		assertEquals(1, e.getObs().size());
		
	}
	
	@Test
	public void shouldTestFindByProvider() {
		Event event = addEvent();
		String postfix = "";
		List<Event> findEvent = mhealthEventsRepository.findByProvider(0, event.getProviderId(), 0, postfix);
		assertNotNull(findEvent);
		assertEquals(1, findEvent.size());
	}
	
	@Test
	public void shouldTestFindByVillageIds() {
		addClient();
		addEvent();
		String postfix = "";
		List<Long> villageIds = new ArrayList<>();
		villageIds.add(2345l);
		List<Event> events = mhealthEventsRepository.findByVillageIds("testsk", villageIds, 0, 1, postfix);
		assertEquals(1, events.size());
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void shouldTestGet() {
		mhealthEventsRepository.get("", "");
	}
	
	private Event addEvent() {
		Obs obs = new Obs("concept", "decimal", "1730AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", null, "3.5", null, "weight");
		Event event = new Event().withBaseEntityId("435534534543").withEventType("Growth Monitoring")
		        .withFormSubmissionId("form_submission_id").withEventDate(new DateTime()).withObs(obs);
		event.setProviderId("testsk");
		event.setEntityType("Growth Monitoring");
		event.setLocationId("1234567");
		event.setTeam("hnpp");
		event.setTeamId("hnpp id");
		event.setId("123456789");
		String district = "234";
		String division = "233";
		String branch = "34";
		String postfix = "";
		mhealthEventsRepository.add(event, postfix, district, division, branch);
		return event;
	}
	
	private Client addClient() {
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
		mhealthClientsRepository.add(client, postfix, district, division, branch);
		return client;
	}
}
