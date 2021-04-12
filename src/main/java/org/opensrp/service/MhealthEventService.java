package org.opensrp.service;

import java.util.List;

import org.joda.time.DateTime;
import org.opensrp.repository.MhealthEventsRepository;
import org.smartregister.domain.Event;
import org.springframework.stereotype.Service;

@Service
public class MhealthEventService {
	
	private MhealthEventsRepository mhealthEventsRepository;
	
	public MhealthEventService(MhealthEventsRepository mhealthEventsRepository) {
		super();
		this.mhealthEventsRepository = mhealthEventsRepository;
	}
	
	public synchronized Event addorUpdateEvent(Event event, String username, String district, String division,
	                                           String branch) {
		String postfix = "_" + district;
		Integer eventId = findEventIdByFormSubmissionId(event.getFormSubmissionId(), postfix);
		
		if (eventId != null) {
			Event existingEvent = findEventByEventId(eventId, postfix);
			event.setId(existingEvent.getId());
			event.setRevision(existingEvent.getRevision());
			event.setDateEdited(DateTime.now());
			event.setRevision(existingEvent.getRevision());
			mhealthEventsRepository.update(event, postfix, district, division, branch);
			
		} else {
			event.setDateCreated(DateTime.now());
			mhealthEventsRepository.add(event, postfix, district, division, branch);
			
		}
		return event;
	}
	
	public Integer findEventIdByFormSubmissionId(String baseEntityId, String postfix) {
		
		return mhealthEventsRepository.findEventIdByFormSubmissionId(baseEntityId, postfix);
	}
	
	public Event findEventByEventId(Integer eventId, String postfix) {
		
		return mhealthEventsRepository.findEventByEventId(eventId, postfix);
	}
	
	public List<Event> findByVillageIds(String providerId, List<Long> villageIds, long serverVersion, int limit,
	                                    String postfix) {
		
		return mhealthEventsRepository.findByVillageIds(providerId, villageIds, serverVersion, limit, postfix);
	}
	
	public List<Event> findByProvider(long serverVersion, String providerId, int limit, String postfix) {
		
		return mhealthEventsRepository.findByProvider(serverVersion, providerId, limit, postfix);
	}
	
}
