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
	
	public Client addOrUpdate(Client client, String district, String division, String branch) {
		if (client.getBaseEntityId() == null) {
			throw new RuntimeException("No baseEntityId");
		}
		String postfix = "_" + district;
		try {
			Long clientId = findClientIdByBaseEntityId(client.getBaseEntityId(), postfix);
			if (clientId != null) {
				Client c = findClientByClientId(clientId, postfix);
				if (c != null) {
					client.setRevision(c.getRevision());
					client.setId(c.getId());
					client.setDateEdited(DateTime.now());
					client.setDateCreated(c.getDateCreated());
					client.setServerVersion(System.currentTimeMillis());
					client.addIdentifier("OPENMRS_UUID", c.getIdentifier("OPENMRS_UUID"));
					mhealthClientsRepository.update(client, postfix, district, division, branch);
				}
				
			} else {
				client.setServerVersion(System.currentTimeMillis());
				client.setDateCreated(DateTime.now());
				
				mhealthClientsRepository.add(client, postfix, district, division, branch);
			}
		}
		catch (Exception e) {
			
		}
		
		return client;
	}
	
	public Long findClientIdByBaseEntityId(String baseEntityId, String postfix) {
		
		return mhealthClientsRepository.findClientIdByBaseEntityId(baseEntityId, postfix);
	}
	
	public Client findClientByClientId(Long clientId, String postfix) {
		
		return mhealthClientsRepository.findClientByClientId(clientId, postfix);
		
	}
}
