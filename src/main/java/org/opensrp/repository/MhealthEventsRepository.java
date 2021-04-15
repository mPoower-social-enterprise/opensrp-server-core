package org.opensrp.repository;

import java.util.List;

import org.smartregister.domain.Event;

public interface MhealthEventsRepository {
	
	Long findEventIdByFormSubmissionId(String formSubmissionId, String table);
	
	Event findEventByEventId(Long eventId, String postfix);
	
	Event findByBaseEntityId(String baseEntityId, String postfix);
	
	Event findByFormSubmissionId(String formSubmissionId, String postfix);
	
	Long retrievePrimaryKey(Event t, String postfix);
	
	Event get(String id, String postfix);
	
	void add(Event entity, String postfix, String district, String division, String branch);
	
	void update(Event entity, String postfix, String district, String division, String branch);
	
	void update(Event entity, boolean allowArchived, String table, String district, String division, String branch);
	
	List<Event> findByVillageIds(String providerId, List<Long> villageIds, long serverVersion, int limit, String postfix);
	
	List<Event> findByProvider(long serverVersion, String providerId, int limit, String postfix);
	
}
