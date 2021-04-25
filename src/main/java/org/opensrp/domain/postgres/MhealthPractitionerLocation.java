package org.opensrp.domain.postgres;

public class MhealthPractitionerLocation {
	
	private Integer id;
	
	private Integer parentId;
	
	private String name;
	
	private String code;
	
	private String district;
	
	private String upazila;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getParentId() {
		return parentId;
	}
	
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getDistrict() {
		return district;
	}
	
	public void setDistrict(String district) {
		this.district = district;
	}
	
	public String getUpazila() {
		return upazila;
	}
	
	public void setUpazila(String upazila) {
		this.upazila = upazila;
	}
	
	@Override
	public String toString() {
		return "MhealthPractitionerLocation [id=" + id + ", parentId=" + parentId + ", name=" + name + ", code=" + code
		        + ", district=" + district + ", upazila=" + upazila + "]";
	}
	
}
