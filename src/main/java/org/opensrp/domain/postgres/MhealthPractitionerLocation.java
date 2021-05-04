package org.opensrp.domain.postgres;

public class MhealthPractitionerLocation {
	
	private Integer id;
	
	private Integer parentId;
	
	private String name;
	
	private String code;
	
	private String division;
	
	private String district;
	
	private String upazila;
	
	private String branch;
	
	private String village;
	
	private String postFix;
	
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
	
	public String getBranch() {
		return branch;
	}
	
	public void setBranch(String branch) {
		this.branch = branch;
	}
	
	public String getVillage() {
		return village;
	}
	
	public void setVillage(String village) {
		this.village = village;
	}
	
	public String getPostFix() {
		return postFix;
	}
	
	public void setPostFix(String postFix) {
		this.postFix = postFix;
	}
	
	public String getDivision() {
		return division;
	}
	
	public void setDivision(String division) {
		this.division = division;
	}
	
	@Override
	public String toString() {
		return "MhealthPractitionerLocation [id=" + id + ", parentId=" + parentId + ", name=" + name + ", code=" + code
		        + ", division=" + division + ", district=" + district + ", upazila=" + upazila + ", branch=" + branch
		        + ", village=" + village + ", postFix=" + postFix + "]";
	}
	
}
