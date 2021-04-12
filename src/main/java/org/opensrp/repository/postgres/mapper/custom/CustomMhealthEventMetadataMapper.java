package org.opensrp.repository.postgres.mapper.custom;

import org.apache.ibatis.annotations.Param;
import org.opensrp.domain.postgres.MhealthEventMetadata;

public interface CustomMhealthEventMetadataMapper {
	
	Long selectPrimaryKey(@Param("documentId") String documentId, @Param("postfix") String postfix);
	
	int insertSelective(MhealthEventMetadata record);
	
	int updateByPrimaryKey(MhealthEventMetadata record);
	
}
