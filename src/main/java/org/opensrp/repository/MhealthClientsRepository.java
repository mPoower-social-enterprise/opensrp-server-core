package org.opensrp.repository;

import java.util.List;

import org.smartregister.domain.Client;

public interface MhealthClientsRepository {
	
	/**
	 * Get the object using identifier
	 * 
	 * @param id identifier
	 * @return entity
	 */
	Client get(String id);
	
	/**
	 * Add entity to the database
	 * 
	 * @param entity to add
	 */
	void add(Client entity, String table, String district, String division, String branch, String village);
	
	/**
	 * Update entity
	 * 
	 * @param entity to update
	 */
	void update(Client entity, String table, String district, String division, String branch, String village);
	
	Integer findClientIdByBaseEntityId(String baseEntityId, String postfix);
	
	Client findClientByClientId(Integer clientId, String postfix);
	
	List<Client> convert(List<org.opensrp.domain.postgres.Client> clients);
	
	void update(Client entity, boolean allowArchived, String table, String district, String division, String branch,
	            String village);
}
