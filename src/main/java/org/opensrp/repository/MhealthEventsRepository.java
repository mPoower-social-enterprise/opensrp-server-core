package org.opensrp.repository;

import org.apache.ibatis.annotations.Param;
import org.smartregister.domain.Event;

public interface MhealthEventsRepository {
	
	Integer findEventIdByFormSubmissionId(@Param("baseEntityId") String baseEntityId, @Param("table") String table);
	
	Event findEventByEventId(Integer eventId, String postfix);
}
