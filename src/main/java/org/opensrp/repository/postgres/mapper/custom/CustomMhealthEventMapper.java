package org.opensrp.repository.postgres.mapper.custom;

import org.apache.ibatis.annotations.Param;
import org.opensrp.domain.postgres.Event;
import org.opensrp.domain.postgres.MhealthEvent;
import org.opensrp.repository.postgres.mapper.EventMetadataMapper;

public interface CustomMhealthEventMapper extends EventMetadataMapper {
	
	Event selectByDocumentId(@Param("documentId") String documentId, @Param("postfix") String postfix);
	
	int insertSelectiveAndSetId(MhealthEvent record);
	
	int updateByPrimaryKeySelective(MhealthEvent record);
	
	Long selectServerVersionByPrimaryKey(@Param("id") Long id, @Param("postfix") String postfix);
	
	int updateByPrimaryKeyAndGenerateServerVersion(MhealthEvent record);
	
	Integer selectEventIdByFormSubmissionId(@Param("baseEntityId") String baseEntityId, @Param("postfix") String table);
	
	Event selectEventByEventId(@Param("eventId") Integer eventId, @Param("postfix") String postfix);
	
}
