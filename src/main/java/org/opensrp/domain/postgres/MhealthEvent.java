package org.opensrp.domain.postgres;

public class MhealthEvent extends Event {
	
private String providerId;
	
	private String eventType;
	
	private Long serverVersion;
	
	private String baseEntityId;
	
	private String district;
	
	private String division;
	
	private String branch;
	
	private String village;
	
	private String formSubmissionId;

	
	public String getProviderId() {
		return providerId;
	}

	
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	
	public String getEventType() {
		return eventType;
	}

	
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	
	public Long getServerVersion() {
		return serverVersion;
	}

	
	public void setServerVersion(Long serverVersion) {
		this.serverVersion = serverVersion;
	}

	
	public String getBaseEntityId() {
		return baseEntityId;
	}

	
	public void setBaseEntityId(String baseEntityId) {
		this.baseEntityId = baseEntityId;
	}

	
	public String getDistrict() {
		return district;
	}

	
	public void setDistrict(String district) {
		this.district = district;
	}

	
	public String getDivision() {
		return division;
	}

	
	public void setDivision(String division) {
		this.division = division;
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

	
	public String getFormSubmissionId() {
		return formSubmissionId;
	}

	
	public void setFormSubmissionId(String formSubmissionId) {
		this.formSubmissionId = formSubmissionId;
	}
	
	
}
