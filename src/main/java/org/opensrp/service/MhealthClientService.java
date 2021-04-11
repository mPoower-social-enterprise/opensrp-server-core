package org.opensrp.service;

import org.joda.time.DateTime;
import org.opensrp.repository.MhealthClientsRepository;
import org.smartregister.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MhealthClientService {
	
	private MhealthClientsRepository mhealthClientsRepository;
	
	@Autowired
	public void setMhealthClientsRepository(MhealthClientsRepository mhealthClientsRepository) {
		this.mhealthClientsRepository = mhealthClientsRepository;
	}
	
	public Client addOrUpdate(Client client, String table, String district, String division, String branch, String village) {
		if (client.getBaseEntityId() == null) {
			throw new RuntimeException("No baseEntityId");
		}
		
		try {
			Integer clientId = findClientIdByBaseEntityId(client.getBaseEntityId(), table);
			if (clientId != null) {
				Client c = findClientByClientId(clientId, table);
				if (c != null) {
					client.setRevision(c.getRevision());
					client.setId(c.getId());
					client.setDateEdited(DateTime.now());
					client.setDateCreated(c.getDateCreated());
					client.setServerVersion(System.currentTimeMillis());
					client.addIdentifier("OPENMRS_UUID", c.getIdentifier("OPENMRS_UUID"));
					mhealthClientsRepository.update(client, table, district, division, branch, village);
				}
				
			} else {
				client.setServerVersion(System.currentTimeMillis());
				client.setDateCreated(DateTime.now());
				
				mhealthClientsRepository.add(client, table, district, division, branch, village);
			}
		}
		catch (Exception e) {
			
		}
		
		return client;
	}
	
	public Integer findClientIdByBaseEntityId(String baseEntityId, String postfix) {
		
		return mhealthClientsRepository.findClientIdByBaseEntityId(baseEntityId, postfix);
	}
	
	public Client findClientByClientId(Integer clientId, String postfix) {
		
		return mhealthClientsRepository.findClientByClientId(clientId, postfix);
		
	}
}
