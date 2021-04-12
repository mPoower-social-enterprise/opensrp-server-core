package org.opensrp.domain;

public class PractitionerLocation {
	
	private int id;
	
	private String code;
	
	private int leafLocationId;
	
	private int PractitionerId;
	
	private String username;
	
	private String firstName;
	
	private String lastName;
	
	private String locationTagName;
	
	private String locationName;
	
	private Boolean enable;
	
	private String fullName;
	
	private String groupName;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public int getLeafLocationId() {
		return leafLocationId;
	}
	
	public void setLeafLocationId(int leafLocationId) {
		this.leafLocationId = leafLocationId;
	}
	
	public int getPractitionerId() {
		return PractitionerId;
	}
	
	public void setPractitionerId(int practitionerId) {
		PractitionerId = practitionerId;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getLocationTagName() {
		return locationTagName;
	}
	
	public void setLocationTagName(String locationTagName) {
		this.locationTagName = locationTagName;
	}
	
	public Boolean getEnable() {
		return enable;
	}
	
	public void setEnable(Boolean enable) {
		this.enable = enable;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getGroupName() {
		return groupName;
	}
	
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public String getLocationName() {
		return locationName;
	}
	
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
}
