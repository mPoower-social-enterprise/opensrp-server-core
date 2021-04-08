package org.opensrp.repository.postgres.mapper.custom;

import org.apache.ibatis.annotations.Param;
import org.opensrp.domain.postgres.Client;
import org.opensrp.repository.postgres.mapper.ClientMetadataMapper;

public interface CustomMhealthClientMetadataMapper extends ClientMetadataMapper {
	
	Integer selectClientIdByBaseEntityId(@Param("baseEntityId") String baseEntityId, @Param("postfix") String postfi);
	
	Client selectClientByClientId(@Param("clientId") Integer clientId, @Param("postfix") String postfix);
}
