package org.opensrp.service;

import java.util.List;

import org.joda.time.DateTime;
import org.opensrp.domain.postgres.MhealthEventMetadata;
import org.opensrp.domain.postgres.MhealthPractitionerLocation;
import org.opensrp.repository.MhealthEventsRepository;
import org.smartregister.domain.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MhealthEventService {
	
	private MhealthEventsRepository mhealthEventsRepository;
	
	@Autowired
	public MhealthEventService(MhealthEventsRepository mhealthEventsRepository) {
		this.mhealthEventsRepository = mhealthEventsRepository;
	}
	
	public synchronized Event addorUpdateEvent(Event event, String username, MhealthPractitionerLocation location) {
		
		Long eventId = findEventIdByFormSubmissionId(event.getFormSubmissionId(), location.getPostFix());
		
		if (eventId != null) {
			Event existingEvent = findEventByEventId(eventId, location.getPostFix());
			event.setId(existingEvent.getId());
			event.setRevision(existingEvent.getRevision());
			event.setDateEdited(DateTime.now());
			event.setRevision(existingEvent.getRevision());
			mhealthEventsRepository.update(event, location);
			
		} else {
			event.setDateCreated(DateTime.now());
			mhealthEventsRepository.add(event, location);
			
		}
		return event;
	}
	
	public Long findEventIdByFormSubmissionId(String baseEntityId, String postfix) {
		
		return mhealthEventsRepository.findEventIdByFormSubmissionId(baseEntityId, postfix);
	}
	
	public Event findEventByEventId(Long eventId, String postfix) {
		
		return mhealthEventsRepository.findEventByEventId(eventId, postfix);
	}
	
	public List<Event> findByVillageIds(String providerId, List<Long> villageIds, long serverVersion, int limit,
	                                    String postfix) {
		System.err.println("findByVillageIds");
		return mhealthEventsRepository.findByVillageIds(providerId, villageIds, serverVersion, limit, postfix);
	}
	
	public List<Event> findByProvider(long serverVersion, String providerId, int limit, String postfix) {
		System.err.println("findByProvider");
		
		return mhealthEventsRepository.findByProvider(serverVersion, providerId, limit, postfix);
	}
	
	public Event findByFormSubmissionId(String formSubmissionId, String postfix) {
		System.err.println("findByFormSubmissionId");
		return mhealthEventsRepository.findByFormSubmissionId(formSubmissionId, postfix);
	}
	
	public List<Event> findEventsByBaseEntityId(String baseEntityId, String postfix) {
		System.err.println("findEventsByBaseEntityId");
		return mhealthEventsRepository.findEventsByBaseEntityId(baseEntityId, postfix);
	}
	
	public MhealthEventMetadata findFirstEventMetadata(String baseEntityId, String postfix) {
		System.err.println("findFirstEventMetadata");
		return mhealthEventsRepository.findFirstEventMetadata(baseEntityId, postfix);
	}
}
