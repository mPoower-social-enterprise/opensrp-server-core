package org.opensrp.repository.postgres.mapper.custom;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.opensrp.domain.postgres.Client;
import org.opensrp.domain.postgres.MhealthClient;
import org.opensrp.domain.postgres.MhealthClientMetadata;

public interface CustomMhealthClientMapper {
	
	Long selectClientIdByBaseEntityId(@Param("baseEntityId") String baseEntityId, @Param("postfix") String postfi);
	
	Client selectClientByClientId(@Param("clientId") Long clientId, @Param("postfix") String postfix);
	
	int insertSelectiveAndSetId(MhealthClient record);
	
	int insertSelective(MhealthClientMetadata record);
	
	Client selectOne(@Param("baseEntityId") String baseEntityId, @Param("postfix") String postfix);
	
	Client selectByDocumentId(@Param("documentId") String documentId, @Param("postfix") String postfix);
	
	int updateByPrimaryKeyAndGenerateServerVersion(MhealthClient record);
	
	int updateByPrimaryKey(MhealthClientMetadata record);
	
	int updateByPrimaryKeySelective(MhealthClient record);
	
	Long selectServerVersionByPrimaryKey(@Param("id") Long id, @Param("postfix") String postfix);
	
	Client selectByBaseEntityId(@Param("baseEntityId") String baseEntityId, @Param("postfix") String postfix);
	
	List<Client> selectByBaseEntityIds(@Param("baseEntityIds") List<String> baseEntityIds, @Param("postfix") String postfix);
	
	List<Client> selectByRelationshipId(@Param("relationshipId") String relationshipId, @Param("postfix") String postfix);
	
	List<Client> selectClientForMigration(@Param("villageId") int villageId, @Param("gender") String gender,
	                                      @Param("startAge") Integer startAge, @Param("endAge") Integer endAge,
	                                      @Param("type") String type, @Param("postfix") String postfix);
}
