package org.opensrp.repository.postgres.mapper.custom;

import org.apache.ibatis.annotations.Param;
import org.opensrp.domain.postgres.EventMetadataExample;
import org.opensrp.domain.postgres.MhealthEventMetadata;
import org.opensrp.repository.postgres.mapper.EventMetadataMapper;

public interface CustomMhealthEventMetadataMapper extends EventMetadataMapper {
	
	Long selectPrimaryKey(EventMetadataExample eventMetadataExample, @Param("postfix") String postfix);
	
	int insertSelective(MhealthEventMetadata record);
	
	int updateByPrimaryKey(MhealthEventMetadata record);
	
}
