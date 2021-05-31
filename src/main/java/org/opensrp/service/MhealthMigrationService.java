package org.opensrp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.domain.postgres.MhealthMigration;
import org.opensrp.domain.postgres.MhealthPractitionerLocation;
import org.opensrp.repository.MhealthMigrationRepository;
import org.smartregister.domain.Address;
import org.smartregister.domain.Client;
import org.smartregister.domain.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MhealthMigrationService {
	
	private MhealthMigrationRepository mhealthMigrationRepository;
	
	private MhealthEventService eventService;
	
	private MhealthClientService clientService;
	
	private final static String HOUSEHOLD = "HH";
	
	private final static String MEMBER = "Member";
	
	public enum MigrationStatus {
		PENDING, REJECT, ACCEPT
	}
	
	@Autowired
	public MhealthMigrationService(MhealthMigrationRepository mhealthMigrationRepository, MhealthClientService clientService,
	    MhealthEventService eventService) {
		this.mhealthMigrationRepository = mhealthMigrationRepository;
		this.clientService = clientService;
		this.eventService = eventService;
		
	}
	
	public Integer addMigration(MhealthMigration migration) {
		return mhealthMigrationRepository.addMigration(migration);
	}
	
	public List<String> getMigratedList(String provider, String type, Long timestamp) {
		return mhealthMigrationRepository.getMigratedList(provider, type, timestamp);
	}
	
	public List<String> getRejectedList(String provider, String type, Long timestamp) {
		return mhealthMigrationRepository.getRejectedList(provider, type, timestamp);
	}
	
	public MhealthMigration findMigrationById(Long id) {
		return mhealthMigrationRepository.findMigrationById(id);
	}
	
	public List<MhealthMigration> findMigrationByIdRelationId(String relationalId, String status) {
		return mhealthMigrationRepository.findMigrationByIdRelationId(relationalId, status);
	}
	
	public Integer updateMigration(MhealthMigration migration, String baseEntityId) {
		return mhealthMigrationRepository.updateMigration(migration, baseEntityId);
	}
	
	public Integer updateMigrationStatusById(Long id, String status) {
		return mhealthMigrationRepository.updateMigrationStatusById(id, status);
	}
	
	public Integer updateMigrationStatusByRelationalId(String relationalId, String status) {
		return mhealthMigrationRepository.updateMigrationStatusByRelationalId(relationalId, status);
	}
	
	public MhealthMigration findFirstMigrationBybaseEntityId(String baseEntityId) {
		return mhealthMigrationRepository.findFirstMigrationBybaseEntityId(baseEntityId);
	}
	
	@Transactional
	public boolean migrate(Client inClient, JSONObject syncData, MhealthPractitionerLocation inUserLocation,
	                       MhealthPractitionerLocation outUserLocation, String type)
	    throws JSONException {
		boolean returnValue = true;
		try {
			Map<String, List<String>> inClientrelationships = inClient.getRelationships();
			String inClientRelationalId = "";
			if (inClientrelationships.containsKey("family")) {
				inClientRelationalId = inClientrelationships.get("family").get(0);
			} else if (inClientrelationships.containsKey("family_head")) {
				inClientRelationalId = inClientrelationships.get("family_head").get(0);
			}
			
			JSONArray clients = new JSONArray(syncData.getString("clients"));
			JSONObject firstClient = clients.getJSONObject(0);
			String baseEntityId = inClient.getBaseEntityId();
			JSONObject attributes = firstClient.getJSONObject("attributes");
			JSONObject identifiers = firstClient.getJSONObject("identifiers");
			String outPostfix = outUserLocation.getPostFix();
			Client getClient = clientService.findByBaseEntityId(baseEntityId, outPostfix);
			
			Map<String, List<String>> outClientRelationships = getClient.getRelationships();
			String outClientRelationalId = "";
			if (outClientRelationships.containsKey("family")) {
				outClientRelationalId = outClientRelationships.get("family").get(0);
			} else if (outClientRelationships.containsKey("family_head")) {
				outClientRelationalId = outClientRelationships.get("family_head").get(0);
			}
			
			getClient.getAddresses().remove(0);
			Address clientAddres = inClient.getAddress("usual_residence");
			List<Address> clientNewAddres = new ArrayList<>();
			getClient.getAddresses().clear();
			clientNewAddres.add(clientAddres);
			getClient.setAddresses(clientNewAddres);
			Map<String, Object> att = getClient.getAttributes();
			for (int i = 0; i < attributes.names().length(); i++) {
				att.put(attributes.names().getString(i), attributes.get(attributes.names().getString(i)));
				
			}
			getClient.withAttributes(att);
			for (int i = 0; i < identifiers.names().length(); i++) {
				getClient.addIdentifier(identifiers.names().getString(i),
				    identifiers.get(identifiers.names().getString(i)) + "");
			}
			
			getClient.withRelationships(inClient.getRelationships());
			inUserLocation.setPostFix(outPostfix);
			Client outClient = clientService.findByBaseEntityId(baseEntityId, outPostfix);
			clientService.addOrUpdate(getClient, inUserLocation);
			if (type.equalsIgnoreCase(HOUSEHOLD)) {
				migrateHHEventsClients(inClient, outClient, outClientRelationalId, inUserLocation, outUserLocation);
				MhealthMigration migration = setMigration(inClient, outClient, inClient, outClient, type, outUserLocation,
				    inUserLocation, "no");
				mhealthMigrationRepository.addMigration(migration);
				
			} else if (type.equalsIgnoreCase(MEMBER)) {
				Client inHhousehold = clientService.findByBaseEntityId(inClientRelationalId, inUserLocation.getPostFix());
				Client outHhousehold = clientService.findByBaseEntityId(outClientRelationalId, outUserLocation.getPostFix());
				MhealthMigration migration = setMigration(inClient, outClient, inHhousehold, outHhousehold, type,
				    outUserLocation, inUserLocation, "yes");
				mhealthMigrationRepository.addMigration(migration);
				migrateMemberEvents(inClient, inUserLocation, outUserLocation);
				
			} else {
				
			}
		}
		catch (Exception e) {
			returnValue = false;
		}
		return returnValue;
	}
	
	public void migrateHHEventsClients(Client inClient, Client outClient, String HHrelationalId,
	                                   MhealthPractitionerLocation inUserLocation,
	                                   MhealthPractitionerLocation outUserLocation) {
		
		List<Event> events = eventService.findEventsByBaseEntityId(inClient.getBaseEntityId(), outUserLocation.getPostFix());
		
		for (Event event : events) {
			inUserLocation.setPostFix(outUserLocation.getPostFix());
			eventService.addorUpdateEvent(event, "", inUserLocation);
		}
		List<Client> clients = clientService.findByRelationshipId(inClient.getBaseEntityId(), outUserLocation.getPostFix());
		
		for (Client client : clients) {
			client.getAddresses().remove(0);
			Address memberAddress = inClient.getAddress("usual_residence");
			List<Address> memberNewAddress = new ArrayList<>();
			client.getAddresses().clear();
			memberNewAddress.add(memberAddress);
			client.setAddresses(memberNewAddress);
			Client outMember = clientService.findByBaseEntityId(client.getBaseEntityId(), outUserLocation.getPostFix());
			
			clientService.addOrUpdate(client, inUserLocation);
			List<Event> clinetsEvents = eventService.findEventsByBaseEntityId(client.getBaseEntityId(),
			    outUserLocation.getPostFix());
			
			MhealthMigration migration = setMigration(outMember, outMember, inClient, inClient, "Member", outUserLocation,
			    inUserLocation, "no");
			mhealthMigrationRepository.addMigration(migration);
			for (Event event : clinetsEvents) {
				eventService.addorUpdateEvent(event, "", inUserLocation);
			}
			
		}
		
	}
	
	public void migrateMemberEvents(Client c, MhealthPractitionerLocation newUserLocation,
	                                MhealthPractitionerLocation oldUserLocation) {
		newUserLocation.setPostFix(oldUserLocation.getPostFix());
		List<Event> events = eventService.findEventsByBaseEntityId(c.getBaseEntityId(), oldUserLocation.getPostFix());
		for (Event event : events) {
			eventService.addorUpdateEvent(event, "", newUserLocation);
		}
		
	}
	
	public MhealthMigration setMigration(Client inClient, Client outClient, Client inHHousehold, Client outHHousehold,
	                                     String type, MhealthPractitionerLocation outUserLocation,
	                                     
	                                     MhealthPractitionerLocation inUserLocation, String isMember) {
		Address inAddressa;
		if (isMember.equalsIgnoreCase("Yes")) {
			inAddressa = inClient.getAddress("usual_residence");
		} else {
			inAddressa = inHHousehold.getAddress("usual_residence");
		}
		MhealthMigration migration = new MhealthMigration();
		migration.setDistrictIn(inAddressa.getCountyDistrict());
		migration.setDivisionIn(inAddressa.getStateProvince());
		migration.setVillageIn(inAddressa.getCityVillage());
		migration.setUpazilaIn(inAddressa.getAddressField("address2"));
		migration.setPourasavaIn(inAddressa.getAddressField("address3"));
		migration.setUnionIn(inAddressa.getAddressField("address1"));
		migration.setVillageIDIn(inAddressa.getAddressField("address8"));
		Address outAddress = outClient.getAddress("usual_residence");
		migration.setDistrictOut(outAddress.getCountyDistrict());
		migration.setDivisionOut(outAddress.getStateProvince());
		migration.setVillageOut(outAddress.getCityVillage());
		migration.setUpazilaOut(outAddress.getAddressField("address2"));
		migration.setPourasavaOut(outAddress.getAddressField("address3"));
		migration.setUnionOut(outAddress.getAddressField("address1"));
		migration.setVillageIDOut(outAddress.getAddressField("address8"));
		migration.setMemberName(inClient.getFirstName());
		migration.setMemberContact(inClient.getAttribute("Mobile_Number") + "");
		migration.setMemberIDIn(inClient.getIdentifier("opensrp_id"));
		
		migration.setMemberIDOut(outClient.getIdentifier("opensrp_id"));
		migration.setSKIn(inUserLocation.getUsername());
		migration.setSKOut(outUserLocation.getUsername());
		String ssOut = outClient.getAttribute("SS_Name") + "";
		String ssIn = inClient.getAttribute("SS_Name") + "";
		if (StringUtils.isBlank(ssOut) || ssOut.equalsIgnoreCase("null")) {
			ssOut = "";
		}
		if (StringUtils.isBlank(ssIn) || ssIn.equalsIgnoreCase("null")) {
			ssIn = "";
		}
		migration.setSSIn(ssIn);
		migration.setSSOut(ssOut);
		
		Map<String, List<String>> inClientRelationships = inClient.getRelationships();
		String inClientRelationalId = "";
		if (inClientRelationships.containsKey("family")) {
			inClientRelationalId = inClientRelationships.get("family").get(0);
		} else if (inClientRelationships.containsKey("family_head")) {
			inClientRelationalId = inClientRelationships.get("family_head").get(0);
		}
		
		migration.setRelationalIdIn(inClientRelationalId);
		
		Map<String, List<String>> outClientRelationships = outClient.getRelationships();
		String outClientRelationalId = "";
		if (outClientRelationships.containsKey("family")) {
			outClientRelationalId = outClientRelationships.get("family").get(0);
		} else if (outClientRelationships.containsKey("family_head")) {
			outClientRelationalId = outClientRelationships.get("family_head").get(0);
		}
		
		migration.setRelationalIdOut(outClientRelationalId);
		
		if (outHHousehold != null) {
			migration.setHHNameOut(outHHousehold.getFirstName());
			migration.setHHContactOut(outHHousehold.getAttribute("HOH_Phone_Number") + "");
			migration.setNumberOfMemberOut(outHHousehold.getAttribute("Number_of_HH_Member") + "");
		}
		if (inHHousehold != null) {
			migration.setHHNameIn(inHHousehold.getFirstName());
			migration.setHHContactIn(inHHousehold.getAttribute("HOH_Phone_Number") + "");
			migration.setNumberOfMemberIn(inHHousehold.getAttribute("Number_of_HH_Member") + "");
		}
		
		Map<String, Object> outClientAttributes = outClient.getAttributes();
		
		if (isMember.equalsIgnoreCase("no")) {
			migration.setRelationWithHHOut(inClient.getAttribute("Relation_with_HOH") + "");
		} else {
			migration.setRelationWithHHOut(outClientAttributes.get("Relation_with_HOH") + "");
		}
		migration.setRelationWithHHIn(inClient.getAttribute("Relation_with_HOH") + "");
		migration.setBranchIDIn(inUserLocation.getBranch());
		migration.setBranchIDOut(outUserLocation.getBranch());
		
		migration.setStatus(MigrationStatus.PENDING.name());
		
		if (inClient.getBirthdate() != null) {
			migration.setDob(inClient.getBirthdate().toDate());
		}
		migration.setMigrationDate(new DateTime().toDate());
		migration.setMemberType(type);
		
		migration.setIsMember(isMember);
		
		migration.setBaseEntityId(inClient.getBaseEntityId());
		migration.setDistrictIdOut(outUserLocation.getPostFix());
		migration.setDistrictIdIn(inUserLocation.getPostFix());
		
		migration.setDivisionIdOut(outUserLocation.getDivision());
		migration.setDivisionIdIn(inUserLocation.getDivision());
		migration.setTimestamp(System.currentTimeMillis());
		Map<String, List<String>> outClientRelationship = outClient.getRelationships();
		String motherId = "";
		if (outClientRelationship.containsKey("mother")) {
			motherId = outClientRelationship.get("mother").get(0);
		}
		migration.setMotherId(motherId);
		return migration;
	}
	
	@Transactional
	public boolean acceptOrRejectMigration(Long id, String relationalId, String type, String status) throws JSONException {
		boolean returnValue = true;
		try {
			if (status.equalsIgnoreCase(MigrationStatus.ACCEPT.name())) {
				if (type.equalsIgnoreCase(HOUSEHOLD)) {
					mhealthMigrationRepository.updateMigrationStatusById(id, MigrationStatus.ACCEPT.name());
					mhealthMigrationRepository.updateMigrationStatusByRelationalId(relationalId,
					    MigrationStatus.ACCEPT.name());
				} else if (type.equalsIgnoreCase(MEMBER)) {
					mhealthMigrationRepository.updateMigrationStatusById(id, MigrationStatus.ACCEPT.name());
				}
				
			} else if (status.equalsIgnoreCase(MigrationStatus.REJECT.name())) {
				MhealthMigration migration = mhealthMigrationRepository.findMigrationById(id);
				
				if (type.equalsIgnoreCase(HOUSEHOLD)) {
					Client household = clientService.findByBaseEntityId(migration.getBaseEntityId(),
					    migration.getDistrictIdIn());
					household.getAddresses().clear();
					household.addAddress(clientService.setAddress(household, migration));
					household.addIdentifier("opensrp_id", migration.getMemberIDOut());
					Map<String, Object> att = household.getAttributes();
					att.put("SS_Name", migration.getSSOut());
					att.put("village_id", migration.getVillageIDOut());
					household.getRelationships().clear();
					List<String> family = new ArrayList<>();
					family.add(migration.getRelationalIdOut());
					Map<String, List<String>> relationships = new HashMap<>();
					relationships.put("family_head", family);
					relationships.put("primary_caregiver", family);
					household.setRelationships(relationships);
					
					rejectEvent(household, migration);
					List<MhealthMigration> migrations = mhealthMigrationRepository.findMigrationByIdRelationId(relationalId,
					    MigrationStatus.PENDING.name());
					String districtId = migration.getDistrictIdOut().replace("_", "");
					MhealthPractitionerLocation userLocation = new MhealthPractitionerLocation();
					userLocation.setBranch(migration.getBranchIDOut());
					userLocation.setDistrict(districtId);
					userLocation.setDivision(migration.getDivisionIdOut());
					userLocation.setPostFix(migration.getDistrictIdIn());
					clientService.addOrUpdate(household, userLocation);
					mhealthMigrationRepository.updateMigrationStatusById(id, MigrationStatus.REJECT.name());
					for (MhealthMigration migration2 : migrations) {
						
						Client c = clientService.findByBaseEntityId(migration2.getBaseEntityId(),
						    migration2.getDistrictIdIn());
						rejectClient(c, migration2);
					}
					
				} else if (type.equalsIgnoreCase(MEMBER)) {
					
					Client member = clientService.findByBaseEntityId(migration.getBaseEntityId(),
					    migration.getDistrictIdIn());
					rejectClient(member, migration);
				}
			}
		}
		catch (Exception e) {
			returnValue = false;
		}
		return returnValue;
	}
	
	private void rejectClient(Client c, MhealthMigration migration) {
		
		c.getAddresses().clear();
		c.addAddress(clientService.setAddress(c, migration));
		Map<String, Object> att = c.getAttributes();
		att.put("Relation_with_HOH", migration.getRelationWithHHOut());
		c.addIdentifier("opensrp_id", migration.getMemberIDOut());
		c.getRelationships().clear();
		List<String> family = new ArrayList<>();
		family.add(migration.getRelationalIdOut());
		List<String> mother = new ArrayList<>();
		mother.add(migration.getMotherId());
		Map<String, List<String>> relationships = new HashMap<>();
		relationships.put("family", family);
		relationships.put("mother", mother);
		c.setRelationships(relationships);
		
		String districtId = migration.getDistrictIdOut().replace("_", "");
		MhealthPractitionerLocation userLocation = new MhealthPractitionerLocation();
		userLocation.setBranch(migration.getBranchIDOut());
		userLocation.setDistrict(districtId);
		userLocation.setDivision(migration.getDivisionIdOut());
		userLocation.setPostFix(migration.getDistrictIdIn());
		
		int updateMigrationStatusById = mhealthMigrationRepository.updateMigrationStatusById(migration.getId(),
		    MigrationStatus.REJECT.name());
		Client updatedClient = null;
		if (updateMigrationStatusById == 1) {
			updatedClient = clientService.addOrUpdate(c, userLocation);
		}
		if (updatedClient != null) {
			rejectEvent(c, migration);
		}
		
	}
	
	private void rejectEvent(Client c, MhealthMigration migration) {
		List<Event> events = eventService.findEventsByBaseEntityId(c.getBaseEntityId(), migration.getDistrictIdIn());
		String districtId = migration.getDistrictIdOut().replace("_", "");
		MhealthPractitionerLocation userLocation = new MhealthPractitionerLocation();
		userLocation.setBranch(migration.getBranchIDOut());
		userLocation.setDistrict(districtId);
		userLocation.setDivision(migration.getDivisionIdOut());
		userLocation.setPostFix(migration.getDistrictIdIn());
		
		for (Event event : events) {
			eventService.addorUpdateEvent(event, "", userLocation);
		}
	}
}
