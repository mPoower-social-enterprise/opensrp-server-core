package org.opensrp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.domain.PractitionerLocation;
import org.opensrp.repository.postgres.PractitionerDetailsRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PractionerDetailsService {
	
	@Autowired
	private PractitionerDetailsRepositoryImpl practitionerDetailsRepositoryImpl;
	
	public List<PractitionerLocation> findPractitionerLocationsByChildGroup(int practitionerId, int childGroupId) {
		
		return practitionerDetailsRepositoryImpl.findPractitionerLocationsByChildGroup(practitionerId, childGroupId);
	}
	
	public JSONArray convertLocationTreeToJSON(List<PractitionerLocation> practitionerLocations, Boolean enable, String fullName)
	    throws JSONException {
		JSONArray locationTree = new JSONArray();
		
		Map<String, Boolean> map = new HashMap<>();
		JSONObject object = new JSONObject();
		JSONArray locations = new JSONArray();
		JSONObject fullLocation = new JSONObject();
		
		int counter = 0, limit = 0;
		String username = "";
		
		for (PractitionerLocation practitionerLoation : practitionerLocations) {
			counter++;
			limit++;
			if (map.get(practitionerLoation.getUsername()) == null || !map.get(practitionerLoation.getUsername())) {
				if (counter > 1) {
					fullLocation = setEmptyValues(fullLocation);
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
				map.put(practitionerLoation.getUsername(), true);
			}
			
			username = practitionerLoation.getFirstName();
			
			if (practitionerLoation.getLocationTagName().equalsIgnoreCase("country")) {
				if (counter > 1) {
					fullLocation = setEmptyValues(fullLocation);
					locations.put(fullLocation);
					fullLocation = new JSONObject();
				}
			}
			
			String[] names = practitionerLoation.getLocationName().split(":");
			String locationName = names[0];
			
			JSONObject location = new JSONObject();
			location.put("code", practitionerLoation.getCode().trim());
			location.put("id", practitionerLoation.getId());
			location.put("name", locationName.trim());
			String name = practitionerLoation.getLocationTagName().toLowerCase().replaceAll(" ", "_");
			fullLocation.put(name, location);
			
			if (limit == practitionerLocations.size()) {
				fullLocation = setEmptyValues(fullLocation);
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
	
	private JSONObject setEmptyValues(JSONObject fullLocation) throws JSONException {
		if (!fullLocation.has("country")) {
			fullLocation.put("country", getLocationProperty());
		}
		if (!fullLocation.has("division")) {
			fullLocation.put("division", getLocationProperty());
		}
		if (!fullLocation.has("district")) {
			fullLocation.put("district", getLocationProperty());
		}
		if (!fullLocation.has("city_corporation_upazila")) {
			fullLocation.put("city_corporation_upazila", getLocationProperty());
		}
		if (!fullLocation.has("pourasabha")) {
			fullLocation.put("pourasabha", getLocationProperty());
		}
		if (!fullLocation.has("union_ward")) {
			fullLocation.put("union_ward", getLocationProperty());
		}
		if (!fullLocation.has("village")) {
			fullLocation.put("village", getLocationProperty());
		}
		return fullLocation;
	}
	
	private JSONObject getLocationProperty() throws JSONException {
		JSONObject property = new JSONObject();
		property.put("name", "");
		property.put("id", 0);
		property.put("code", "00");
		return property;
	}
}
