package org.opensrp.domain.postgres;

public class MhealthClientMetadata extends ClientMetadata {
	
	private String address2;
	
	private String address1;
	
	private String address3;
	
	private String division;
	
	private String district;
	
	private Long villageId;
	
	private String branch;
	
	public String getAddress2() {
		return address2;
	}
	
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	
	public String getAddress1() {
		return address1;
	}
	
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	
	public String getAddress3() {
		return address3;
	}
	
	public void setAddress3(String address3) {
		this.address3 = address3;
	}
	
	public String getDivision() {
		return division;
	}
	
	public void setDivision(String division) {
		this.division = division;
	}
	
	public String getDistrict() {
		return district;
	}
	
	public void setDistrict(String district) {
		this.district = district;
	}
	
	public Long getVillageId() {
		return villageId;
	}
	
	public void setVillageId(Long villageId) {
		this.villageId = villageId;
	}
	
	public String getBranch() {
		return branch;
	}
	
	public void setBranch(String branch) {
		this.branch = branch;
	}
	
}
