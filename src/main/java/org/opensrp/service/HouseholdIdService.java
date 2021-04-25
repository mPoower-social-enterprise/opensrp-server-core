package org.opensrp.service;

import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.opensrp.domain.postgres.HouseholdId;
import org.opensrp.repository.HouseholdIdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HouseholdIdService {
	
	private HouseholdIdRepository householdIdRepository;
	
	private Integer HOUSEHOLD_ID_LIMIT = 200;
	
	@Autowired
	public HouseholdIdService(HouseholdIdRepository householdIdRepository) {
		this.householdIdRepository = householdIdRepository;
	}
	
	public Integer getMaxHouseholdIdByLocation(Integer locationId) {
		
		return householdIdRepository.getMaxHouseholdIdByLocation(locationId);
	}
	
	public Integer getMaxGuestHouseholdIdByLocation(Integer locationId) {
		
		return householdIdRepository.getMaxGuestHouseholdIdByLocation(locationId);
	}
	
	public List<String> getSeriesOfHouseholdId(Integer maxId) {
		return householdIdRepository.getSeriesOfHouseholdId(maxId);
	}
	
	public List<String> getSeriesOfGuestHouseholdId(Integer maxId) {
		return householdIdRepository.getSeriesOfGuestHouseholdId(maxId);
	}
	
	public int insertHouseholdId(HouseholdId healthId) {
		return householdIdRepository.insertHouseholdId(healthId);
	}
	
	public int insertGuestHouseholdId(HouseholdId healthId) {
		return householdIdRepository.insertGuestHouseholdId(healthId);
	}
	
	public JSONArray generateHouseholdId(List<Integer> villageIds) throws Exception {
		JSONArray villageCodes = new JSONArray();
		for (Integer villageId : villageIds) {
			Integer number = getMaxHouseholdIdByLocation(villageId);
			List<String> listOfString = getSeriesOfHouseholdId(number + 1);
			HouseholdId healthId = new HouseholdId();
			healthId.setCreated(new Date());
			healthId.sethId(String.valueOf(number + HOUSEHOLD_ID_LIMIT));
			healthId.setLocationId(villageId);
			healthId.setStatus(true);
			
			long isSaved = insertHouseholdId(healthId);
			if (isSaved > 0) {
				JSONObject villageCode = new JSONObject();
				villageCode.put("village_id", villageId);
				JSONArray ids = new JSONArray();
				for (String healthId1 : listOfString) {
					ids.put(healthId1);
				}
				villageCode.put("generated_code", ids);
				villageCodes.put(villageCode);
			}
		}
		return villageCodes;
	}
	
	public JSONArray generateGuestHouseholdId(List<Integer> villageIds) throws Exception {
		JSONArray villageCodes = new JSONArray();
		for (Integer villageId : villageIds) {
			Integer number = getMaxGuestHouseholdIdByLocation(villageId);
			List<String> listOfString = getSeriesOfGuestHouseholdId(number + 1);
			HouseholdId healthId = new HouseholdId();
			healthId.setCreated(new Date());
			healthId.sethId(String.valueOf(number + HOUSEHOLD_ID_LIMIT));
			healthId.setLocationId(villageId);
			healthId.setStatus(true);
			
			long isSaved = insertGuestHouseholdId(healthId);
			if (isSaved > 0) {
				JSONObject villageCode = new JSONObject();
				villageCode.put("village_id", villageId);
				JSONArray ids = new JSONArray();
				for (String healthId1 : listOfString) {
					ids.put(healthId1);
				}
				villageCode.put("generated_code", ids);
				villageCodes.put(villageCode);
				
			}
		}
		return villageCodes;
	}
}
