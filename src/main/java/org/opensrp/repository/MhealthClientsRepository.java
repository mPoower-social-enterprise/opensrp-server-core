package org.opensrp.repository;

import java.util.List;

import org.opensrp.domain.postgres.ClientMetadata;
import org.opensrp.domain.postgres.MhealthPractitionerLocation;
import org.smartregister.domain.Client;

public interface MhealthClientsRepository {
	
	Client get(String id);
	
	void add(Client entity, MhealthPractitionerLocation location);
	
	void update(Client entity, MhealthPractitionerLocation location);
	
	Long findClientIdByBaseEntityId(String baseEntityId, String postfix);
	
	Client findClientByClientId(Long clientId, String postfix);
	
	List<Client> convert(List<org.opensrp.domain.postgres.Client> clients);
	
	Client findByBaseEntityId(String baseEntityId, String postfix);
	
	List<Client> findByBaseEntityIds(List<String> baseEntityIds, String postfix);
	
	void update(Client entity, boolean allowArchived, MhealthPractitionerLocation location);
	
	ClientMetadata findByClientId(Long clientId, String postfix);
	
	List<Client> findByRelationshipId(String relationshipId, String table);
	
	List<Client> searchClientForMigration(Integer vilageId, String gender, Integer startAge, Integer endAge, String type);
	
}
