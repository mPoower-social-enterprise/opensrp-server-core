package org.opensrp.repository.postgres.mapper.custom;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.opensrp.domain.postgres.Event;
import org.opensrp.domain.postgres.MhealthEvent;

public interface CustomMhealthEventMapper {
	
	Event selectByDocumentId(@Param("documentId") String documentId, @Param("postfix") String postfix);
	
	int insertSelectiveAndSetId(MhealthEvent record);
	
	int updateByPrimaryKeySelective(MhealthEvent record);
	
	Long selectServerVersionByPrimaryKey(@Param("id") Long id, @Param("postfix") String postfix);
	
	int updateByPrimaryKeyAndGenerateServerVersion(MhealthEvent record);
	
	Long selectEventIdByFormSubmissionId(@Param("formSubmissionId") String formSubmissionId, @Param("postfix") String table);
	
	Long selectEventIdByDocumentId(@Param("documentId") String documentId, @Param("postfix") String table);
	
	Event selectEventByEventId(@Param("eventId") Long eventId, @Param("postfix") String postfix);
	
	List<Event> selectByVillageIds(@Param("providerId") String providerId, @Param("villageIds") List<Long> villageIds,
	                               @Param("serverVersion") long serverVersion, @Param("limit") int limit,
	                               @Param("postfix") String postfix);
	
	List<Event> selectByProvider(@Param("serverVersion") long serverVersion, @Param("providerId") String providerId,
	                             @Param("limit") int limit, @Param("postfix") String postfix);
	
	Event selectByBaseEntityId(@Param("baseEntityId") String baseEntityId, @Param("postfix") String postfix);
	
	Event selectByFormSubmissionId(@Param("formSubmissionId") String formSubmissionId, @Param("postfix") String postfix);
}
