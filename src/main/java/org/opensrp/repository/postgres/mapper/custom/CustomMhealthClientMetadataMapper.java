package org.opensrp.repository.postgres.mapper.custom;

import org.apache.ibatis.annotations.Param;
import org.opensrp.domain.postgres.ClientMetadata;
import org.opensrp.domain.postgres.MhealthClientMetadata;

public interface CustomMhealthClientMetadataMapper {
	
	int insertSelective(MhealthClientMetadata record);
	
	int updateByPrimaryKey(MhealthClientMetadata record);
	
	ClientMetadata selectByClientId(@Param("clientId") Long clientId, @Param("postfix") String postfix);
}
