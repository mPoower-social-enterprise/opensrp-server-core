package org.opensrp.repository.postgres;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opensrp.common.AllConstants;
import org.opensrp.domain.postgres.ClientMetadata;
import org.opensrp.domain.postgres.ClientMetadataExample;
import org.opensrp.domain.postgres.ClientMetadataExample.Criteria;
import org.opensrp.domain.postgres.MhealthClientMetadata;
import org.opensrp.domain.postgres.MhealthPractitionerLocation;
import org.opensrp.repository.MhealthClientsRepository;
import org.opensrp.repository.postgres.mapper.custom.CustomMhealthClientMapper;
import org.opensrp.repository.postgres.mapper.custom.CustomMhealthClientMetadataMapper;
import org.smartregister.domain.Address;
import org.smartregister.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class MhealthClientsRepositoryImpl extends BaseRepositoryImpl<Client> implements MhealthClientsRepository {
	
	private static Logger logger = LogManager.getLogger(MhealthClientsRepositoryImpl.class.toString());
	
	@Value("#{opensrp['address.type']}")
	private String addressType;
	
	public static String RESIDENCE = "usual_residence";
	
	@Autowired
	private CustomMhealthClientMapper customMhealthClientMapper;
	
	@Autowired
	private CustomMhealthClientMetadataMapper customMhealthClientMetadataMapper;
	
	@Override
	public Client get(String id) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	@Transactional
	public void add(Client entity, MhealthPractitionerLocation location) {
		if (entity == null || entity.getBaseEntityId() == null) {
			throw new IllegalArgumentException("Not a valid Client");
		}
		Long id = findClientIdByBaseEntityId(entity.getBaseEntityId(), location.getPostFix());
		if (id != null) { // Client already added			
			throw new IllegalArgumentException("Client exists");
		}
		
		if (entity.getId() == null || entity.getId().isEmpty())
			entity.setId(UUID.randomUUID().toString());
		
		setRevision(entity);
		
		org.opensrp.domain.postgres.MhealthClient pgClient = convert(entity, null);
		if (pgClient == null) {
			throw new IllegalStateException();
		}
		
		pgClient.setDistrict(location.getDistrict());
		pgClient.setDivision(location.getDivision());
		pgClient.setBranch(location.getBranch());
		pgClient.setBaseEntityId(entity.getBaseEntityId());
		Map<String, String> addressFields = entity.getAddresses().get(0).getAddressFields();
		if (addressFields != null) {
			if (addressFields.containsKey("address8")) {
				pgClient.setVillage(addressFields.get("address8"));
			}
		}
		
		int rowsAffected = customMhealthClientMapper.insertSelectiveAndSetId(pgClient);
		if (rowsAffected < 1 || pgClient.getId() == null) {
			throw new IllegalStateException();
		}
		
		updateServerVersion(pgClient, entity, location.getPostFix());
		
		MhealthClientMetadata clientMetadata = createMetadata(entity, pgClient.getId());
		if (clientMetadata != null) {
			clientMetadata.setDistrict(location.getDistrict());
			clientMetadata.setDivision(location.getDivision());
			clientMetadata.setBranch(location.getBranch());
			customMhealthClientMetadataMapper.insertSelective(clientMetadata);
		}
	}
	
	private void updateServerVersion(org.opensrp.domain.postgres.MhealthClient pgClient, Client entity, String postfix) {
		long serverVersion = customMhealthClientMapper.selectServerVersionByPrimaryKey(pgClient.getId(), postfix);
		entity.setServerVersion(serverVersion);
		pgClient.setJson(entity);
		pgClient.setServerVersion(null);
		int rowsAffected = customMhealthClientMapper.updateByPrimaryKeySelective(pgClient);
		if (rowsAffected < 1) {
			throw new IllegalStateException();
		}
	}
	
	@Override
	public void update(Client entity, MhealthPractitionerLocation location) {
		update(entity, false, location);
	}
	
	@Transactional
	@Override
	public void update(Client entity, boolean allowArchived, MhealthPractitionerLocation location) {
		if (entity == null || entity.getBaseEntityId() == null) {
			throw new IllegalArgumentException("Not a valid Client");
		}
		
		Long id = findClientIdByBaseEntityId(entity.getBaseEntityId(), location.getPostFix());
		if (id == null) { // Client not added
			throw new IllegalStateException();
		}
		
		setRevision(entity);
		
		org.opensrp.domain.postgres.MhealthClient pgClient = convert(entity, id);
		if (pgClient == null) {
			throw new IllegalStateException();
		}
		pgClient.setDistrict(location.getDistrict());
		pgClient.setDivision(location.getDivision());
		pgClient.setBranch(location.getBranch());
		pgClient.setBaseEntityId(entity.getBaseEntityId());
		pgClient.setServerVersion(entity.getServerVersion());
		Map<String, String> addressFields = entity.getAddresses().get(0).getAddressFields();
		if (addressFields != null) {
			if (addressFields.containsKey("address8")) {
				pgClient.setVillage(addressFields.get("address8"));
			}
		}
		int rowsAffected = customMhealthClientMapper.updateByPrimaryKeyAndGenerateServerVersion(pgClient);
		if (rowsAffected < 1) {
			throw new IllegalStateException();
		}
		
		updateServerVersion(pgClient, entity, location.getPostFix());
		
		MhealthClientMetadata clientMetadata = createMetadata(entity, id);
		if (clientMetadata == null) {
			throw new IllegalStateException();
		}
		
		ClientMetadataExample clientMetadataExample = new ClientMetadataExample();
		Criteria criteria = clientMetadataExample.createCriteria();
		criteria.andClientIdEqualTo(id);
		if (!allowArchived) {
			criteria.andDateDeletedIsNull();
		}
		ClientMetadata metadata = findByClientId(id, location.getPostFix());
		clientMetadata.setId(metadata.getId());
		clientMetadata.setDateCreated(metadata.getDateCreated());
		clientMetadata.setDistrict(location.getDistrict());
		clientMetadata.setDivision(location.getDivision());
		clientMetadata.setBranch(location.getBranch());
		customMhealthClientMetadataMapper.updateByPrimaryKey(clientMetadata);
	}
	
	@Override
	public List<Client> convert(List<org.opensrp.domain.postgres.Client> clients) {
		if (clients == null || clients.isEmpty()) {
			return new ArrayList<>();
		}
		
		List<Client> convertedClients = new ArrayList<>();
		for (org.opensrp.domain.postgres.Client client : clients) {
			Client convertedClient = convert(client);
			if (convertedClient != null) {
				convertedClients.add(convertedClient);
			}
		}
		
		return convertedClients;
	}
	
	private org.opensrp.domain.postgres.MhealthClient convert(Client client, Long primaryKey) {
		if (client == null) {
			return null;
		}
		
		org.opensrp.domain.postgres.MhealthClient pgClient = new org.opensrp.domain.postgres.MhealthClient();
		pgClient.setId(primaryKey);
		pgClient.setJson(client);
		
		return pgClient;
	}
	
	private MhealthClientMetadata createMetadata(Client client, Long clientId) {
		try {
			
			MhealthClientMetadata clientMetadata = new MhealthClientMetadata();
			clientMetadata.setDocumentId(client.getId());
			clientMetadata.setBaseEntityId(client.getBaseEntityId());
			if (client.getBirthdate() != null) {
				clientMetadata.setBirthDate(client.getBirthdate().toDate());
			}
			clientMetadata.setClientId(clientId);
			clientMetadata.setFirstName(client.getFirstName());
			clientMetadata.setMiddleName(client.getMiddleName());
			clientMetadata.setLastName(client.getLastName());
			
			if (clientId != null) {
				clientMetadata.setDateEdited(new Date());
			}
			
			Address requiredAddress = new Address();
			
			for (Address address : client.getAddresses()) {
				if (address.getAddressType().equalsIgnoreCase(RESIDENCE)) {
					requiredAddress = address;
					break;
				}
			}
			
			if (requiredAddress != null) {
				clientMetadata.setAddress1(requiredAddress.getAddressField("address1"));
				clientMetadata.setAddress2(requiredAddress.getAddressField("address2"));
				clientMetadata.setAddress3(requiredAddress.getCityVillage());
				if (requiredAddress.getAddressField("address8") != null) {
					clientMetadata.setVillageId(Long.valueOf(requiredAddress.getAddressField("address8")));
					
				}
			}
			
			/*String relationalId = null;
			Map<String, List<String>> relationShips = client.getRelationships();
			if (relationShips != null && !relationShips.isEmpty()) {
				for (Map.Entry<String, List<String>> maEntry : relationShips.entrySet()) {
					List<String> values = maEntry.getValue();
					if (values != null && !values.isEmpty()) {
						relationalId = values.get(0);
						break;
					}
				}
			}*/
			String relationalId = null;
			
			Map<String, List<String>> relationShips = client.getRelationships();
			
			if (relationShips.containsKey("family")) {
				relationalId = relationShips.get("family").get(0);
			} else if (relationShips.containsKey("family_head")) {
				relationalId = relationShips.get("family_head").get(0);
			}
			clientMetadata.setRelationalId(relationalId);
			
			String uniqueId = null;
			String openmrsUUID = null;
			Map<String, String> identifiers = client.getIdentifiers();
			if (identifiers != null && !identifiers.isEmpty()) {
				for (Map.Entry<String, String> entry : identifiers.entrySet()) {
					String value = entry.getValue();
					if (StringUtils.isNotBlank(value)) {
						if (AllConstants.Client.OPENMRS_UUID_IDENTIFIER_TYPE.equalsIgnoreCase(entry.getKey())) {
							openmrsUUID = value;
						} else {
							uniqueId = value;
						}
					}
				}
			}
			
			clientMetadata.setUniqueId(uniqueId);
			clientMetadata.setOpenmrsUuid(openmrsUUID);
			clientMetadata.setServerVersion(client.getServerVersion());
			if (client.getDateVoided() != null)
				clientMetadata.setDateDeleted(client.getDateVoided().toDate());
			Object residence = client.getAttribute(RESIDENCE);
			if (residence != null)
				clientMetadata.setResidence(residence.toString());
			clientMetadata.setLocationId(client.getLocationId());
			clientMetadata.setClientType(client.getClientType());
			return clientMetadata;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	@Override
	protected Object getUniqueField(Client t) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Long findClientIdByBaseEntityId(String baseEntityId, String postfix) {
		
		return customMhealthClientMapper.selectClientIdByBaseEntityId(baseEntityId, postfix);
	}
	
	@Override
	public Client findClientByClientId(Long clientId, String postfix) {
		
		org.opensrp.domain.postgres.Client pgClient = customMhealthClientMapper.selectClientByClientId(clientId, postfix);
		if (pgClient != null) {
			return convert(pgClient);
		}
		return null;
		
	}
	
	private Client convert(org.opensrp.domain.postgres.Client client) {
		if (client == null || client.getJson() == null || !(client.getJson() instanceof Client)) {
			return null;
		}
		return (Client) client.getJson();
	}
	
	@Override
	protected Object retrievePrimaryKey(Client t) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Client findByBaseEntityId(String baseEntityId, String postfix) {
		org.opensrp.domain.postgres.Client pgClient = customMhealthClientMapper.selectByBaseEntityId(baseEntityId, postfix);
		if (pgClient != null) {
			return convert(pgClient);
		}
		return null;
	}
	
	@Override
	public ClientMetadata findByClientId(Long clientId, String postfix) {
		
		return customMhealthClientMetadataMapper.selectByClientId(clientId, postfix);
	}
	
	@Override
	public List<Client> findByBaseEntityIds(List<String> baseEntityIds, String postfix) {
		List<org.opensrp.domain.postgres.Client> clients = customMhealthClientMapper.selectByBaseEntityIds(baseEntityIds,
		    postfix);
		return convert(clients);
		
	}
	
	@Override
	public List<Client> findByRelationshipId(String relationshipId, String postfix) {
		List<org.opensrp.domain.postgres.Client> clients = customMhealthClientMapper.selectByRelationshipId(relationshipId,
		    postfix);
		return convert(clients);
	}
	
	@Override
	public List<Client> searchClientForMigration(int villageId, String gender, Integer startAge, Integer endAge, String type,
	                                             String postfix) {
		if (villageId == 0) {
			throw new IllegalArgumentException();
		}
		List<org.opensrp.domain.postgres.Client> clients = customMhealthClientMapper.selectClientForMigration(villageId,
		    gender, startAge, endAge, type, postfix);
		return convert(clients);
	}
	
}
