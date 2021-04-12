package org.opensrp.repository;

import java.util.List;

import org.smartregister.domain.Client;

public interface MhealthClientsRepository {
	
	Client get(String id);
	
	void add(Client entity, String postfix, String district, String division, String branch);
	
	Long retrievePrimaryKey(Client t, String postfix);
	
	void update(Client entity, String postfix, String district, String division, String branch);
	
	Integer findClientIdByBaseEntityId(String baseEntityId, String postfix);
	
	Client findClientByClientId(Integer clientId, String postfix);
	
	List<Client> convert(List<org.opensrp.domain.postgres.Client> clients);
	
	void update(Client entity, boolean allowArchived, String table, String district, String division, String branch);
	
}
