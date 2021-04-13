package org.opensrp.repository.postgres;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opensrp.repository.MhealthEventsRepository;
import org.smartregister.domain.Event;
import org.smartregister.domain.Obs;
import org.springframework.beans.factory.annotation.Autowired;

public class MhealthEventsRepositoryTest extends BaseRepositoryTest {
	
	@Autowired
	private MhealthEventsRepository mhealthEventsRepository;
	
	@BeforeClass
	public static void bootStrap() {
		tableNames = Arrays.asList("core.event", "core.event_metadata");
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
	public void shouldTestFindByProvider() {
		Event event = addEvent();
		String postfix = "";
		List<Event> findEvent = mhealthEventsRepository.findByProvider(0, event.getProviderId(), 0, postfix);
		assertNotNull(findEvent);
		assertEquals(1, findEvent.size());
	}
	
	private Event addEvent() {
		Obs obs = new Obs("concept", "decimal", "1730AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", null, "3.5", null, "weight");
		Event event = new Event().withBaseEntityId("435534534543").withEventType("Growth Monitoring")
		        .withFormSubmissionId("form_submission_id").withEventDate(new DateTime()).withObs(obs);
		event.setProviderId("testsk");
		event.setEntityType("growth monitoring");
		event.setLocationId("1234567");
		event.setTeam("hnpp");
		event.setTeamId("hnpp id");
		String district = "234";
		String division = "233";
		String branch = "34";
		String postfix = "";
		mhealthEventsRepository.add(event, postfix, district, division, branch);
		return event;
	}
}
