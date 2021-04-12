package org.opensrp.repository.postgres.mapper.custom;

import org.opensrp.domain.postgres.MhealthClientMetadata;
import org.opensrp.repository.postgres.mapper.ClientMetadataMapper;

public interface CustomMhealthClientMetadataMapper extends ClientMetadataMapper {
	
	int insertSelective(MhealthClientMetadata record);
	
	int updateByPrimaryKey(MhealthClientMetadata record);
	
}
