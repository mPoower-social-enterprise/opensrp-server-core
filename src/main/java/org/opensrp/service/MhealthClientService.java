package org.opensrp.service;

import java.util.List;

import org.joda.time.DateTime;
import org.opensrp.repository.MhealthClientsRepository;
import org.smartregister.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MhealthClientService {
	
	private MhealthClientsRepository mhealthClientsRepository;
	
	@Autowired
	public MhealthClientService(MhealthClientsRepository mhealthClientsRepository) {
		this.mhealthClientsRepository = mhealthClientsRepository;
	}
	
	public Client addOrUpdate(Client client, String district, String division, String branch, String postfix) {
		if (client == null || client.getBaseEntityId() == null) {
			throw new IllegalArgumentException("Not a valid Client");
		}
		
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
		
		return client;
	}
	
	public Long findClientIdByBaseEntityId(String baseEntityId, String postfix) {
		
		return mhealthClientsRepository.findClientIdByBaseEntityId(baseEntityId, postfix);
	}
	
	public Client findClientByClientId(Long clientId, String postfix) {
		
		return mhealthClientsRepository.findClientByClientId(clientId, postfix);
		
	}
	
	public List<Client> findByBaseEntityIds(List<String> baseEntityIds, String postfix) {
		return mhealthClientsRepository.findByBaseEntityIds(baseEntityIds, postfix);
	}
	
	public Client findByBaseEntityId(String baseEntityId, String postfix) {
		return mhealthClientsRepository.findByBaseEntityId(baseEntityId, postfix);
	}
}
