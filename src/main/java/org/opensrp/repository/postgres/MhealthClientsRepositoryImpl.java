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
import org.opensrp.repository.MhealthClientsRepository;
import org.opensrp.repository.postgres.mapper.custom.CustomClientMapper;
import org.opensrp.repository.postgres.mapper.custom.CustomClientMetadataMapper;
import org.opensrp.repository.postgres.mapper.custom.CustomMhealthClientMetadataMapper;
import org.smartregister.domain.Address;
import org.smartregister.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("clientsRepositoryPostgres")
public class MhealthClientsRepositoryImpl extends BaseRepositoryImpl<Client> implements MhealthClientsRepository {
	
	private static Logger logger = LogManager.getLogger(MhealthClientsRepositoryImpl.class.toString());
	
	@Value("#{opensrp['address.type']}")
	private String addressType;
	
	public static String RESIDENCE = "residence";
	
	@Autowired
	private CustomClientMetadataMapper clientMetadataMapper;
	
	@Autowired
	private CustomClientMapper clientMapper;
	
	@Autowired
	private CustomMhealthClientMetadataMapper customMhealthClientMetadataMapper;
	
	@Override
	public Client get(String id) {
		if (StringUtils.isBlank(id)) {
			return null;
		}
		
		org.opensrp.domain.postgres.Client pgClient = clientMetadataMapper.selectByDocumentId(id);
		if (pgClient == null) {
			return null;
		}
		return convert(pgClient);
	}
	
	@Override
	@Transactional
	public void add(Client entity, String table, String district, String division, String branch, String village) {
		if (entity == null || entity.getBaseEntityId() == null) {
			return;
		}
		
		if (retrievePrimaryKey(entity) != null) { // Client already added
			return;
		}
		
		if (entity.getId() == null || entity.getId().isEmpty())
			entity.setId(UUID.randomUUID().toString());
		
		setRevision(entity);
		
		org.opensrp.domain.postgres.MhealthClient pgClient = convert(entity, null);
		if (pgClient == null) {
			throw new IllegalStateException();
		}
		
		pgClient.setDistrict(district);
		pgClient.setDivision(division);
		pgClient.setBranch(branch);
		pgClient.setBaseEntityId(entity.getBaseEntityId());
		pgClient.setServerVersion(entity.getServerVersion());
		Map<String, String> addressFields = entity.getAddresses().get(0).getAddressFields();
		if (addressFields != null) {
			if (addressFields.containsKey("address8")) {
				pgClient.setVillage(addressFields.get("address8"));
			}
		}
		
		int rowsAffected = clientMapper.insertSelectiveAndSetId(pgClient);
		if (rowsAffected < 1 || pgClient.getId() == null) {
			throw new IllegalStateException();
		}
		
		updateServerVersion(pgClient, entity);
		
		MhealthClientMetadata clientMetadata = createMetadata(entity, pgClient.getId());
		if (clientMetadata != null) {
			clientMetadata.setDistrict(district);
			clientMetadata.setDivision(division);
			clientMetadata.setBranch(branch);
			clientMetadataMapper.insertSelective(clientMetadata);
		}
	}
	
	private void updateServerVersion(org.opensrp.domain.postgres.Client pgClient, Client entity) {
		long serverVersion = clientMapper.selectServerVersionByPrimaryKey(pgClient.getId());
		entity.setServerVersion(serverVersion);
		pgClient.setJson(entity);
		pgClient.setServerVersion(null);
		int rowsAffected = clientMapper.updateByPrimaryKeySelective(pgClient);
		if (rowsAffected < 1) {
			throw new IllegalStateException();
		}
	}
	
	@Override
	public void update(Client entity, String table, String district, String division, String branch, String village) {
		update(entity, false, table, district, division, branch, village);
	}
	
	@Transactional
	@Override
	public void update(Client entity, boolean allowArchived, String table, String district, String division, String branch,
	                   String village) {
		if (entity == null || entity.getBaseEntityId() == null) {
			return;
		}
		
		Long id = retrievePrimaryKey(entity, allowArchived);
		if (id == null) { // Client not added
			throw new IllegalStateException();
		}
		
		setRevision(entity);
		
		org.opensrp.domain.postgres.MhealthClient pgClient = convert(entity, id);
		if (pgClient == null) {
			throw new IllegalStateException();
		}
		pgClient.setDistrict(district);
		pgClient.setDivision(division);
		pgClient.setBranch(branch);
		pgClient.setBaseEntityId(entity.getBaseEntityId());
		pgClient.setServerVersion(entity.getServerVersion());
		Map<String, String> addressFields = entity.getAddresses().get(0).getAddressFields();
		if (addressFields != null) {
			if (addressFields.containsKey("address8")) {
				pgClient.setVillage(addressFields.get("address8"));
			}
		}
		int rowsAffected = clientMapper.updateByPrimaryKeyAndGenerateServerVersion(pgClient);
		if (rowsAffected < 1) {
			throw new IllegalStateException();
		}
		
		updateServerVersion(pgClient, entity);
		
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
		ClientMetadata metadata = clientMetadataMapper.selectByExample(clientMetadataExample).get(0);
		clientMetadata.setId(metadata.getId());
		clientMetadata.setDateCreated(metadata.getDateCreated());
		clientMetadata.setDistrict(district);
		clientMetadata.setDivision(division);
		clientMetadata.setBranch(branch);
		clientMetadataMapper.updateByPrimaryKey(clientMetadata);
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
				if (address.getAddressType().equalsIgnoreCase(this.addressType)) {
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
	protected Long retrievePrimaryKey(Client entity) {
		return retrievePrimaryKey(entity, false);
	}
	
	/**
	 * @param entity
	 * @param allowArchived
	 * @return
	 */
	private Long retrievePrimaryKey(Client entity, boolean allowArchived) {
		Object uniqueId = getUniqueField(entity);
		if (uniqueId == null) {
			return null;
		}
		
		String baseEntityId = uniqueId.toString();
		
		ClientMetadataExample clientMetadataExample = new ClientMetadataExample();
		Criteria criteria = clientMetadataExample.createCriteria();
		criteria.andBaseEntityIdEqualTo(baseEntityId);
		if (!allowArchived) {
			criteria.andDateDeletedIsNull();
		}
		
		org.opensrp.domain.postgres.Client pgClient = clientMetadataMapper.selectOne(clientMetadataExample);
		if (pgClient == null) {
			return null;
		}
		return pgClient.getId();
	}
	
	@Override
	protected Object getUniqueField(Client t) {
		if (t == null) {
			return null;
		}
		return t.getBaseEntityId();
	}
	
	@Override
	public Integer findClientIdByBaseEntityId(String baseEntityId, String postfix) {
		
		return customMhealthClientMetadataMapper.selectClientIdByBaseEntityId(baseEntityId, postfix);
	}
	
	@Override
	public Client findClientByClientId(Integer clientId, String postfix) {
		
		org.opensrp.domain.postgres.Client pgClient = customMhealthClientMetadataMapper.selectClientByClientId(clientId,
		    postfix);
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
	
}
