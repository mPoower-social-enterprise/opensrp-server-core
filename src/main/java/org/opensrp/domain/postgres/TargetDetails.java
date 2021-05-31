package org.opensrp.domain.postgres;

public class TargetDetails {
	
	private String username;
	
	private Long targetId;
	
	private String targetName;
	
	private int targetCount;
	
	private String year;
	
	private String month;
	
	private String day;
	
	private String startDate;
	
	private String endDate;
	
	private Long timestamp;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public Long getTargetId() {
		return targetId;
	}
	
	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}
	
	public String getTargetName() {
		return targetName;
	}
	
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	
	public int getTargetCount() {
		return targetCount;
	}
	
	public void setTargetCount(int targetCount) {
		this.targetCount = targetCount;
	}
	
	public String getYear() {
		return year;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
	
	public String getMonth() {
		return month;
	}
	
	public void setMonth(String month) {
		this.month = month;
	}
	
	public String getDay() {
		return day;
	}
	
	public void setDay(String day) {
		this.day = day;
	}
	
	public String getStartDate() {
		return startDate;
	}
	
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	public String getEndDate() {
		return endDate;
	}
	
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public Long getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	
	@Override
	public String toString() {
		return "TargetDetails [username=" + username + ", targetId=" + targetId + ", targetName=" + targetName
		        + ", targetCount=" + targetCount + ", year=" + year + ", month=" + month + ", day=" + day + ", startDate="
		        + startDate + ", endDate=" + endDate + ", timestamp=" + timestamp + "]";
	}
}
