package org.opensrp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.domain.PractitionerLocation;
import org.opensrp.domain.postgres.PractitionerDetails;
import org.opensrp.repository.PractitionerDetailsRepository;
import org.springframework.stereotype.Service;

@Service
public class PractionerDetailsService {
	
	private PractitionerDetailsRepository practitionerDetailsRepository;
	
	public PractitionerDetailsRepository getPractitionerDetailsRepository() {
		return practitionerDetailsRepository;
	}
	
	public void setPractitionerDetailsRepository(PractitionerDetailsRepository practitionerDetailsRepository) {
		this.practitionerDetailsRepository = practitionerDetailsRepository;
	}
	
	public List<PractitionerLocation> findPractitionerLocationsByChildGroup(int practitionerId, int childGroupId,
	                                                                        int assignedLocationTagId) {
		
		return getPractitionerDetailsRepository().findPractitionerLocationsByChildGroup(practitionerId, childGroupId,
		    assignedLocationTagId);
	}
	
	public PractitionerDetails findPractitionerDetailsByUsername(String username) {
		
		return getPractitionerDetailsRepository().findPractitionerDetailsByUsername(username);
	}
	
	public JSONArray convertLocationTreeToJSON(List<PractitionerLocation> practitionerLocations, Boolean enable,
	                                           String fullName)
	    throws JSONException {
		JSONArray locationTree = new JSONArray();
		
		Map<String, Boolean> map = new HashMap<>();
		JSONObject object = new JSONObject();
		JSONArray locations = new JSONArray();
		JSONObject fullLocation = new JSONObject();
		
		int counter = 0, limit = 0;
		String username = "";
		
		for (PractitionerLocation practitionerLocation : practitionerLocations) {
			counter++;
			limit++;
			if (map.get(practitionerLocation.getUsername()) == null || !map.get(practitionerLocation.getUsername())) {
				if (counter > 1) {
					locations.put(fullLocation);
					object.put("username", username.trim());
					object.put("locations", locations);
					object.put("full_name", fullName);
					object.put("simprints_enable", enable);
					locationTree.put(object);
					locations = new JSONArray();
					object = new JSONObject();
					fullLocation = new JSONObject();
					counter = 1;
				}
				map.put(practitionerLocation.getUsername(), true);
			}
			
			username = practitionerLocation.getFirstName();
			String[] names = practitionerLocation.getLocationName().split(":");
			String locationName = names[0];
			
			JSONObject location = new JSONObject();
			location.put("code", practitionerLocation.getCode().trim());
			location.put("id", practitionerLocation.getId());
			location.put("name", locationName.trim());
			String name = practitionerLocation.getLocationTagName().toLowerCase().replaceAll(" ", "_");
			fullLocation.put(name, location);
			
			if (limit == practitionerLocations.size()) {
				locations.put(fullLocation);
				object.put("username", username.trim());
				object.put("locations", locations);
				object.put("full_name", fullName);
				object.put("simprints_enable", enable);
				locationTree.put(object);
				object = new JSONObject();
				locations = new JSONArray();
			}
		}
		return locationTree;
	}
	
}
