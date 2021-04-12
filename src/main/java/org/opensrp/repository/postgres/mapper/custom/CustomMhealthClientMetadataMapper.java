package org.opensrp.repository.postgres.mapper.custom;

import org.opensrp.domain.postgres.MhealthClientMetadata;

public interface CustomMhealthClientMetadataMapper {
	
	int insertSelective(MhealthClientMetadata record);
	
	int updateByPrimaryKey(MhealthClientMetadata record);
	
}
