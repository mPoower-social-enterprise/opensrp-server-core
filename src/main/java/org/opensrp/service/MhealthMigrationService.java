package org.opensrp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.domain.postgres.MhealthEventMetadata;
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
	
	private PractitionerLocationService PractionerDetailsService;
	
	public enum MigrationStatus {
		PENDING, REJECT, ACCEPT
	}
	
	@Autowired
	public MhealthMigrationService(MhealthMigrationRepository mhealthMigrationRepository, MhealthClientService clientService,
	    MhealthEventService eventService, PractitionerLocationService PractionerDetailsService) {
		this.mhealthMigrationRepository = mhealthMigrationRepository;
		this.clientService = clientService;
		this.eventService = eventService;
		this.PractionerDetailsService = PractionerDetailsService;
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
	
	public boolean migrate(ArrayList<Client> clients, JSONObject syncData, String district, String inProvider,
	                       String division, String branch, String type)
	    throws JSONException {
		
		MhealthPractitionerLocation newUserLocation = PractionerDetailsService.generatePostfixAndLocation(inProvider, "", "",
		    "");
		
		Client client = clients.get(0);
		Map<String, List<String>> inHHrelationShips = client.getRelationships();
		String inHHrelationalId = "";
		if (inHHrelationShips.containsKey("family")) {
			inHHrelationalId = inHHrelationShips.get("family").get(0);
		} else if (inHHrelationShips.containsKey("family_head")) {
			inHHrelationalId = inHHrelationShips.get("family_head").get(0);
		}
		
		JSONArray cls = new JSONArray(syncData.getString("clients"));
		JSONObject json = cls.getJSONObject(0);
		String baseEntityId = client.getBaseEntityId();
		JSONObject attributes = json.getJSONObject("attributes");
		JSONObject identifiers = json.getJSONObject("identifiers");
		String ssIn = "";
		if (attributes.has("SS_Name")) {
			ssIn = attributes.getString("SS_Name");
		}
		MhealthEventMetadata mhealthEventMetadata = eventService.findFirstEventMetadata(baseEntityId, "_" + district);
		MhealthMigration existingMigration = mhealthMigrationRepository.findFirstMigrationBybaseEntityId(baseEntityId);
		
		String outProvider = "";
		if (existingMigration != null) {
			outProvider = existingMigration.getSKIn();
		} else {
			outProvider = mhealthEventMetadata.getProviderId();
		}
		MhealthPractitionerLocation oldUserLocation = PractionerDetailsService.generatePostfixAndLocation(outProvider,
		    district, division, "");
		oldUserLocation.setBranch(mhealthEventMetadata.getBranch());
		
		String oldTable = oldUserLocation.getPostFix();
		Client c = clientService.findByBaseEntityId(baseEntityId, oldTable);
		
		Address otuClientAddres = c.getAddress("usual_residence");
		
		Map<String, Object> otuClientAtt = c.getAttributes();
		String outMemberId = c.getIdentifier("opensrp_id");
		String ssOut = c.getAttribute("SS_Name") + "";
		Map<String, List<String>> outrelationShips = c.getRelationships();
		String outHHrelationalId = "";
		if (outrelationShips.containsKey("family")) {
			outHHrelationalId = outrelationShips.get("family").get(0);
		} else if (outrelationShips.containsKey("family_head")) {
			outHHrelationalId = outrelationShips.get("family_head").get(0);
		}
		
		c.getAddresses().remove(0);
		Address clientAddres = client.getAddress("usual_residence");
		List<Address> clientNewAddres = new ArrayList<>();
		c.getAddresses().clear();
		clientNewAddres.add(clientAddres);
		c.setAddresses(clientNewAddres);
		Map<String, Object> att = c.getAttributes();
		
		for (int i = 0; i < attributes.names().length(); i++) {
			att.put(attributes.names().getString(i), attributes.get(attributes.names().getString(i)));
			
		}
		
		c.withAttributes(att);
		for (int i = 0; i < identifiers.names().length(); i++) {
			c.addIdentifier(identifiers.names().getString(i), identifiers.get(identifiers.names().getString(i)) + "");
		}
		
		c.withRelationships(client.getRelationships());
		newUserLocation.setPostFix(oldTable);
		clientService.addOrUpdate(c, newUserLocation);
		String branchIdIn = newUserLocation.getBranch();
		String branchIdOut = oldUserLocation.getBranch();
		if (type.equalsIgnoreCase("HH")) {
			migrateHHEventsClients(c, client, otuClientAddres, otuClientAtt, outMemberId, outrelationShips, inProvider,
			    outProvider, outHHrelationalId, branchIdIn, branchIdOut, newUserLocation, oldUserLocation);
			MhealthMigration migration = setMigration(ssOut, ssIn, c, otuClientAddres, otuClientAtt, outMemberId,
			    outrelationShips, c, c, inProvider, outProvider, inHHrelationalId, outHHrelationalId, branchIdIn,
			    branchIdOut, type, oldUserLocation, newUserLocation, "no");
			mhealthMigrationRepository.addMigration(migration);
			
		} else if (type.equalsIgnoreCase("Member")) {
			
			Client inHhousehold = clientService.findByBaseEntityId(inHHrelationalId, newUserLocation.getPostFix());
			
			Client outHhousehold = clientService.findByBaseEntityId(outHHrelationalId, oldUserLocation.getPostFix());
			
			MhealthMigration migration = setMigration("", "", c, otuClientAddres, otuClientAtt, outMemberId,
			    outrelationShips, inHhousehold, outHhousehold, inProvider, outProvider, inHHrelationalId, outHHrelationalId,
			    branchIdIn, branchIdOut, type, oldUserLocation, newUserLocation, "yes");
			mhealthMigrationRepository.addMigration(migration);
			
			migrateMemberEvents(client, newUserLocation, oldUserLocation);
			
		} else {
			
		}
		
		return true;
		
	}
	
	public boolean migrateHHEventsClients(Client outClient, Client inClient, Address outAddressa,
	                                      Map<String, Object> otuClientAtt, String outClientIdentifier,
	                                      Map<String, List<String>> outrelationShips, String inProvider, String outProvider,
	                                      String HHrelationalId, String branchIdIn, String branchIdOut,
	                                      MhealthPractitionerLocation newUserLocation,
	                                      MhealthPractitionerLocation oldUserLocation) {
		
		List<Event> events = eventService.findEventsByBaseEntityId(inClient.getBaseEntityId(), oldUserLocation.getPostFix());
		
		for (Event event : events) {
			newUserLocation.setPostFix(oldUserLocation.getPostFix());
			eventService.addorUpdateEvent(event, "", newUserLocation);
			
		}
		List<Client> householdmembers = clientService.findByRelationshipId(inClient.getBaseEntityId(),
		    oldUserLocation.getPostFix());
		
		for (Client client : householdmembers) {
			
			String outMemberId = client.getIdentifier("opensrp_id");
			client.getAddresses().remove(0);
			Address memberAddress = inClient.getAddress("usual_residence");
			List<Address> memberNewAddress = new ArrayList<>();
			client.getAddresses().clear();
			memberNewAddress.add(memberAddress);
			client.setAddresses(memberNewAddress);
			clientService.addOrUpdate(client, newUserLocation);
			List<Event> clinetsEvents = eventService.findEventsByBaseEntityId(client.getBaseEntityId(),
			    oldUserLocation.getPostFix());
			
			MhealthMigration migration = setMigration("", "", client, outAddressa, otuClientAtt, outMemberId,
			    outrelationShips, outClient, outClient, inProvider, outProvider, HHrelationalId, HHrelationalId, branchIdIn,
			    branchIdOut, "Member", oldUserLocation, newUserLocation, "no");
			mhealthMigrationRepository.addMigration(migration);
			for (Event event : clinetsEvents) {
				eventService.addorUpdateEvent(event, "", newUserLocation);
			}
			
		}
		return false;
		
	}
	
	public boolean migrateMemberEvents(Client c, MhealthPractitionerLocation newUserLocation,
	                                   MhealthPractitionerLocation oldUserLocation) {
		newUserLocation.setPostFix(oldUserLocation.getPostFix());
		List<Event> events = eventService.findEventsByBaseEntityId(c.getBaseEntityId(), oldUserLocation.getPostFix());
		for (Event event : events) {
			eventService.addorUpdateEvent(event, "", newUserLocation);
		}
		
		return false;
		
	}
	
	public MhealthMigration setMigration(String ssOut, String ssIn, Client inClient, Address outAddress,
	                                     Map<String, Object> outClientAttributes, String outMemberId,
	                                     Map<String, List<String>> outClientRelationship, Client inHHousehold,
	                                     Client outHHousehold, String inProvider, String outProvider,
	                                     String inHHRelationalId, String outHHRelationalId, String branchIdIn,
	                                     String branchIdOut, String type, MhealthPractitionerLocation oldUserLocation,
	                                     MhealthPractitionerLocation newUserLocation, String isMember) {
		
		Address inAddressa = inClient.getAddress("usual_residence");
		MhealthMigration migration = new MhealthMigration();
		migration.setDistrictIn(inAddressa.getCountyDistrict());
		migration.setDivisionIn(inAddressa.getStateProvince());
		migration.setVillageIn(inAddressa.getCityVillage());
		migration.setUpazilaIn(inAddressa.getAddressField("address2"));
		migration.setPourasavaIn(inAddressa.getAddressField("address3"));
		migration.setUnionIn(inAddressa.getAddressField("address1"));
		migration.setVillageIDIn(inAddressa.getAddressField("address8"));
		
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
		migration.setMemberIDOut(outMemberId);
		migration.setSKIn(inProvider);
		migration.setSKOut(outProvider);
		migration.setSSIn(ssIn);
		migration.setSSOut(ssOut);
		migration.setRelationalIdIn(inHHRelationalId);
		migration.setRelationalIdOut(outHHRelationalId);
		
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
		System.err.println("inClient.getAttribute" + inClient.getAttribute("Relation_with_HOH"));
		if (isMember.equalsIgnoreCase("no")) {
			migration.setRelationWithHHOut(inClient.getAttribute("Relation_with_HOH") + "");
		} else {
			migration.setRelationWithHHOut(outClientAttributes.get("Relation_with_HOH") + "");
		}
		migration.setRelationWithHHIn(inClient.getAttribute("Relation_with_HOH") + "");
		migration.setBranchIDIn(branchIdIn);
		migration.setBranchIDOut(branchIdOut);
		
		migration.setStatus(MigrationStatus.PENDING.name());
		
		if (inClient.getBirthdate() != null) {
			migration.setDob(inClient.getBirthdate().toDate());
		}
		migration.setMigrationDate(new DateTime().toDate());
		migration.setMemberType(type);
		
		migration.setIsMember(isMember);
		
		migration.setBaseEntityId(inClient.getBaseEntityId());
		migration.setDistrictIdOut(oldUserLocation.getPostFix());
		migration.setDistrictIdIn(newUserLocation.getPostFix());
		
		migration.setDivisionIdOut(oldUserLocation.getDivision());
		migration.setDivisionIdIn(newUserLocation.getDivision());
		migration.setTimestamp(System.currentTimeMillis());
		
		String motherId = "";
		if (outClientRelationship.containsKey("mother")) {
			motherId = outClientRelationship.get("mother").get(0);
		}
		migration.setMotherId(motherId);
		return migration;
		
	}
}
