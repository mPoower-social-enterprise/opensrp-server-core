package org.opensrp.repository;

import java.util.List;

import org.opensrp.domain.postgres.ClientMetadata;
import org.smartregister.domain.Client;

public interface MhealthClientsRepository {
	
	Client get(String id);
	
	void add(Client entity, String postfix, String district, String division, String branch);
	
	void update(Client entity, String postfix, String district, String division, String branch);
	
	Long findClientIdByBaseEntityId(String baseEntityId, String postfix);
	
	Client findClientByClientId(Long clientId, String postfix);
	
	List<Client> convert(List<org.opensrp.domain.postgres.Client> clients);
	
	Client findByBaseEntityId(String baseEntityId, String postfix);
	
	void update(Client entity, boolean allowArchived, String table, String district, String division, String branch);
	
	ClientMetadata findByClientId(Long clientId, String postfix);
}
