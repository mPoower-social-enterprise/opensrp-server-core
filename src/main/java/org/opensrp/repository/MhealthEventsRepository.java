package org.opensrp.repository;

import org.smartregister.domain.Event;

public interface MhealthEventsRepository {
	
	Integer findEventIdByFormSubmissionId(String baseEntityId, String table);
	
	Event findEventByEventId(Integer eventId, String postfix);
	
	Long retrievePrimaryKey(Event t, String postfix);
	
	Event get(String id, String postfix);
	
	void add(Event entity, String postfix, String district, String division, String branch, String village);
	
	void update(Event entity, String postfix, String district, String division, String branch, String village);
	
	void update(Event entity, boolean allowArchived, String table, String district, String division, String branch,
	            String village);
}
