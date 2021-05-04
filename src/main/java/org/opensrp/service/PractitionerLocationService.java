package org.opensrp.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
	
	public JSONArray getDistrictAndUpazila(Integer districtLocationTagId) throws JSONException {
		List<MhealthPractitionerLocation> districtAndUpazila = practitionerLocationRepository
		        .getDistrictAndUpazila(districtLocationTagId);
		JSONArray response = new JSONArray();
		for (MhealthPractitionerLocation o : districtAndUpazila) {
			JSONObject disstrictUpazila = new JSONObject();
			disstrictUpazila.put("name", o.getDistrict());
			String[] upazilas = o.getUpazila().split(",");
			JSONArray upazilaName = new JSONArray();
			for (String upazila : upazilas) {
				upazilaName.put(upazila);
			}
			disstrictUpazila.put("upazilas", upazilaName);
			response.put(disstrictUpazila);
		}
		return response;
	}
	
	public List<MhealthPractitionerLocation> getLocationByTagId(Integer tagId) {
		return practitionerLocationRepository.getLocationByTagId(tagId);
	}
	
	public List<MhealthPractitionerLocation> getLocationByParentId(Integer parentId) {
		return practitionerLocationRepository.getLocationByParentId(parentId);
	}
	
	public List<Integer> getPractitionerVillageIds(String username) {
		
		return practitionerLocationRepository.getPractitionerVillageIds(username);
	}
	
	public MhealthPractitionerLocation getPractitionerDivisionDistrictBranch(String username) {
		
		return practitionerLocationRepository.getPractitionerDivisionDistrictBranch(username);
	}
	
	public MhealthPractitionerLocation generatePostfix(String username, String district, String division, String branch) {
		MhealthPractitionerLocation mhealthPractitionerLocation = new MhealthPractitionerLocation();
		if (!StringUtils.isBlank(district)) {
			System.err.println("ok");
			mhealthPractitionerLocation.setPostFix("_" + district);
			mhealthPractitionerLocation.setBranch(branch);
			mhealthPractitionerLocation.setDistrict(district);
			mhealthPractitionerLocation.setDivision(division);
		} else {
			mhealthPractitionerLocation = getPractitionerDivisionDistrictBranch(username);
			
			if (mhealthPractitionerLocation == null) {
				mhealthPractitionerLocation = new MhealthPractitionerLocation();
				mhealthPractitionerLocation.setPostFix("_NA");
				mhealthPractitionerLocation.setBranch("");
				mhealthPractitionerLocation.setDistrict("");
				mhealthPractitionerLocation.setDivision("");
				System.err.println("na");
			}
		}
		return mhealthPractitionerLocation;
	}
}
