package org.opensrp.repository.postgres.mapper.custom;

import org.apache.ibatis.annotations.Param;
import org.opensrp.domain.postgres.Client;
import org.opensrp.domain.postgres.MhealthClient;
import org.opensrp.domain.postgres.MhealthClientMetadata;

public interface CustomMhealthClientMapper {
	
	Integer selectClientIdByBaseEntityId(@Param("baseEntityId") String baseEntityId, @Param("postfix") String postfi);
	
	Client selectClientByClientId(@Param("clientId") Integer clientId, @Param("postfix") String postfix);
	
	int insertSelectiveAndSetId(MhealthClient record);
	
	int insertSelective(MhealthClientMetadata record);
	
	Client selectOne(@Param("baseEntityId") String baseEntityId, @Param("postfix") String postfix);
	
	Client selectByDocumentId(@Param("documentId") String documentId, @Param("postfix") String postfix);
	
	int updateByPrimaryKeyAndGenerateServerVersion(MhealthClient record);
	
	int updateByPrimaryKey(MhealthClientMetadata record);
	
	int updateByPrimaryKeySelective(MhealthClient record);
	
	Long selectServerVersionByPrimaryKey(@Param("id") Long id, @Param("postfix") String postfix);
}
