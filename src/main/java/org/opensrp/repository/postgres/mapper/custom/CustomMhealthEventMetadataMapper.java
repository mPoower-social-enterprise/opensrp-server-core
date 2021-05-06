package org.opensrp.repository.postgres.mapper.custom;

import org.apache.ibatis.annotations.Param;
import org.opensrp.domain.postgres.MhealthEventMetadata;

public interface CustomMhealthEventMetadataMapper {
	
	Long selectEventMetadataIdByEventId(@Param("eventId") Long eventId, @Param("postfix") String postfix);
	
	int insertSelective(MhealthEventMetadata record);
	
	int updateByPrimaryKey(MhealthEventMetadata record);
	
	MhealthEventMetadata selectFirstEventMetadata(@Param("baseEntityId") String baseEntityId,
	                                              @Param("postfix") String postfix);
	
}
