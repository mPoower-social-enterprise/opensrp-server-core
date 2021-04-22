package org.opensrp.service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.domain.postgres.MhealthPractitionerLocation;
import org.opensrp.repository.PractitionerLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PractitionerLocationService {
	
	private PractitionerLocationRepository practitionerLocationRepository;
	
	@Autowired
	public PractitionerLocationService(PractitionerLocationRepository practitionerLocationRepository) {
		this.practitionerLocationRepository = practitionerLocationRepository;
	}
	
	public JSONArray getDistrictAndUpazila(Integer parentLocationTag) throws JSONException {
		List<MhealthPractitionerLocation> districtAndUpazila = practitionerLocationRepository
		        .getDistrictAndUpazila(parentLocationTag);
		JSONArray response = new JSONArray();
		for (MhealthPractitionerLocation o : districtAndUpazila) {
			JSONObject disAndUpa = new JSONObject();
			disAndUpa.put("name", o.getDistrict());
			String[] upazilas = o.getUpazila().split(",");
			JSONArray upa = new JSONArray();
			for (String upazila : upazilas) {
				upa.put(upazila);
			}
			disAndUpa.put("upazilas", upa);
			response.put(disAndUpa);
		}
		return response;
	}
	
	public List<MhealthPractitionerLocation> getLocationByTagId(Integer tagId) {
		return practitionerLocationRepository.getDistrictAndUpazila(tagId);
	}
	
	public List<MhealthPractitionerLocation> getLocationByParentId(Integer parentId) {
		return practitionerLocationRepository.getLocationByParentId(parentId);
	}
}
