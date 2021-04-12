package org.opensrp.repository.postgres;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.opensrp.common.AllConstants;
import org.opensrp.domain.postgres.EventMetadataExample;
import org.opensrp.domain.postgres.EventMetadataExample.Criteria;
import org.opensrp.domain.postgres.MhealthEvent;
import org.opensrp.domain.postgres.MhealthEventMetadata;
import org.opensrp.repository.MhealthEventsRepository;
import org.opensrp.repository.postgres.mapper.custom.CustomEventMetadataMapper;
import org.opensrp.repository.postgres.mapper.custom.CustomMhealthEventMapper;
import org.opensrp.repository.postgres.mapper.custom.CustomMhealthEventMetadataMapper;
import org.smartregister.domain.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class MhealthEventsRepositoryImpl extends BaseRepositoryImpl<Event> implements MhealthEventsRepository {
	
	@Autowired
	private CustomEventMetadataMapper eventMetadataMapper;
	
	@Autowired
	private CustomMhealthEventMapper customMhealthEventMapper;
	
	@Autowired
	private CustomMhealthEventMetadataMapper customMhealthEventMetadataMapper;
	
	@Override
	public Event get(String id, String postfix) {
		if (StringUtils.isBlank(id)) {
			return null;
		}
		org.opensrp.domain.postgres.Event pgEvent = eventMetadataMapper.selectByDocumentId(id);
		
		return convert(pgEvent);
	}
	
	@Transactional
	@Override
	public void add(Event entity, String postfix, String district, String division, String branch) {
		if (entity == null || entity.getBaseEntityId() == null) {
			return;
		}
		
		if (retrievePrimaryKey(entity) != null) { // Event already added
			throw new IllegalArgumentException("Event exists");
		}
		
		if (entity.getId() == null || entity.getId().isEmpty())
			entity.setId(UUID.randomUUID().toString());
		setRevision(entity);
		
		MhealthEvent pgEvent = convert(entity, null);
		if (pgEvent == null) {
			throw new IllegalStateException();
		}
		
		pgEvent.setDistrict(district);
		pgEvent.setDivision(division);
		pgEvent.setBranch(branch);
		pgEvent.setVillage("");
		pgEvent.setProviderId(entity.getProviderId());
		pgEvent.setBaseEntityId(entity.getBaseEntityId());
		pgEvent.setFormSubmissionId(entity.getFormSubmissionId());
		pgEvent.setEventType(entity.getEventType());
		
		int rowsAffected = customMhealthEventMapper.insertSelectiveAndSetId(pgEvent);
		if (rowsAffected < 1 || pgEvent.getId() == null) {
			throw new IllegalStateException();
		}
		
		updateServerVersion(pgEvent, entity, postfix);
		
		MhealthEventMetadata eventMetadata = createMetadata(entity, pgEvent.getId());
		if (eventMetadata != null) {
			eventMetadata.setDistrict(district);
			eventMetadata.setDivision(division);
			eventMetadata.setBranch(branch);
			eventMetadata.setVillage("");
			customMhealthEventMetadataMapper.insertSelective(eventMetadata);
		}
		
	}
	
	private void updateServerVersion(org.opensrp.domain.postgres.MhealthEvent pgEvent, Event entity, String postfix) {
		long serverVersion = customMhealthEventMapper.selectServerVersionByPrimaryKey(pgEvent.getId(), postfix);
		entity.setServerVersion(serverVersion);
		pgEvent.setJson(entity);
		pgEvent.setServerVersion(null);
		int rowsAffected = customMhealthEventMapper.updateByPrimaryKeySelective(pgEvent);
		if (rowsAffected < 1) {
			throw new IllegalStateException();
		}
	}
	
	@Override
	public void update(Event entity, String postfix, String district, String division, String branch) {
		update(entity, false, postfix, district, division, branch);
	}
	
	@Transactional
	@Override
	public void update(Event entity, boolean allowArchived, String postfix, String district, String division,
	                   String branch) {
		if (entity == null || entity.getBaseEntityId() == null) {
			throw new IllegalStateException();
		}
		
		Long id = retrievePrimaryKey(entity, allowArchived, postfix);
		if (id == null) { // Event not added
			throw new IllegalStateException();
		}
		
		setRevision(entity);
		
		org.opensrp.domain.postgres.MhealthEvent pgEvent = convert(entity, id);
		if (pgEvent == null) {
			throw new IllegalStateException();
		}
		pgEvent.setDistrict(district);
		pgEvent.setDivision(division);
		pgEvent.setBranch(branch);
		pgEvent.setVillage("");
		pgEvent.setProviderId(entity.getProviderId());
		pgEvent.setBaseEntityId(entity.getBaseEntityId());
		pgEvent.setFormSubmissionId(entity.getFormSubmissionId());
		pgEvent.setEventType(entity.getEventType());
		int rowsAffected = customMhealthEventMapper.updateByPrimaryKeyAndGenerateServerVersion(pgEvent);
		if (rowsAffected < 1) {
			throw new IllegalStateException();
		}
		
		updateServerVersion(pgEvent, entity, postfix);
		
		MhealthEventMetadata eventMetadata = createMetadata(entity, id);
		if (eventMetadata == null) {
			throw new IllegalStateException();
		}
		
		EventMetadataExample eventMetadataExample = new EventMetadataExample();
		Criteria criteria = eventMetadataExample.createCriteria();
		criteria.andEventIdEqualTo(id);
		if (!allowArchived) {
			criteria.andDateDeletedIsNull();
		}
		eventMetadata.setId(eventMetadataMapper.selectByExample(eventMetadataExample).get(0).getId());
		eventMetadata.setDistrict(district);
		eventMetadata.setDivision(division);
		eventMetadata.setBranch(branch);
		eventMetadata.setVillage("");
		customMhealthEventMetadataMapper.updateByPrimaryKey(eventMetadata);
		
	}
	
	/**
	 * Get the primary key of an event
	 * 
	 * @param event
	 * @param allowArchived
	 * @return the promary key
	 */
	private Long retrievePrimaryKey(Event event, boolean allowArchived, String postfix) {
		Object uniqueId = getUniqueField(event);
		if (uniqueId == null) {
			return null;
		}
		
		String documentId = uniqueId.toString();
		
		EventMetadataExample eventMetadataExample = new EventMetadataExample();
		Criteria criteria = eventMetadataExample.createCriteria();
		criteria.andDocumentIdEqualTo(documentId);
		if (!allowArchived) {
			criteria.andDateDeletedIsNull();
		}
		
		return customMhealthEventMetadataMapper.selectPrimaryKey(documentId, postfix);
		
	}
	
	@Override
	public Long retrievePrimaryKey(Event event, String postfix) {
		return retrievePrimaryKey(event, false, postfix);
	}
	
	@Override
	protected Object getUniqueField(Event t) {
		if (t == null) {
			return null;
		}
		return t.getId();
	}
	
	// Private Methods
	private Event convert(org.opensrp.domain.postgres.Event event) {
		if (event == null || event.getJson() == null || !(event.getJson() instanceof Event)) {
			return null;
		}
		return (Event) event.getJson();
	}
	
	private org.opensrp.domain.postgres.MhealthEvent convert(Event event, Long primaryKey) {
		if (event == null) {
			return null;
		}
		
		org.opensrp.domain.postgres.MhealthEvent pgEvent = new org.opensrp.domain.postgres.MhealthEvent();
		pgEvent.setId(primaryKey);
		pgEvent.setJson(event);
		
		return pgEvent;
	}
	
	private MhealthEventMetadata createMetadata(Event event, Long eventId) {
		try {
			MhealthEventMetadata eventMetadata = new MhealthEventMetadata();
			eventMetadata.setBaseEntityId(event.getBaseEntityId());
			eventMetadata.setEventId(eventId);
			eventMetadata.setDocumentId(event.getId());
			eventMetadata.setBaseEntityId(event.getBaseEntityId());
			eventMetadata.setFormSubmissionId(event.getFormSubmissionId());
			eventMetadata.setOpenmrsUuid(event.getIdentifier(AllConstants.Client.OPENMRS_UUID_IDENTIFIER_TYPE));
			eventMetadata.setEventType(event.getEventType());
			if (event.getEventDate() != null)
				eventMetadata.setEventDate(event.getEventDate().toDate());
			eventMetadata.setEntityType(event.getEntityType());
			eventMetadata.setProviderId(event.getProviderId());
			eventMetadata.setLocationId(event.getLocationId());
			eventMetadata.setTeam(event.getTeam());
			eventMetadata.setTeamId(event.getTeamId());
			eventMetadata.setServerVersion(event.getServerVersion());
			if (event.getDateCreated() != null)
				eventMetadata.setDateCreated(event.getDateCreated().toDate());
			if (event.getDateEdited() != null)
				eventMetadata.setDateEdited(event.getDateEdited().toDate());
			if (event.getDateVoided() != null)
				eventMetadata.setDateDeleted(event.getDateVoided().toDate());
			String planIdentifier = event.getDetails() != null ? event.getDetails().get("planIdentifier") : null;
			eventMetadata.setPlanIdentifier(planIdentifier);
			return eventMetadata;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	@Override
	public Integer findEventIdByFormSubmissionId(String baseEntityId, String postfix) {
		
		return customMhealthEventMapper.selectEventIdByFormSubmissionId(baseEntityId, postfix);
	}
	
	@Override
	public Event findEventByEventId(Integer eventId, String postfix) {
		org.opensrp.domain.postgres.Event pgEvent = customMhealthEventMapper.selectEventByEventId(eventId, postfix);
		if (pgEvent != null) {
			return convert(pgEvent);
		} else {
			return null;
		}
		
	}
	
	@Override
	protected Object retrievePrimaryKey(Event t) {
		
		return null;
	}
	
	@Override
	public List<Event> findByVillageIds(String providerId, List<Long> villageIds, long serverVersion, int limit,
	                                    String postfix) {
		
		return customMhealthEventMapper.selectByVillageIds(providerId, villageIds, serverVersion, limit, postfix);
	}
	
	@Override
	public List<Event> findByProvider(long serverVersion, String providerId, int limit, String postfix) {
		
		return customMhealthEventMapper.selectByProvider(serverVersion, providerId, limit, postfix);
	}
	
}
